package pl.devcezz.animalshelter.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class AnimalReadConfig {

    @Bean
    AnimalProjection animalProjection(JdbcTemplate jdbcTemplate) {
        return new AnimalDatabaseProjection(jdbcTemplate);
    }
}
