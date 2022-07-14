package pl.devcezz.shelter.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.generator.external.GeneratorExternalConfig;
import pl.devcezz.shelter.generator.pdf.PdfFileGeneratorConfig;

import java.util.Set;

@Configuration
@Import({GeneratorExternalConfig.class, PdfFileGeneratorConfig.class})
public class ReportGeneratorConfig {

    @Bean
    ReportGeneratorFacade fileGeneratorFacade(
            Set<DataFetcher> dataFetchers,
            Set<FileGenerator> fileGenerators) {
        return new ReportGeneratorFacade(dataFetchers, fileGenerators);
    }
}
