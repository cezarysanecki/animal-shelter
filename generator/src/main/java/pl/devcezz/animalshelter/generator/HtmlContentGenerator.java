package pl.devcezz.animalshelter.generator;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

class HtmlContentGenerator {

    private final TemplateEngine templateEngine;

    HtmlContentGenerator(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    HtmlContent process(HtmlInput htmlInput) {
        Context context = new Context(null, htmlInput.contextMap().toJavaMap());

        return new HtmlContent(templateEngine.process(htmlInput.template(), context));
    }
}
