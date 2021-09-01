package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;

class EmailFactory {

    private final EmailContentFactory emailContentFactory;
    private final EmailContentProperties properties;

    EmailFactory(final EmailContentFactory emailContentFactory, final EmailContentProperties properties) {
        this.emailContentFactory = emailContentFactory;
        this.properties = properties;
    }

    EmailTemplate createTemplateFor(final Notification notification) {
        EmailContent content = emailContentFactory.createUsing(notification);
        return EmailTemplate.builder()
                .content(content)
                .properties(properties);
    }
}
