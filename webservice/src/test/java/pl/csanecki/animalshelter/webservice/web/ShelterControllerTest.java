package pl.csanecki.animalshelter.webservice.web;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.command.Result;
import pl.csanecki.animalshelter.domain.model.AnimalId;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.csanecki.animalshelter.webservice.model.AnimalFixture.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShelterController.class)
@ContextConfiguration(classes = MockMvcConfig.class)
class ShelterControllerTest {

    AnimalId animalId = anyAnimalId();

    @Test
    void should_admit_animal_to_shelter(@Autowired MockMvc mockMvc, @Autowired ShelterService shelterService) throws Exception {
        given(shelterService.acceptIntoShelter(any())).willReturn(animalId);

        mockMvc.perform(post("/shelter/animals")
                .content(animalToAdmit())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.LOCATION, endsWith("/shelter/animals/" + animalId.getAnimalId())));
    }

    @Test
    void should_return_animal_details_by_animal_id(@Autowired MockMvc mockMvc, @Autowired ShelterService shelterService) throws Exception {
        AnimalDetails animalDetails = animalInShelter(65);

        given(shelterService.getAnimalDetails(animalId)).willReturn(animalDetails);

        mockMvc.perform(get("/shelter/animals/{id}", animalId.getAnimalId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(animalDetails.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(animalDetails.getName())))
                .andExpect(jsonPath("$.kind", is(animalDetails.getKind())))
                .andExpect(jsonPath("$.age", is(animalDetails.getAge())))
                .andExpect(jsonPath("$.admittedAt").isNotEmpty())
                .andExpect(jsonPath("$.adoptedAt").value(nullValue()));
    }

    @Test
    void should_return_animals_short_info(@Autowired MockMvc mockMvc, @Autowired ShelterService shelterService) throws Exception {
        List<AnimalShortInfo> animals = animalsInShelter();

        given(shelterService.getAnimalsInfo()).willReturn(animals);

        mockMvc.perform(get("/shelter/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*]]", hasSize(animals.size())));
    }

    @Test
    void should_adopt_animal(@Autowired MockMvc mockMvc, @Autowired ShelterService shelterService) throws Exception {
        given(shelterService.adoptAnimal(animalId)).willReturn(Try.success(Result.SUCCESS));

        mockMvc.perform(post("/shelter/animals/{id}/adopt", animalId.getAnimalId()))
                .andExpect(status().isOk());
    }

    private String animalToAdmit() throws IOException {
        return Files.readString(Path.of("src/test/resources/animal.json"));
    }
}

@Configuration(proxyBeanMethods = false)
class MockMvcConfig {

    @Bean
    ShelterService fakeShelterService() {
        return mock(ShelterService.class);
    }

    @Bean
    ShelterController shelterController(ShelterService shelterService) {
        return new ShelterController(shelterService);
    }
}