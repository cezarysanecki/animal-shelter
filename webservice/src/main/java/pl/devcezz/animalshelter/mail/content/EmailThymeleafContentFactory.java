package pl.devcezz.animalshelter.mail.content;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

class EmailThymeleafContentFactory extends EmailContentFactory {

    private final TemplateEngine templateEngine;

    EmailThymeleafContentFactory(final EmailSchemaFactory emailSchemaFactory, final TemplateEngine templateEngine) {
        super(emailSchemaFactory);
        this.templateEngine = templateEngine;
    }

    @Override
    String generateContentFrom(final EmailSchema emailSchema) {
        Context context = new Context(null, emailSchema.context().collect().toJavaMap());
        return templateEngine.process(emailSchema.template().templateFile(), context);
    }
}
