package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;

class AcceptingAnimalTest {

    private Animals animals;
    private ShelterFactory shelterFactory;

    private AcceptingAnimal acceptingAnimal;

    @BeforeEach
    void setUp() {
        animals = mock(Animals.class);
        shelterFactory = mock(ShelterFactory.class);

        acceptingAnimal = new AcceptingAnimal(animals, shelterFactory);
    }

    @Test
    void should_successfully_handle_accept_animal_command() {
        AcceptAnimalCommand command = new AcceptAnimalCommand(
                UUID.randomUUID(),
                "Azor",
                "Dog",
                2
        );

        acceptingAnimal.handle(command);
    }

    private Shelter shelter() {
        Set<ShelterAnimal> shelterAnimals = Stream.of(
                new ShelterAnimal(new AnimalId(UUID.randomUUID()))
        ).toSet();

        return new Shelter(
                new ShelterLimits(3, 2),
                shelterAnimals
        );
    }
}