package pl.csanecki.animalshelter.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.webservice.controller.AnimalService;
import pl.csanecki.animalshelter.webservice.repository.AnimalRepositoryImpl;
import pl.csanecki.animalshelter.webservice.service.AnimalRepository;
import pl.csanecki.animalshelter.webservice.service.AnimalServiceImpl;

@Configuration(proxyBeanMethods = false)
public class AnimalConfig {

    @Bean
    public AnimalRepository animalRepository(JdbcTemplate jdbcTemplate) {
        return new AnimalRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public AnimalService animalService(AnimalRepository animalRepository) {
        return new AnimalServiceImpl(animalRepository);
    }
}
