package pl.devcezz.animalshelter.notification.mail;

import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.mail.dto.EmailContent;

class EmailFactory {

    private final EmailContentFactory contentFactory;
    private final EmailContentProperties contentProperties;

    EmailFactory(final EmailContentFactory contentFactory, final EmailContentProperties contentProperties) {
        this.contentFactory = contentFactory;
        this.contentProperties = contentProperties;
    }

    public Email createEmailFor(final Notification notification) {
        EmailContent content = contentFactory.createUsing(notification);
        return Email.builder()
                .contentProperties(contentProperties)
                .content(content);
    }
}
