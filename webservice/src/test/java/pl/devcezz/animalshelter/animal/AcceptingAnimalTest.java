package pl.devcezz.animalshelter.animal;

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
    void should_() {
        AcceptAnimalCommand command = new AcceptAnimalCommand(
                UUID.randomUUID(),
                "Azor",
                "Dog",
                2
        );

        acceptingAnimal.handle(command);
    }
}