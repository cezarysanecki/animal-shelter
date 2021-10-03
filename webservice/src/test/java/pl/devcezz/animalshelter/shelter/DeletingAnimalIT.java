package pl.devcezz.animalshelter.shelter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import pl.devcezz.animalshelter.shelter.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.model.AnimalId;
import pl.devcezz.cqrs.event.EventsBus;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.animal;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.anyAnimalId;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.deleteAnimalCommand;

@SpringBootTest(classes = { ShelterConfig.class, DeletingAnimalIT.Config.class })
@ActiveProfiles("container")
@Testcontainers
class DeletingAnimalIT {

    private final static EventsBus EVENTS_BUS = mock(EventsBus.class);

    private final AnimalId animalId = anyAnimalId();

    @Container
    private static final MySQLContainer<?> DB_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"))
            .withUsername("test")
            .withPassword("test");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @BeforeEach
    void resetMock() {
        Mockito.reset(EVENTS_BUS);
    }

    @Test
    @Transactional
    @DisplayName("Should delete animal which is available")
    void should_delete_animal_which_is_available(
            @Autowired DeletingAnimal deletingAnimal,
            @Autowired ShelterDatabaseRepository repository
    ) {
        repository.save(animal(animalId));
        DeleteAnimalCommand command = deleteAnimalCommand(animalId);

        deletingAnimal.handle(command);

        assertThat(repository.queryForAvailableAnimals()).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Should fail when deleting animal which is not in shelter")
    void should_fail_when_deleting_animal_which_is_not_in_shelter(
            @Autowired DeletingAnimal deletingAnimal
    ) {
        DeleteAnimalCommand command = deleteAnimalCommand(animalId);

        assertThatThrownBy(() -> deletingAnimal.handle(command))
            .isInstanceOf(NotFoundAnimalInShelterException.class);
    }

    @Test
    @Transactional
    @DisplayName("Should fail when deleting animal which is already adopted")
    void should_fail_when_deleting_animal_which_is_already_adopted(
            @Autowired DeletingAnimal deletingAnimal,
            @Autowired ShelterDatabaseRepository repository
    ) {
        repository.save(animal(animalId));
        repository.adopt(new AvailableAnimal(animalId));
        DeleteAnimalCommand command = deleteAnimalCommand(animalId);

        assertThatThrownBy(() -> deletingAnimal.handle(command))
                .isInstanceOf(AnimalAlreadyAdoptedException.class);
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    static class Config extends ShelterDatabaseConfig {

        @Bean
        EventsBus eventsBus() {
            return EVENTS_BUS;
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