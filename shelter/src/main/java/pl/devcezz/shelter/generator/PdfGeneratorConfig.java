package pl.devcezz.shelter.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModel;
import pl.devcezz.shelter.catalogue.CatalogueReadModelRepository;
import pl.devcezz.shelter.generator.pdf.HtmlContentGenerator;
import pl.devcezz.shelter.generator.pdf.HtmlContextPreparer;
import pl.devcezz.shelter.generator.pdf.PdfCreator;
import pl.devcezz.shelter.generator.pdf.PdfGenerator;

@Configuration
class PdfGeneratorConfig {

    @Bean
    ReportGeneratorFacade fileGeneratorFacade(

            TemplateEngine templateEngine) {
        HtmlContextPreparer htmlContextPreparer = new HtmlContextPreparer(
                shelterReadModel, catalogueReadModelRepository);
        HtmlContentGenerator htmlContentGenerator = new HtmlContentGenerator(templateEngine);
        PdfCreator pdfCreator = new PdfCreator();
        PdfGenerator pdfGenerator = new PdfGenerator(
                htmlContextPreparer, htmlContentGenerator, pdfCreator);
        return new ReportGeneratorFacade(pdfGenerator);
    }
}
