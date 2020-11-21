package pl.csanecki.animalshelter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.controller.AnimalService;
import pl.csanecki.animalshelter.repository.AnimalRepositoryImpl;
import pl.csanecki.animalshelter.service.AnimalRepository;
import pl.csanecki.animalshelter.service.AnimalServiceImpl;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class AnimalConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public AnimalRepository animalRepository(JdbcTemplate jdbcTemplate) {
        return new AnimalRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public AnimalService animalSerivce(AnimalRepository animalRepository) {
        return new AnimalServiceImpl(animalRepository);
    }
}
