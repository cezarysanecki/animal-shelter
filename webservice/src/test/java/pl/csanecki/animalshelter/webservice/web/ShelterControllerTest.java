package pl.csanecki.animalshelter.webservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = MockMvcConfig.class)
class ShelterControllerTest {

    @Test
    void should_admit_animal_to_shelter(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(post("/shelter/animals")
                .content(animalToAdmit())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private String animalToAdmit() throws IOException {
        return Files.readString(Path.of("src/test/resources/animal.json"));
    }
}

@Configuration(proxyBeanMethods = false)
class MockMvcConfig {

    @Bean
    ShelterService fakeShelterService() {
        return new ShelterService(new ShelterInMemoryRepository());
    }

    @Bean
    ShelterController shelterController(ShelterService shelterService) {
        return new ShelterController(shelterService);
    }
}