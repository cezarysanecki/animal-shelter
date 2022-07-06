package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyConfirmed;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;

@RequiredArgsConstructor
@Slf4j
class CatalogueEventHandler {

    private final CatalogueDatabase database;

    @EventListener
    public void handle(ProposalAccepted event) {
        Try.of(() -> Option.of(event.getProposalId())
                .map(AnimalId::of)
                .flatMap(database::findBy)
                .map(Animal::register)
                .map(database::updateStatus)
        ).onFailure(ex -> log.error("failed to register animal", ex));
    }

    @EventListener
    public void handle(ProposalAlreadyConfirmed event) {
        Try.of(() -> Option.of(event.getProposalId())
                .map(AnimalId::of)
                .flatMap(database::findBy)
                .map(Animal::register)
                .map(database::updateStatus)
        ).onFailure(ex -> log.error("failed to register animal", ex));
    }
}
