package pl.devcezz.shelter.catalogue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
@Import({CatalogueDatabaseConfig.class})
public class CatalogueConfig {

    @Bean
    AnimalRepository animalRepository(@Qualifier("catalogue") JdbcTemplate jdbcTemplate) {
        return new AnimalRepository(jdbcTemplate);
    }

    @Bean
    AnimalFacade animalFacade(
            AnimalRepository animalRepository,
            DomainEvents publisher) {
        return new AnimalFacade(animalRepository, publisher);
    }

    @Bean
    AnimalEventHandler animalEventHandler(
            AnimalRepository animalRepository) {
        return new AnimalEventHandler(animalRepository);
    }
}