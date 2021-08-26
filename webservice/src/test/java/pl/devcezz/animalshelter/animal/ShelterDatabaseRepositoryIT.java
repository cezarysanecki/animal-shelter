package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.devcezz.cqrs.event.EventsBus;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static pl.devcezz.animalshelter.animal.fixture.AnimalFixture.animal;

@SpringBootTest(classes = ShelterDatabaseRepositoryIT.Config.class)
@ActiveProfiles("container")
@Testcontainers
class ShelterDatabaseRepositoryIT {

    @Container
    private static final MySQLContainer<?> DB_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"))
            .withUsername("test")
            .withPassword("test");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    @Transactional
    @DisplayName("Should fetch animals which are in shelter")
    void should_fetch_animals_which_are_in_shelter(@Autowired ShelterDatabaseRepository repository) {
        Animal animal = animal();
        repository.save(animal);

        Set<ShelterAnimal> shelterAnimals = repository.queryForAnimalsInShelter();

        assertThat(shelterAnimals).containsOnly(new ShelterAnimal(animal.getId()));
    }

    @Test
    @Transactional
    @DisplayName("Should fetch shelter limits")
    void should_fetch_shelter_limits(@Autowired ShelterDatabaseRepository repository) {
        ShelterLimits shelterLimits = repository.queryForShelterLimits();

        assertThat(shelterLimits.safeThreshold()).isEqualTo(7);
        assertThat(shelterLimits.capacity()).isEqualTo(10);
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    static class Config extends AnimalDatabaseConfig {

        @Bean
        EventsBus eventsBus() {
            return mock(EventsBus.class);
        }

        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(DB_CONTAINER.getJdbcUrl())
                    .username(DB_CONTAINER.getUsername())
                    .password(DB_CONTAINER.getPassword())
                    .build();
        }
    }
}