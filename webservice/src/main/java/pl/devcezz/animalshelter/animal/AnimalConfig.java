package pl.devcezz.animalshelter.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventsBus;

@Configuration(proxyBeanMethods = false)
public class AnimalConfig {

    @Bean
    ShelterDatabaseRepository shelterRepository(JdbcTemplate jdbcTemplate, EventsBus eventsBus) {
        return new ShelterDatabaseRepository(jdbcTemplate, eventsBus);
    }

    @Bean
    ShelterFactory shelterFactory(ShelterRepository shelterRepository) {
        return new ShelterFactory(shelterRepository);
    }

    @Bean
    AcceptingAnimal acceptingAnimal(Animals animals, ShelterFactory shelterFactory) {
        return new AcceptingAnimal(animals, shelterFactory);
    }
}
