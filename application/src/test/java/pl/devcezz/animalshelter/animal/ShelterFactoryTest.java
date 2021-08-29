package pl.devcezz.animalshelter.animal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.commons.exception.ShelterLimitExceededException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.animal.ShelterFixture.availableAnimals;
import static pl.devcezz.animalshelter.animal.ShelterFixture.shelterLimits;

class ShelterFactoryTest {

    private ShelterRepository shelterRepository;

    private ShelterFactory shelterFactory;

    @BeforeEach
    void setUp() {
        shelterRepository = mock(ShelterRepository.class);

        shelterFactory = new ShelterFactory(shelterRepository);
    }

    @DisplayName("Should create proper shelter")
    @Test
    void should_create_proper_shelter() {
        when(shelterRepository.queryForShelterLimits()).thenReturn(shelterLimits(10, 7));
        when(shelterRepository.queryForAnimalsInShelter()).thenReturn(availableAnimals(6));

        shelterFactory.create();
    }

    @DisplayName("Should fail when there are more animals than space in shelter")
    @Test
    void should_fail_when_there_are_more_animals_than_space_in_shelter() {
        when(shelterRepository.queryForShelterLimits()).thenReturn(shelterLimits(10, 7));
        when(shelterRepository.queryForAnimalsInShelter()).thenReturn(availableAnimals(11));

        assertThatExceptionOfType(ShelterLimitExceededException.class)
                .isThrownBy(() -> shelterFactory.create())
                .withMessage("more animals in shelter than capacity");
    }
}