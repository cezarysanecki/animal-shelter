package pl.devcezz.animalshelter.shelter.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class AnimalIdTest {

    @DisplayName("Should fail because of null id when creating animal id")
    @Test
    void should_fail_because_of_null_id_when_creating_animal_id() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new AnimalId(null))
                .withMessage("id cannot be null");
    }

    @DisplayName("Should succeed when id is proper")
    @Test
    void should_succeed_when_id_is_proper() {
        new AnimalId(UUID.randomUUID());
    }
}