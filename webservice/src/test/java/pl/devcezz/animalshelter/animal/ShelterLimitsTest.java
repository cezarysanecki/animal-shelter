package pl.devcezz.animalshelter.animal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ShelterLimitsTest {

    @DisplayName("Should create shelter limits")
    @ParameterizedTest(name = "Capacity \"{0}\" and safe threshold \"{1}\" are proper values")
    @MethodSource("properValues")
    void should_create_shelter_limits(Integer capacity, Integer safeThreshold) {
        new ShelterLimits(capacity, safeThreshold);
    }

    private static Stream<Arguments> properValues() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(10, 0),
                Arguments.of(10, 10),
                Arguments.of(5, 3)
        );
    }

    @DisplayName("Should fail when creating shelter limits")
    @ParameterizedTest(name = "Capacity \"{0}\" and safe threshold \"{1}\" are not proper values because {2}")
    @MethodSource("improperValues")
    void should_fail_when_creating_shelter_limits(
            Integer capacity, Integer safeThreshold, String reason
    ) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ShelterLimits(capacity, safeThreshold))
                .withMessage(reason);
    }

    private static Stream<Arguments> improperValues() {
        return Stream.of(
                Arguments.of(-1, 10, "capacity cannot be negative"),
                Arguments.of(10, -1, "safe threshold cannot be negative"),
                Arguments.of(4, 5, "safe threshold cannot be greater than capacity")
        );
    }
}