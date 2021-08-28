package pl.devcezz.animalshelter.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventsBus;

@Configuration(proxyBeanMethods = false)
public class AnimalDatabaseConfig {

    @Bean
    ShelterDatabaseRepository shelterRepository(JdbcTemplate jdbcTemplate, EventsBus eventsBus) {
        return new ShelterDatabaseRepository(jdbcTemplate, eventsBus);
    }
}
