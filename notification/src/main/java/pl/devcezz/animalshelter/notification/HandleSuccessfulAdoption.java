package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.SuccessfulAnimalAdoption;
import pl.devcezz.cqrs.event.EventHandler;

class HandleSuccessfulAdoption implements EventHandler<SuccessfulAnimalAdoption> {

    private final ContactRepository contactRepository;
    private final Set<Notifier> notifiers;

    HandleSuccessfulAdoption(
            final ContactRepository contactRepository,
            final Set<Notifier> notifiers
    ) {
        this.contactRepository = contactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final SuccessfulAnimalAdoption event) {
        Set<Contact> contacts = contactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(Contact::toContactDetails),
                createNotification(event))
        );
    }

    private SuccessfulAdoptionNotification createNotification(final SuccessfulAnimalAdoption event) {
        return new SuccessfulAdoptionNotification(
                event.animalId(),
                event.animalName(),
                event.animalAge(),
                event.animalSpecies()
        );
    }
}
