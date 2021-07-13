package pl.csanecki.animalshelter.webservice.web;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.service.ShelterRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.csanecki.animalshelter.webservice.model.AnimalFixture.animalInShelter;
import static pl.csanecki.animalshelter.webservice.model.AnimalFixture.animalsInShelter;
import static pl.csanecki.animalshelter.webservice.model.AnimalFixture.anyAnimalId;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShelterController.class)
@ContextConfiguration(classes = MockMvcConfig.class)
class AcceptingAnimalControllerTest {

    long animalId = anyAnimalId();

    @Test
    void should_return_animal_details_by_animal_id(@Autowired MockMvc mockMvc, @Autowired ShelterRepository shelterRepository) throws Exception {
        AnimalDetails animalDetails = animalInShelter(65);

        given(shelterRepository.getAnimalDetails(animalId)).willReturn(Option.of(animalDetails));

        mockMvc.perform(get("/shelter/animals/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(animalDetails.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(animalDetails.getName())))
                .andExpect(jsonPath("$.kind", is(animalDetails.getKind().name())))
                .andExpect(jsonPath("$.age", is(animalDetails.getAge())))
                .andExpect(jsonPath("$.admittedAt").isNotEmpty())
                .andExpect(jsonPath("$.adoptedAt").value(nullValue()));
    }

    @Test
    void should_return_animals_short_info(@Autowired MockMvc mockMvc, @Autowired ShelterRepository shelterRepository) throws Exception {
        List<AnimalShortInfo> animals = animalsInShelter();

        given(shelterRepository.getAnimalsInfo()).willReturn(animals);

        mockMvc.perform(get("/shelter/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*]]", hasSize(animals.size())));
    }
}

@Configuration(proxyBeanMethods = false)
class MockMvcConfig {

    @Bean
    ShelterRepository fakeShelterService() {
        return mock(ShelterRepository.class);
    }

    @Bean
    ShelterController shelterController(ShelterRepository shelterRepository) {
        return new ShelterController(shelterRepository);
    }
}