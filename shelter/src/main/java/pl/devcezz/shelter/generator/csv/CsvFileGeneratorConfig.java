package pl.devcezz.shelter.generator.csv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.generator.FileGenerator;

@Configuration
public class CsvFileGeneratorConfig {

    @Bean
    FileGenerator csvGenerator() {
        return new CsvGenerator();
    }
}
