package pl.devcezz.animalshelter.read;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.devcezz.animalshelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.read.query.GetAnimalsQuery;
import pl.devcezz.animalshelter.read.result.AnimalDto;
import pl.devcezz.animalshelter.read.result.AnimalInfoDto;

public interface AnimalProjection {

    List<AnimalDto> handle(GetAnimalsQuery query);

    Option<AnimalInfoDto> handle(GetAnimalInfoQuery query);
}