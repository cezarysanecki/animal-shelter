package pl.csanecki.animalshelter.webservice.web;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.csanecki.animalshelter.domain.model.AnimalId;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.csanecki.animalshelter.webservice.model.AnimalFixture.anyAnimalId;

@RunWith(SpringRunner.class)
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
                .andExpect(header().string(HttpHeaders.LOCATION, "/shelter/animals/" + animalId.getAnimalId()));
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