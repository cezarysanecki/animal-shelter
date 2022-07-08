package pl.devcezz.shelter.catalogue

import io.vavr.control.Try
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension.class)
@WebMvcTest(CatalogueController.class)
@ContextConfiguration(classes = [CatalogueTestContext.class])
class CatalogueControllerIT extends Specification {

    @Autowired
    private MockMvc mvc

    @MockBean
    private Catalogue catalogue

    def "should add animal"() {
        given: "Prepare animal."
            given(catalogue.addAnimal(any(), any(), any(), any(), any())).willReturn(Try.success(Result.Success))
            var requestBody = """
                {
                    "name": "Azor",
                    "age": 5,
                    "species": "Dog",
                    "gender": "Male"
                }
                """

        when: "Add animal."
            mvc.perform(post("/shelter/catalogue")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())

        then: "Operation is successful."
            noExceptionThrown()
    }

    def "should update animal"() {
        given: "Prepare animal new data."
            given(catalogue.updateAnimal(any(), any(), any(), any(), any())).willReturn(Try.success(Result.Success))
            var requestBody = """
                {
                    "animalId": "8a52a221-b769-443e-a6a1-8ba29ae76b0a",
                    "name": "Azor",
                    "age": 5,
                    "species": "Dog",
                    "gender": "Male"
                }
                """

        when: "Update animal."
            mvc.perform(put("/shelter/catalogue")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())

        then: "Operation is successful."
            noExceptionThrown()
    }

    def "should delete animal"() {
        given: "Prepare animal to delete."
            given(catalogue.deleteAnimal(any())).willReturn(Try.success(Result.Success))
            var requestBody = """
                {
                    "animalId": "8a52a221-b769-443e-a6a1-8ba29ae76b0a"
                }
                """

        when: "Delete animal."
            mvc.perform(delete("/shelter/catalogue")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())

        then: "Operation is successful."
            noExceptionThrown()
    }

    def "should confirm animal"() {
        given: "Prepare animal to confirm."
            given(catalogue.confirmAnimal(any())).willReturn(Try.success(Result.Success))
            var requestBody = """
                {
                    "animalId": "8a52a221-b769-443e-a6a1-8ba29ae76b0a"
                }
                """

        when: "Confirm animal."
            mvc.perform(post("/shelter/catalogue/confirm")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())

        then: "Operation is successful."
            noExceptionThrown()
    }

}
