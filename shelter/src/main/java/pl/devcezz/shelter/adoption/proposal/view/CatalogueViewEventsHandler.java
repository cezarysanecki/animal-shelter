package pl.devcezz.shelter.adoption.proposal.view;

import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.commons.events.handler.EventsListener;
import pl.devcezz.shelter.commons.infrastructure.AdoptionTransactional;

import static pl.devcezz.shelter.catalogue.CatalogueEvent.AnimalConfirmedEvent;

@RequiredArgsConstructor
class CatalogueViewEventsHandler {

    private final ProposalAnimalDatabase animalDatabase;

    @EventsListener
    @AdoptionTransactional
    public void handle(AnimalConfirmedEvent event) {
        animalDatabase.save(
                ProposalAnimalData.of(
                        ProposalId.of(event.getAnimalId()),
                        event.getName(),
                        event.getAge(),
                        event.getSpecies(),
                        event.getGender()));
    }
}
