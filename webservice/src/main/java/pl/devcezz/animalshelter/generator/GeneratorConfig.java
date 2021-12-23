package pl.devcezz.animalshelter.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.animalshelter.generator.csv.ShelterCsvGenerator;
import pl.devcezz.animalshelter.generator.pdf.ShelterPdfGenerator;

@Configuration
class GeneratorConfig {

    @Bean
    FileGeneratorFacade fileGeneratorFacade(final ShelterPdfGenerator shelterPdfGenerator,
                                            final ShelterCsvGenerator shelterCsvGenerator) {
        return new FileGeneratorFacade(shelterPdfGenerator, shelterCsvGenerator);
    }
}
