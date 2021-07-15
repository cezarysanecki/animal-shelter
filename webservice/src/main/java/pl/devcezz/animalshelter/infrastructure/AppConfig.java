package pl.devcezz.animalshelter.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventsBus;

@Configuration(proxyBeanMethods = false)
class AppConfig {

    @Bean
    ShelterDatabaseRepository shelterRepository(JdbcTemplate jdbcTemplate, EventsBus eventsBus) {
        return new ShelterDatabaseRepository(jdbcTemplate, eventsBus);
    }
}
