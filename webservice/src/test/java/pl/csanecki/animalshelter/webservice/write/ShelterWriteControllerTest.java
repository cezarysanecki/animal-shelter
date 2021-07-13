package pl.csanecki.animalshelter.webservice.write;

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
import pl.csanecki.animalshelter.webservice.WriteController;
import pl.devcezz.cqrs.command.CommandsBus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WriteController.class)
@ContextConfiguration(classes = MockMvcConfig.class)
class ShelterWriteControllerTest {

    @Test
    void should_admit_animal_to_shelter(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(post("/shelter/animals")
                .content(animalToAdmit())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.LOCATION, matchesPattern(".*/shelter/animals/.*")));
    }

    @Test
    void should_adopt_animal(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(post("/shelter/animals/{id}/adopt", UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    private String animalToAdmit() throws IOException {
        return Files.readString(Path.of("src/test/resources/animal.json"));
    }
}

@Configuration(proxyBeanMethods = false)
class MockMvcConfig {

    @Bean
    CommandsBus fakeCommandsBus() {
        return mock(CommandsBus.class);
    }

    @Bean
    WriteController writeController(CommandsBus commandsBus) {
        return new WriteController(commandsBus);
    }
}