package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.animal.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.commons.notification.Notifier;
import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.read.AnimalProjection;
import pl.devcezz.animalshelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.read.result.AnimalInfoDto;
import pl.devcezz.cqrs.event.EventHandler;

class HandleAdoptingAnimal implements EventHandler<AnimalAdoptionSucceeded> {

    private final Notifier notifier;
    private final AnimalProjection animalProjection;

    HandleAdoptingAnimal(final Notifier notifier, final AnimalProjection animalProjection) {
        this.notifier = notifier;
        this.animalProjection = animalProjection;
    }

    @Override
    public void handle(final AnimalAdoptionSucceeded event) {
        animalProjection.handle(new GetAnimalInfoQuery(event.getAnimalId().value()))
                .peek(this::sendNotification)
                .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }

    private void sendNotification(final AnimalInfoDto animal) {
        notifier.notify(new Notification.AdoptionNotification(animal.getName()));
    }
}
