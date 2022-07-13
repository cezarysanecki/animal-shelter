package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class HtmlContentGenerator {

    private final TemplateEngine templateEngine;

    HtmlContent process(HtmlInput htmlInput) {
        Context context = new Context(null, htmlInput.contextMap().toJavaMap());

        return new HtmlContent(templateEngine.process(htmlInput.template(), context));
    }
}
