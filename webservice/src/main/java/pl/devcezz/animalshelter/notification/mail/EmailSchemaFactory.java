package pl.devcezz.animalshelter.notification.mail;

import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.WarnedAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.FailureAcceptanceNotification;
import pl.devcezz.animalshelter.notification.mail.exception.SchemaCreationFailedException;
import pl.devcezz.animalshelter.notification.mail.exception.UnknownTypeOfNotificationException;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class EmailSchemaFactory {

    private final EmailDatabaseRepository emailRepository;

    EmailSchemaFactory(final EmailDatabaseRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    EmailSchema createUsing(Notification notification) {
        EmailData data = emailRepository.findEmailDataBy(notification.type())
                .getOrElseThrow(() -> new SchemaCreationFailedException("not found value data for notification: " + notification.type()));

        EmailContext context = Match(notification).of(
                Case($(instanceOf(SuccessfulAdoptionNotification.class)), this::createContext),
                Case($(instanceOf(SuccessfulAcceptanceNotification.class)), this::createContext),
                Case($(instanceOf(WarnedAcceptanceNotification.class)), this::createContext),
                Case($(instanceOf(FailureAcceptanceNotification.class)), this::createContext),
                Case($(), () -> { throw new UnknownTypeOfNotificationException(); })
        );

        return new EmailSchema(data, context);
    }

    private EmailContext createContext(SuccessfulAdoptionNotification notification) {
        return EmailContext.create()
                .append("animalName", notification.animalName());
    }

    private EmailContext createContext(SuccessfulAcceptanceNotification notification) {
        return EmailContext.create()
                .append("animalName", notification.animalName());
    }

    private EmailContext createContext(WarnedAcceptanceNotification notification) {
        return EmailContext.create()
                .append("animalName", notification.animalName());
    }

    private EmailContext createContext(FailureAcceptanceNotification notification) {
        return EmailContext.create()
                .append("reason", notification.reason());
    }
}
