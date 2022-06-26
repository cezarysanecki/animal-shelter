package pl.devcezz.shelter.catalogue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyConfirmed;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;

@RequiredArgsConstructor
class AnimalEventHandler {

    private final AnimalRepository animalRepository;

    @EventListener
    public void handleProposalAccepted(ProposalAccepted event) {
        Animal animal = findBy(AnimalId.of(event.getProposalId()));

        animal.register();
    }

    @EventListener
    public void handleProposalAlreadyConfirmed(ProposalAlreadyConfirmed event) {
        Animal animal = findBy(AnimalId.of(event.getProposalId()));

        animal.register();
    }

    private Animal findBy(AnimalId animalId) {
        return animalRepository.findByAnimalId(animalId)
                .getOrElseThrow(() -> new AnimalNotFoundException(animalId.getValue()));
    }
}
