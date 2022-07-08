package pl.devcezz.shelter.catalogue;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.devcezz.shelter.commons.commands.Result;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CatalogueController.class)
@ContextConfiguration(classes = {CatalogueTestContext.class})
public class CatalogueControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Catalogue catalogue;

    @Test
    public void shouldAddAnimal() throws Exception {
        //given
        given(catalogue.addAnimal(any(), any(), any(), any(), any())).willReturn(Try.success(Result.Success));
        var requestBody = "{ \"name\": \"Azor\", \"age\": 5, \"species\": \"Dog\", \"gender\": \"Male\" }";

        //expect
        mvc.perform(post("/shelter/catalogue")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateAnimal() throws Exception {
        //given
        given(catalogue.updateAnimal(any(), any(), any(), any(), any())).willReturn(Try.success(Result.Success));
        var requestBody = """
                    {
                        "animalId": "8a52a221-b769-443e-a6a1-8ba29ae76b0a",
                        "name": "Azor",
                        "age": 5,
                        "species": "Dog",
                        "gender": "Male"
                    }
                """;

        //expect
        mvc.perform(put("/shelter/catalogue")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAnimal() throws Exception {
        //given
        given(catalogue.deleteAnimal(any())).willReturn(Try.success(Result.Success));
        var requestBody = "8a52a221-b769-443e-a6a1-8ba29ae76b0a";

        //expect
        mvc.perform(delete("/shelter/catalogue")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldConfirmAnimal() throws Exception {
        //given
        given(catalogue.confirmAnimal(any())).willReturn(Try.success(Result.Success));
        var requestBody = "8a52a221-b769-443e-a6a1-8ba29ae76b0a";

        //expect
        mvc.perform(post("/shelter/catalogue/confirm")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
