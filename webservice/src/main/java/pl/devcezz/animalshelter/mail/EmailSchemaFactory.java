package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.commons.notification.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.mail.model.SchemaCreationFailedException;
import pl.devcezz.animalshelter.mail.model.EmailSchema;
import pl.devcezz.animalshelter.mail.model.EmailData;
import pl.devcezz.animalshelter.mail.model.EmailContext;
import pl.devcezz.animalshelter.read.AnimalProjection;
import pl.devcezz.animalshelter.read.query.GetAnimalInfoQuery;

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
                .getOrElseThrow(() -> new SchemaCreationFailedException("not found email data for notification: " + notification.type()));

        EmailContext context = Match(notification).of(
                Case($(instanceOf(SuccessfulAdoptionNotification.class)), this::createContext)
        );

        return new EmailSchema(data, context);
    }

    private EmailContext createContext(SuccessfulAdoptionNotification notification) {
        return animalProjection.handle(new GetAnimalInfoQuery(notification.animalId().value()))
                .map(animal -> EmailContext.create()
                        .append("animalName", animal.getName()))
                .getOrElseThrow(() -> new SchemaCreationFailedException("not found animal of id: " + notification.animalId().value()));
    }
}
