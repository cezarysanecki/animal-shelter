package pl.devcezz.animalshelter.mail.content;

import pl.devcezz.animalshelter.commons.notification.Notification;

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
