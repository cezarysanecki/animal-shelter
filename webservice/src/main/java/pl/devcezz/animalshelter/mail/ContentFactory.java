package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.commons.notification.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.mail.model.ContextCreationFailedException;
import pl.devcezz.animalshelter.mail.model.EmailContent;
import pl.devcezz.animalshelter.mail.model.EmailTemplate;
import pl.devcezz.animalshelter.mail.model.EmailContext;
import pl.devcezz.animalshelter.read.AnimalProjection;
import pl.devcezz.animalshelter.read.query.GetAnimalInfoQuery;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class ContentFactory {

    private final AnimalProjection animalProjection;
    private final EmailRepository emailRepository;

    ContentFactory(final AnimalProjection animalProjection, final EmailRepository emailRepository) {
        this.animalProjection = animalProjection;
        this.emailRepository = emailRepository;
    }

    EmailContent create(Notification notification) {
        EmailTemplate template = emailRepository.findTemplateBy(notification.type())
                .getOrElseThrow(() -> new ContextCreationFailedException("not found template for notification: " + notification.type()));

        EmailContext context = Match(notification).of(
                Case($(instanceOf(SuccessfulAdoptionNotification.class)), this::createContext)
        );

        return new EmailContent(template, context);
    }

    private EmailContext createContext(SuccessfulAdoptionNotification notification) {
        return animalProjection.handle(new GetAnimalInfoQuery(notification.animalId().value()))
                .map(animal -> EmailContext.create()
                        .append("animalName", animal.getName()))
                .getOrElseThrow(() -> new ContextCreationFailedException("not found animal of id: " + notification.animalId().value()));
    }
}
