package pl.devcezz.animalshelter.shelter.read;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalDto;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalInfoDto;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsQuery;

import java.util.List;
import java.util.UUID;

@Component
class AnimalGraphQLQuery implements GraphQLQueryResolver {

    private final AnimalProjection animalProjection;

    AnimalGraphQLQuery(AnimalProjection animalProjection) {
        this.animalProjection = animalProjection;
    }

    public AnimalInfoDto animalById(String id) {
        UUID animalId = UUID.fromString(id);
        return animalProjection.handle(new GetAnimalInfoQuery(animalId))
                .getOrElse(() -> null);
    }

    public List<AnimalDto> allAnimals(AnimalFilter filter) {
        var animals = animalProjection.handle(new GetAnimalsQuery());

        if (filter != null) {
            animals = animals.filter(animal -> animal.getInShelter().equals(filter.getInShelter()));
        }

        return animals.asJava();
    }
}

class AnimalFilter {

    private Boolean inShelter;

    Boolean getInShelter() {
        return inShelter;
    }

    void setInShelter(Boolean inShelter) {
        this.inShelter = inShelter;
    }
}
