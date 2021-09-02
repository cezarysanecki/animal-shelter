package pl.devcezz.animalshelter.mail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.devcezz.animalshelter.mail.model.EmailSchema;

public class EmailThymeleafContentFactory extends EmailContentFactory {

    private final TemplateEngine templateEngine;

    public EmailThymeleafContentFactory(final EmailSchemaFactory emailSchemaFactory, final TemplateEngine templateEngine) {
        super(emailSchemaFactory);
        this.templateEngine = templateEngine;
    }

    @Override
    String generateContentFrom(final EmailSchema emailSchema) {
        Context context = new Context(null, emailSchema.context().collect().toJavaMap());
        return templateEngine.process(emailSchema.template().templateFile(), context);
    }
}
