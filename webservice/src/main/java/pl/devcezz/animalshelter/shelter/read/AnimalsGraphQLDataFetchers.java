package pl.devcezz.animalshelter.shelter.read;

import graphql.schema.DataFetcher;
import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalDto;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalInfoDto;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsQuery;

import java.util.UUID;

class AnimalsGraphQLDataFetchers {

    private final AnimalProjection animalProjection;

    AnimalsGraphQLDataFetchers(AnimalProjection animalProjection) {
        this.animalProjection = animalProjection;
    }

    DataFetcher<AnimalInfoDto> getAnimalByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            UUID animalId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
            Option<AnimalInfoDto> animal = animalProjection.handle(new GetAnimalInfoQuery(animalId));
            return animal.getOrElse(() -> null);
        };
    }

    DataFetcher<List<AnimalDto>> getAnimalsDataFetcher() {
        return dataFetchingEnvironment -> animalProjection.handle(new GetAnimalsQuery());
    }
}
