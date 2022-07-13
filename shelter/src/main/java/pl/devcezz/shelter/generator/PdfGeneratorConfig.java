package pl.devcezz.shelter.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModel;
import pl.devcezz.shelter.catalogue.CatalogueReadModelRepository;

@Configuration
class PdfGeneratorConfig {

    @Bean
    FileGeneratorFacade fileGeneratorFacade(
            ShelterReadModel shelterReadModel,
            CatalogueReadModelRepository catalogueReadModelRepository,
            TemplateEngine templateEngine) {
        HtmlPreparer htmlPreparer = new HtmlPreparer(
                shelterReadModel, catalogueReadModelRepository);
        HtmlContentGenerator htmlContentGenerator = new HtmlContentGenerator(templateEngine);
        PdfCreator pdfCreator = new PdfCreator();
        PdfGenerator pdfGenerator = new PdfGenerator(
                htmlPreparer, htmlContentGenerator, pdfCreator);
        return new FileGeneratorFacade(pdfGenerator);
    }
}
