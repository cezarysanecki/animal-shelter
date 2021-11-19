package pl.devcezz.animalshelter.shelter.read;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalDto;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalInShelterDto;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalInfoDto;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsInShelterQuery;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsQuery;

public interface AnimalProjection {

    List<AnimalDto> handle(GetAnimalsQuery query);

    List<AnimalInShelterDto> handle(GetAnimalsInShelterQuery query);

    Option<AnimalInfoDto> handle(GetAnimalInfoQuery query);
}
