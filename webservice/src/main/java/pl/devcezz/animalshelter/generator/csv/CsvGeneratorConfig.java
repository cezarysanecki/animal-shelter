package pl.devcezz.animalshelter.generator.csv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.animalshelter.shelter.read.AnimalProjection;

@Configuration
class CsvGeneratorConfig {

    @Bean
    ShelterCsvGenerator shelterCsvGenerator(final AnimalProjection animalProjection) {
        return new ShelterCsvGenerator(animalProjection);
    }
}
