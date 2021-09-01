package pl.devcezz.animalshelter.mail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.commons.notification.Notification.AdoptionNotification;
import pl.devcezz.animalshelter.mail.EmailContextMap.AdoptionEmailContextMap;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class EmailContentFactory {

    private final EmailRepository emailRepository;
    private final TemplateEngine templateEngine;

    EmailContentFactory(final EmailRepository emailRepository, final TemplateEngine templateEngine) {
        this.emailRepository = emailRepository;
        this.templateEngine = templateEngine;
    }

    EmailContent createUsing(Notification notification) {
        EmailContextMap contextMap = Match(notification).of(
                Case($(instanceOf(AdoptionNotification.class)), AdoptionEmailContextMap::new)
        );

        return emailRepository.findTemplateBy(notification.notificationType())
                .map(template -> createContent(template, contextMap))
                .getOrElseThrow(IllegalArgumentException::new);
    }

    private EmailContent createContent(final EmailContentTemplate template, final EmailContextMap contextMap) {
        Context context = new Context(null, contextMap.contextMap());
        String text = templateEngine.process(template.template(), context);
        return new EmailContent(template.subject(), text);
    }
}
