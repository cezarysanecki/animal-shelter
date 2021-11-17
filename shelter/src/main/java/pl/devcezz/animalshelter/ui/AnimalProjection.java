package pl.devcezz.animalshelter.ui;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.devcezz.animalshelter.ui.dto.AnimalDto;
import pl.devcezz.animalshelter.ui.dto.AnimalInShelterDto;
import pl.devcezz.animalshelter.ui.dto.AnimalInfoDto;
import pl.devcezz.animalshelter.ui.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.ui.query.GetAnimalsInShelterQuery;
import pl.devcezz.animalshelter.ui.query.GetAnimalsQuery;

public interface AnimalProjection {

    List<AnimalDto> handle(GetAnimalsQuery query);

    List<AnimalInShelterDto> handle(GetAnimalsInShelterQuery query);

    Option<AnimalInfoDto> handle(GetAnimalInfoQuery query);
}
