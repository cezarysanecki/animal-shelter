package pl.devcezz.shelter.proposal

import org.springframework.context.ApplicationEventPublisher
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent
import spock.lang.Specification

class ProposalTest extends Specification {

    ProposalRepository repository = new InMemoryProposalRepository()
    ApplicationEventPublisher publisher = Mock()

    def handler = new ProposalConfig()
            .proposalEventHandler(repository)
    def facade = new ProposalConfig()
            .proposalFacade(repository, publisher)

    def 'should accept proposal'() {
        given:
            def subjectUuid = anySubjectUuid()

        when:
            handleCreationOfProposalFor(subjectUuid)
        and:
            def proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "PENDING"

        when:
            facade.acceptProposal(subjectUuid)
        and:
            proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "ACCEPTED"
    }

    private handleCreationOfProposalFor(UUID subjectUuid) {
        handler.handleCreatedAnimal(new AnimalCreatedEvent(subjectUuid))
    }

    private Proposal getProposal(UUID subjectUuid) {
        repository.findProposalBy(SubjectId.of(subjectUuid))
                .orElseThrow(IllegalStateException::new)
    }

    private UUID anySubjectUuid() {
        UUID.randomUUID()
    }

}