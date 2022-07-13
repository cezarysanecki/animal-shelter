package pl.devcezz.shelter.adoption.shelter.readmodel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ShelterReadModelConfig {

    @Bean
    ShelterReadModelRepository shelterReadModelRepository(@Qualifier("adoption") JdbcTemplate jdbcTemplate) {
        return new ShelterReadModelRepository(jdbcTemplate);
    }

    @Bean
    ShelterReadModel shelterReadModel(ShelterReadModelRepository shelterReadModelRepository) {
        return new ShelterReadModel(shelterReadModelRepository);
    }
}
