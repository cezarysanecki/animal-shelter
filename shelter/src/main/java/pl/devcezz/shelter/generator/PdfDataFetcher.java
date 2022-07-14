package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModel;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterDto;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterProposalDto;
import pl.devcezz.shelter.catalogue.AnimalDto;
import pl.devcezz.shelter.catalogue.CatalogueReadModelRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PdfDataFetcher {

    private final ShelterReadModel shelterReadModel;
    private final CatalogueReadModelRepository catalogueReadModelRepository;

    PdfData fetch() {
        ShelterDto shelter = shelterReadModel.fetchShelter();
        List<AnimalDto> animals = catalogueReadModelRepository.findAllFor(
                extractProposalIds(shelter));

        return new PdfData(animals, shelter.capacity());
    }

    private List<UUID> extractProposalIds(ShelterDto shelter) {
        return shelter.proposals()
                .stream()
                .map(ShelterProposalDto::getProposalId)
                .toList();
    }
}
