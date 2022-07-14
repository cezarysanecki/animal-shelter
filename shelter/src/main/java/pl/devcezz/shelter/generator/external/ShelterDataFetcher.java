package pl.devcezz.shelter.generator.external;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModel;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterDto;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterProposalDto;
import pl.devcezz.shelter.catalogue.AnimalDto;
import pl.devcezz.shelter.catalogue.CatalogueReadModelRepository;
import pl.devcezz.shelter.generator.ContentType;
import pl.devcezz.shelter.generator.DataFetcher;
import pl.devcezz.shelter.generator.dto.ShelterListData;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterDataFetcher implements DataFetcher {

    private final ShelterListDataFetcher shelterListDataFetcher;

    @Override
    public Object fetch(ContentType contentType) {
        return Match(contentType).of(
                Case($(ContentType.ShelterList), c -> shelterListDataFetcher.fetch())
        );
    }
}

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterListDataFetcher {

    private final ShelterReadModel shelterReadModel;
    private final CatalogueReadModelRepository catalogueReadModelRepository;

    ShelterListData fetch() {
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

}
