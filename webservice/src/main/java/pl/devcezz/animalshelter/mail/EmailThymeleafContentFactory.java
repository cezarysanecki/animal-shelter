package pl.devcezz.animalshelter.mail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.devcezz.animalshelter.mail.model.EmailContent;

public class EmailThymeleafContentFactory extends EmailContentFactory {

    private final TemplateEngine templateEngine;

    public EmailThymeleafContentFactory(final ContentFactory contentFactory, final TemplateEngine templateEngine) {
        super(contentFactory);
        this.templateEngine = templateEngine;
    }

    @Override
    String generateText(final EmailContent emailContent) {
        Context context = new Context(null, emailContent.context().collect().toJavaMap());
        return templateEngine.process(emailContent.template().content(), context);
    }
}
