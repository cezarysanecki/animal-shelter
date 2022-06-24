package pl.devcezz.shelter.catalogue;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Import({CatalogueDatabaseConfig.class})
public class CatalogueConfig {

    @Bean
    AnimalRepository animalRepository(JdbcTemplate jdbcTemplate) {
        return new AnimalRepository(jdbcTemplate);
    }

    @Bean
    AnimalFacade animalFacade(
            AnimalRepository animalRepository,
            ApplicationEventPublisher eventPublisher) {
        return new AnimalFacade(animalRepository, eventPublisher);
    }

    @Bean
    AnimalEventHandler animalEventHandler(
            AnimalRepository animalRepository) {
        return new AnimalEventHandler(animalRepository);
    }
}