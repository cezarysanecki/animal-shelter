package pl.devcezz.animalshelter.shelter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.buildAnimalWithAge;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.buildAnimalWithName;

class AnimalTest {

    @DisplayName("Should fail because of wrong name when creating animal")
    @ParameterizedTest(name = "Name \"{0}\" is not proper because {1}")
    @MethodSource("incorrectAnimalNames")
    void should_fail_because_of_wrong_name_when_creating_animal(
            String name, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buildAnimalWithName(name))
                .withMessage("name " + reason);
    }

    private static Stream<Arguments> incorrectAnimalNames() {
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
    @MethodSource("incorrectAnimalAges")
    void should_fail_because_of_wrong_age_when_creating_animal(
            Integer age, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buildAnimalWithAge(age))
                .withMessage("age " + reason);
    }

    private static Stream<Arguments> incorrectAnimalAges() {
        return Stream.of(
                Arguments.of(null, "cannot be null"),
                Arguments.of(-1, "cannot be negative"),
                Arguments.of(31, "cannot be grater than 30")
        );
    }

    @DisplayName("Should success when creating animal of specified name")
    @ParameterizedTest(name = "Name \"{0}\" is proper")
    @MethodSource("properAnimalNames")
    void should_success_when_creating_animal_of_specified_name(String name) {
        buildAnimalWithName(name);
    }

    private static Stream<Arguments> properAnimalNames() {
        return Stream.of(
                Arguments.of("Oz"),
                Arguments.of("Asgar"),
                Arguments.of("Abrakadabra")
        );
    }

    @DisplayName("Should success when creating animal of specified age")
    @ParameterizedTest(name = "Age \"{0}\" is proper")
    @MethodSource("properAnimalAges")
    void should_success_when_creating_animal_of_specified_age(Integer age) {
        buildAnimalWithAge(age);
    }

    private static Stream<Arguments> properAnimalAges() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(12),
                Arguments.of(30)
        );
    }
}