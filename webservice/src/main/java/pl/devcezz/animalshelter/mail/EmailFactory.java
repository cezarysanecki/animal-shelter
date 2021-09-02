package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.mail.content.EmailContent;
import pl.devcezz.animalshelter.mail.content.EmailContentFactory;

class EmailFactory {

    private final EmailContentFactory contentFactory;
    private final EmailContentProperties contentProperties;

    EmailFactory(final EmailContentFactory contentFactory, final EmailContentProperties contentProperties) {
        this.contentFactory = contentFactory;
        this.contentProperties = contentProperties;
    }

    Email createEmailFor(final Notification notification) {
        EmailContent content = contentFactory.createUsing(notification);
        return Email.builder()
                .contentProperties(contentProperties)
                .content(content);
    }
}
