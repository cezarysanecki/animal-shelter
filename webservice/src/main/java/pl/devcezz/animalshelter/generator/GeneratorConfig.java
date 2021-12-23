package pl.devcezz.animalshelter.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.devcezz.animalshelter.shelter.read.AnimalProjection;

@Configuration
class GeneratorConfig {

    @Bean
    FileGeneratorFacade fileGeneratorFacade(final TemplateEngine templateEngine,
                                            final AnimalProjection animalProjection) {
        ShelterPdfGenerator shelterPdfGenerator = new ShelterPdfGenerator(
                new ShelterHtmlPreparer(animalProjection),
                new HtmlContentGenerator(templateEngine),
                new PdfCreator(new ITextRenderer())
        );
        return new FileGeneratorFacade(shelterPdfGenerator);
    }
}
