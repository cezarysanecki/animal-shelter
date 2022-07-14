package pl.devcezz.shelter.generator.dto;

import pl.devcezz.shelter.catalogue.AnimalDto;

import java.util.List;

public record ShelterListData(List<AnimalDto> animals, Long shelterCapacity) {
}
