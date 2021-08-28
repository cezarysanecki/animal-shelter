package pl.devcezz.animalshelter.animal;

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
import pl.devcezz.animalshelter.animal.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;
import pl.devcezz.cqrs.event.EventsBus;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static pl.devcezz.animalshelter.animal.fixture.AnimalFixture.adoptAnimalCommand;
import static pl.devcezz.animalshelter.animal.fixture.AnimalFixture.animal;
import static pl.devcezz.animalshelter.animal.fixture.AnimalFixture.anyAnimalId;

@SpringBootTest(classes = { AnimalConfig.class, AdoptingAnimalIT.Config.class })
@ActiveProfiles("container")
@Testcontainers
class AdoptingAnimalIT {

    private final AnimalId animalId = anyAnimalId();

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
    @DisplayName("Should adopt animal which is in shelter")
    void should_adopt_animal_which_is_in_shelter(
            @Autowired AdoptingAnimal adoptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        addAnimalToShelter(repository);
        AdoptAnimalCommand command = adoptAnimalCommand(animalId);

        adoptingAnimal.handle(command);

        verify(eventsBus).publish(isA(AnimalAdoptionSucceeded.class));
        assertThat(repository.queryForAnimalsInShelter()).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Should fail when adopting animal which is not in shelter")
    void should_fail_when_adopting_animal_which_is_not_in_shelter(
            @Autowired AdoptingAnimal adoptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        AdoptAnimalCommand command = adoptAnimalCommand(animalId);

        assertThatThrownBy(() -> adoptingAnimal.handle(command))
            .isInstanceOf(NotFoundAnimalInShelterException.class);

        verify(eventsBus, never()).publish(any());
    }

    private void addAnimalToShelter(ShelterDatabaseRepository repository) {
        repository.save(animal(animalId));
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