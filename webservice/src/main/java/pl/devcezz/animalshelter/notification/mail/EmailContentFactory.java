package pl.devcezz.animalshelter.notification.mail;

import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.mail.dto.EmailContent;

public abstract class EmailContentFactory {

    private final EmailSchemaFactory emailSchemaFactory;

    public EmailContentFactory(final EmailSchemaFactory emailSchemaFactory) {
        this.emailSchemaFactory = emailSchemaFactory;
    }

    public final EmailContent createUsing(Notification notification) {
        EmailSchema schema = emailSchemaFactory.createUsing(notification);

        String content = generateContentFrom(schema);

        return new EmailContent(schema.template().subject(), content);
    }

    abstract String generateContentFrom(final EmailSchema emailSchema);
}
