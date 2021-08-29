package pl.devcezz.animalshelter.animal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static pl.devcezz.animalshelter.animal.model.AnimalFixture.buildAnimalWithSpecies;

class SpeciesTest {

    @DisplayName("Should fail because of wrong species value")
    @ParameterizedTest(name = "Species \"{0}\" is not proper because {1}")
    @MethodSource("incorrectSpeciesValues")
    void should_fail_because_of_wrong_species_value(
            String species, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> buildAnimalWithSpecies(species))
                .withMessage("species " + reason);
    }

    private static Stream<Arguments> incorrectSpeciesValues() {
        return Stream.of(
                Arguments.of(null, "cannot be null"),
                Arguments.of("", "cannot be empty"),
                Arguments.of("     ", "cannot be empty"),
                Arguments.of("Bird", "cannot be of value Bird")
        );
    }

    @DisplayName("Should success when species value is proper")
    @ParameterizedTest(name = "Species \"{0}\" is proper")
    @MethodSource("properSpeciesValues")
    void should_success_when_species_value_is_proper(String species) {
        buildAnimalWithSpecies(species);
    }

    private static Stream<Arguments> properSpeciesValues() {
        return Stream.of(
                Arguments.of("Dog"),
                Arguments.of("Cat")
        );
    }
}