package pl.devcezz.shelter.generator.pdf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class HtmlContentGenerator {

    private final TemplateEngine templateEngine;

    HtmlContent process(HtmlContext htmlContext) {
        Context context = new Context(null, htmlContext.contextMap().toJavaMap());

        return new HtmlContent(templateEngine.process(htmlContext.template(), context));
    }
}
