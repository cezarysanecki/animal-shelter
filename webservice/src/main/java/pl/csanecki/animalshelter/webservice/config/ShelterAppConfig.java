package pl.csanecki.animalshelter.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.webservice.repository.AnimalRepositoryImpl;
import pl.csanecki.animalshelter.domain.service.AnimalRepository;
import pl.csanecki.animalshelter.domain.service.ShelterService;

@Configuration(proxyBeanMethods = false)
public class ShelterAppConfig {

    @Bean
    public AnimalRepository animalRepository(JdbcTemplate jdbcTemplate) {
        return new AnimalRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public ShelterService shelterService(AnimalRepository animalRepository) {
        return new ShelterService(animalRepository);
    }
}
