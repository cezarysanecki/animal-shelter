package pl.devcezz.animalshelter.generator.pdf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import pl.devcezz.animalshelter.shelter.read.AnimalProjection;

@Configuration
class PdfGeneratorConfig {

    @Bean
    ShelterPdfGenerator shelterPdfGenerator(final TemplateEngine templateEngine,
                                            final AnimalProjection animalProjection) {
        return new ShelterPdfGenerator(
                new ShelterHtmlPreparer(animalProjection),
                new HtmlContentGenerator(templateEngine),
                new PdfCreator()
        );
    }
}
