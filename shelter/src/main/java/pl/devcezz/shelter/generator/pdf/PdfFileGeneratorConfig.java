package pl.devcezz.shelter.generator.pdf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

@Configuration
public class PdfFileGeneratorConfig {

    @Bean
    PdfGenerator pdfGenerator(TemplateEngine templateEngine) {
        HtmlContentGenerator htmlContentGenerator = new HtmlContentGenerator(templateEngine);
        PdfCreator pdfCreator = new PdfCreator();
        return new PdfGenerator(htmlContentGenerator, pdfCreator);
    }
}
