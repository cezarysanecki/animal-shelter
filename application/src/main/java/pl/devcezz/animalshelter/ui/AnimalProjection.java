package pl.devcezz.animalshelter.ui;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.devcezz.animalshelter.ui.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.ui.query.GetAnimalsQuery;
import pl.devcezz.animalshelter.ui.dto.AnimalDto;
import pl.devcezz.animalshelter.ui.dto.AnimalInfoDto;

public interface AnimalProjection {

    List<AnimalDto> handle(GetAnimalsQuery query);

    Option<AnimalInfoDto> handle(GetAnimalInfoQuery query);
}
