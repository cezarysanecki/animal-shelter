package pl.devcezz.animalshelter.shelter.read.dto;

import io.vavr.collection.List;

public record DataToReportDto(List<AnimalInShelterDto> animals, Integer shelterCapacity) {}
