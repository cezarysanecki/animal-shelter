package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.mail.model.EmailTemplate;

class EmailFactory {

    private final EmailContentFactory emailContentFactory;
    private final EmailContentProperties properties;

    EmailFactory(final EmailContentFactory emailContentFactory, final EmailContentProperties properties) {
        this.emailContentFactory = emailContentFactory;
        this.properties = properties;
    }

    Email createEmailFor(final Notification notification) {
        EmailTemplate template = emailContentFactory.createUsing(notification);
        return Email.builder()
                .template(template)
                .properties(properties);
    }
}
