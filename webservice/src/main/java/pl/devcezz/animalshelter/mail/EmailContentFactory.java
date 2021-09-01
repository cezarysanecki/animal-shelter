package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.mail.model.EmailContent;
import pl.devcezz.animalshelter.mail.model.EmailTemplate;

public abstract class EmailContentFactory {

    private final ContentFactory contentFactory;

    public EmailContentFactory(final ContentFactory contentFactory) {
        this.contentFactory = contentFactory;
    }

    public final EmailTemplate createUsing(Notification notification) {
        EmailContent emailContent = contentFactory.create(notification);

        String text = generateText(emailContent);

        return new EmailTemplate(emailContent.template().subject(), text);
    }

    abstract String generateText(final EmailContent emailContent);
}
