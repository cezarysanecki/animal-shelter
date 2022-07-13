package pl.devcezz.shelter.generator;

import io.vavr.collection.HashMap;
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
class HtmlPreparer {

    private final ShelterReadModel shelterReadModel;
    private final CatalogueReadModelRepository catalogueReadModelRepository;

    HtmlInput process() {
        ShelterDto shelter = shelterReadModel.fetchShelter();
        List<UUID> proposalIds = shelter.proposals()
                .stream()
                .map(ShelterProposalDto::proposalId)
                .toList();
        List<AnimalDto> animals = catalogueReadModelRepository.findAllFor(proposalIds);

        HashMap<String, Object> contextMap = HashMap.of(
                "animals", animals,
                "shelterCapacity", shelter.capacity()
        );

        return new HtmlInput("shelter_list", contextMap);
    }
}
