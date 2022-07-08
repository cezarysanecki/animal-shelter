package pl.devcezz.shelter.adoption.shelter.web

import io.vavr.control.Try
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import pl.devcezz.shelter.adoption.AdoptionTestContext
import pl.devcezz.shelter.adoption.shelter.application.AcceptingProposal
import pl.devcezz.shelter.adoption.shelter.application.CancelingProposal
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShelterController.class)
@ContextConfiguration(classes = [AdoptionTestContext.class])
class ShelterControllerIT extends Specification {

    @Autowired
    private MockMvc mvc

    @MockBean
    private AcceptingProposal acceptingProposal

    @MockBean
    private CancelingProposal cancelingProposal

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
