package pl.devcezz.animalshelter.infrastructure;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.devcezz.animalshelter.shelter.read.AnimalProjection;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsQuery;

@Component
public class GraphQLDataFetchers {

    @Autowired
    private AnimalProjection animalProjection;

    public DataFetcher getAnimalByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String animalId = dataFetchingEnvironment.getArgument("id");
            return animalProjection.handle(new GetAnimalsQuery())
                    .filter(animal -> animal.getAnimalId().toString().equals(animalId))
                    .getOrElse(() -> null);
        };
    }
}
