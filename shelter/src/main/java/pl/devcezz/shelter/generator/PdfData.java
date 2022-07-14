package pl.devcezz.shelter.generator;

import pl.devcezz.shelter.catalogue.AnimalDto;

import java.util.List;

record PdfData(List<AnimalDto> animals, Long shelterCapacity) {
}
