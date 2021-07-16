package pl.devcezz.animalshelter.animal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static pl.devcezz.animalshelter.animal.AnimalFixture.buildAnimalWithAge;
import static pl.devcezz.animalshelter.animal.AnimalFixture.buildAnimalWithName;
import static pl.devcezz.animalshelter.animal.AnimalFixture.buildAnimalWithSpecies;

class AnimalTest {

    @DisplayName("Should fail because of wrong name when creating animal")
    @ParameterizedTest(name = "Name \"{0}\" is not proper because {1}")
    @MethodSource("incorrectAnimalName")
    void should_fail_because_of_wrong_name_when_creating_animal(
            String name, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buildAnimalWithName(name))
                .withMessage("name " + reason);
    }

    private static Stream<Arguments> incorrectAnimalName() {
        return Stream.of(
                Arguments.of(null, "cannot be null"),
                Arguments.of("", "must have size between 2 and 11"),
                Arguments.of("     ", "must have size between 2 and 11"),
                Arguments.of("O", "must have size between 2 and 11"),
                Arguments.of("Abrakadabrak", "must have size between 2 and 11"),
                Arguments.of("K   ", "must have size between 2 and 11")
        );
    }

    @DisplayName("Should fail because of wrong age when creating animal")
    @ParameterizedTest(name = "Age \"{0}\" is not proper because {1}")
    @MethodSource("incorrectAnimalAge")
    void should_fail_because_of_wrong_age_when_creating_animal(
            Integer age, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buildAnimalWithAge(age))
                .withMessage("age " + reason);
    }

    private static Stream<Arguments> incorrectAnimalAge() {
        return Stream.of(
                Arguments.of(null, "cannot be null"),
                Arguments.of(-1, "cannot be negative"),
                Arguments.of(31, "cannot be grater than 30")
        );
    }

    @DisplayName("Should fail because of wrong species when creating animal")
    @ParameterizedTest(name = "Species \"{0}\" is not proper because {1}")
    @MethodSource("incorrectAnimalSpecies")
    void should_fail_because_of_wrong_species_when_creating_animal(
            String species, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buildAnimalWithSpecies(species))
                .withMessage("species " + reason);
    }

    private static Stream<Arguments> incorrectAnimalSpecies() {
        return Stream.of(
                Arguments.of(null, "cannot be null"),
                Arguments.of("", "cannot be empty"),
                Arguments.of("     ", "cannot be empty"),
                Arguments.of("Bird", "cannot be of value Bird")
        );
    }

    @DisplayName("Should success when creating animal of specified name")
    @ParameterizedTest(name = "Name \"{0}\" is proper")
    @MethodSource("properAnimalName")
    void should_success_when_creating_animal_of_specified_name(String name) {
        buildAnimalWithName(name);
    }

    private static Stream<Arguments> properAnimalName() {
        return Stream.of(
                Arguments.of("Oz"),
                Arguments.of("Asgar"),
                Arguments.of("Abrakadabra")
        );
    }

    @DisplayName("Should success when creating animal of specified age")
    @ParameterizedTest(name = "Age \"{0}\" is proper")
    @MethodSource("properAnimalAge")
    void should_success_when_creating_animal_of_specified_age(Integer age) {
        buildAnimalWithAge(age);
    }

    private static Stream<Arguments> properAnimalAge() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(12),
                Arguments.of(30)
        );
    }

    @DisplayName("Should success when creating animal of specified species")
    @ParameterizedTest(name = "Species \"{0}\" is proper")
    @MethodSource("properAnimalSpecies")
    void should_success_when_creating_animal_of_specified_species(String species) {
        buildAnimalWithSpecies(species);
    }

    private static Stream<Arguments> properAnimalSpecies() {
        return Stream.of(
                Arguments.of("Dog"),
                Arguments.of("Cat")
        );
    }
}