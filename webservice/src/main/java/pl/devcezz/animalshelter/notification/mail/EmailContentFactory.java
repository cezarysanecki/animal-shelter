package pl.devcezz.animalshelter.notification.mail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.devcezz.animalshelter.notification.dto.Notification;

class EmailContentFactory {

    private final EmailSchemaFactory emailSchemaFactory;
    private final TemplateEngine templateEngine;

    public EmailContentFactory(final EmailSchemaFactory emailSchemaFactory, final TemplateEngine templateEngine) {
        this.emailSchemaFactory = emailSchemaFactory;
        this.templateEngine = templateEngine;
    }

    EmailContent createUsing(Notification notification) {
        EmailSchema schema = emailSchemaFactory.createUsing(notification);

        String content = generateContentFrom(schema);

        return new EmailContent(schema.template().subject(), content);
    }

    String generateContentFrom(final EmailSchema emailSchema) {
        Context context = new Context(null, emailSchema.context().collect().toJavaMap());
        return templateEngine.process(emailSchema.template().templateFile(), context);
    }
}
