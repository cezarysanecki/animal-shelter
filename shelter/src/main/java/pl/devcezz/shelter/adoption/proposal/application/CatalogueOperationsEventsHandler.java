package pl.devcezz.shelter.adoption.proposal.application;

import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.adoption.proposal.view.ProposalAnimalData;
import pl.devcezz.shelter.adoption.proposal.view.ProposalAnimalDatabase;
import pl.devcezz.shelter.commons.events.handler.EventsListener;
import pl.devcezz.shelter.commons.infrastructure.AdoptionTransactional;

import static pl.devcezz.shelter.adoption.proposal.model.PendingProposal.createNewPendingProposal;
import static pl.devcezz.shelter.catalogue.CatalogueEvent.AnimalConfirmedEvent;

@RequiredArgsConstructor
public class CatalogueOperationsEventsHandler {

    private final Proposals proposalRepository;
    private final ProposalAnimalDatabase animalDatabase;

    @EventsListener
    @AdoptionTransactional
    public void handle(AnimalConfirmedEvent event) {
        ProposalId proposalId = ProposalId.of(event.getAnimalId());

        proposalRepository.save(
                createNewPendingProposal(proposalId));
        animalDatabase.save(
                ProposalAnimalData.of(proposalId, event.getName(), event.getAge(), event.getSpecies(), event.getGender()));
    }
}
