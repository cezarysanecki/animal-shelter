package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.animal.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.commons.mail.EmailSender;
import pl.devcezz.animalshelter.commons.mail.model.AdoptionMail;
import pl.devcezz.animalshelter.read.AnimalProjection;
import pl.devcezz.animalshelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.read.result.AnimalInfoDto;
import pl.devcezz.cqrs.event.EventHandler;

class HandleAdoptingAnimal implements EventHandler<AnimalAdoptionSucceeded> {

    private final EmailSender emailSender;
    private final AnimalProjection animalProjection;

    HandleAdoptingAnimal(final EmailSender emailSender, final AnimalProjection animalProjection) {
        this.emailSender = emailSender;
        this.animalProjection = animalProjection;
    }

    @Override
    public void handle(final AnimalAdoptionSucceeded event) {
        animalProjection.handle(new GetAnimalInfoQuery(event.getAnimalId().value()))
                .peek(this::sendMail)
                .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }

    private void sendMail(final AnimalInfoDto animal) {
        emailSender.sendEmail(new AdoptionMail(animal.getName()));
    }
}
