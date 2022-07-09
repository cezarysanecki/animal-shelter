package pl.devcezz.shelter.adoption

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import pl.devcezz.shelter.adoption.shelter.application.AcceptingProposal
import pl.devcezz.shelter.adoption.shelter.application.CancelingProposal
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = [AdoptionTestContext.class])
class AdoptionTestWebContext extends Specification {

    @Autowired
    protected MockMvc mvc

    @MockBean
    protected AcceptingProposal acceptingProposal

    @MockBean
    protected CancelingProposal cancelingProposal
}
