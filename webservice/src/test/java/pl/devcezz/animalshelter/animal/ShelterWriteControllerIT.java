package pl.devcezz.animalshelter.animal;

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
import pl.devcezz.animalshelter.infrastructure.ErrorHandler;
import pl.devcezz.cqrs.command.CommandsBus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShelterWriteController.class)
@ContextConfiguration(classes = MockMvcConfig.class)
class ShelterWriteControllerIT {

    @Test
    void should_accept_animal_into_shelter(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(post("/shelter/animals/accept")
                .content(animalToAccept())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_reject_acceptance_of_animal_into_shelter(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(post("/shelter/animals/accept")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("name.[*]").value("must not be blank"))
                .andExpect(jsonPath("age.[*]").value("must not be null"))
                .andExpect(jsonPath("species.[*]").value(containsInAnyOrder("must be one of the species: Cat, Dog", "must not be blank")));
    }

    @Test
    void should_adopt_animal(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(post("/shelter/animals/adopt")
                .content("38400000-8cf0-11bd-b23e-10b96e4ef00d")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_edit_animal(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(put("/shelter/animals")
                .content(animalToEdit())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void should_delete_animal(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(delete("/shelter/animals")
                .content("38400000-8cf0-11bd-b23e-10b96e4ef00d")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String animalToAccept() throws IOException {
        return Files.readString(Path.of("src/test/resources/animal.json"));
    }

    private String animalToEdit() throws IOException {
        return Files.readString(Path.of("src/test/resources/animal-edit.json"));
    }
}

@Configuration(proxyBeanMethods = false)
class MockMvcConfig {

    @Bean
    CommandsBus fakeCommandsBus() {
        return mock(CommandsBus.class);
    }

    @Bean
    ShelterWriteController writeController(CommandsBus commandsBus) {
        return new ShelterWriteController(commandsBus);
    }

    @Bean
    ErrorHandler errorHandler() {
        return new ErrorHandler();
    }
}