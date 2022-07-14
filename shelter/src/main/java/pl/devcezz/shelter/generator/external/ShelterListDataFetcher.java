package pl.devcezz.shelter.generator.external;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModel;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterDto;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterProposalDto;
import pl.devcezz.shelter.catalogue.AnimalDto;
import pl.devcezz.shelter.catalogue.CatalogueReadModelRepository;
import pl.devcezz.shelter.generator.dto.ContentType;
import pl.devcezz.shelter.generator.DataFetcher;
import pl.devcezz.shelter.generator.dto.ShelterListData;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterListDataFetcher implements DataFetcher {

    private final ShelterReadModel shelterReadModel;
    private final CatalogueReadModelRepository catalogueReadModelRepository;

    @Override
    public ShelterListData fetch() {
        ShelterDto shelter = shelterReadModel.fetchShelter();
        List<AnimalDto> animals = catalogueReadModelRepository.findAllFor(
                extractProposalIds(shelter));

        return new ShelterListData(animals, shelter.capacity());
    }

    private List<UUID> extractProposalIds(ShelterDto shelter) {
        return shelter.proposals()
                .stream()
                .map(ShelterProposalDto::getProposalId)
                .toList();
    }

    @Override
    public boolean isApplicable(ContentType contentType) {
        return contentType == ContentType.ShelterList;
    }

}
