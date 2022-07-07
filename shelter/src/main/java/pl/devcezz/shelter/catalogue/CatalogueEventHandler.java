package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;

@RequiredArgsConstructor
@Slf4j
class CatalogueEventHandler {

    private final CatalogueRepository repository;

    @EventListener
    public void handle(ProposalAccepted event) {
        Try.of(() -> Option.of(event.getProposalId())
                .map(AnimalId::of)
                .flatMap(repository::findBy)
                .map(Animal::register)
                .map(repository::updateStatus)
        ).onFailure(ex -> log.error("failed to register animal", ex));
    }
}
