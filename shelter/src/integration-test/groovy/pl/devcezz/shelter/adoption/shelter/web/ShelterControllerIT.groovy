package pl.devcezz.shelter.adoption.shelter.web

import io.vavr.control.Try
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import pl.devcezz.shelter.adoption.AdoptionTestWebContext
import pl.devcezz.shelter.commons.commands.Result

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ShelterController.class)
class ShelterControllerIT extends AdoptionTestWebContext {

    def "should accept proposal"() {
        given: "Prepare proposal to accept."
            given(acceptingProposal.acceptProposal(any())).willReturn(Try.success(Result.Success))
            def requestBody = """
                { 
                    "proposalId": "8a52a221-b769-443e-a6a1-8ba29ae76b0a" 
                }
                """

        when: "Accept proposal."
            mvc.perform(post("/shelter/proposals/accept")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())

        then: "Operation is successful."
            noExceptionThrown()
    }

    def "should cancel proposal"() {
        given: "Prepare proposal to cancel."
            given(cancelingProposal.cancelProposal(any())).willReturn(Try.success(Result.Success))
            def requestBody = """
                { 
                    "proposalId": "8a52a221-b769-443e-a6a1-8ba29ae76b0a" 
                }
                """

        when: "Cancel proposal."
            mvc.perform(post("/shelter/proposals/cancel")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())

        then: "Operation is successful."
            noExceptionThrown()
    }

}
