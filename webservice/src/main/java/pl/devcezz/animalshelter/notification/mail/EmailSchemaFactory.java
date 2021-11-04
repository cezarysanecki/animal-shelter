package pl.devcezz.animalshelter.notification.mail;

import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.notification.mail.dto.EmailData;
import pl.devcezz.animalshelter.notification.mail.exception.SchemaCreationFailedException;
import pl.devcezz.animalshelter.ui.AnimalProjection;
import pl.devcezz.animalshelter.ui.query.GetAnimalInfoQuery;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class EmailSchemaFactory {

    private final AnimalProjection animalProjection;
    private final EmailRepository emailRepository;

    EmailSchemaFactory(final AnimalProjection animalProjection, final EmailRepository emailRepository) {
        this.animalProjection = animalProjection;
        this.emailRepository = emailRepository;
    }

    EmailSchema createUsing(Notification notification) {
        EmailData data = emailRepository.findEmailDataBy(notification.type())
                .getOrElseThrow(() -> new SchemaCreationFailedException("not found value data for notification: " + notification.type()));

        EmailContext context = Match(notification).of(
                Case($(instanceOf(SuccessfulAdoptionNotification.class)), this::createContext)
        );

        return new EmailSchema(data, context);
    }

    private EmailContext createContext(SuccessfulAdoptionNotification notification) {
        return animalProjection.handle(new GetAnimalInfoQuery(notification.animalId()))
                .map(animal -> EmailContext.create()
                        .append("animalName", animal.getName()))
                .getOrElseThrow(() -> new SchemaCreationFailedException("not found animal of id: " + notification.animalId()));
    }
}
