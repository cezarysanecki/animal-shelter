package pl.devcezz.shelter.proposal

import org.springframework.context.ApplicationEventPublisher
import pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent
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

    def 'should decline proposal'() {
        given:
            def subjectUuid = anySubjectUuid()

        when:
            handleCreationOfProposalFor(subjectUuid)
        and:
            def proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "PENDING"

        when:
            facade.declineProposal(subjectUuid)
        and:
            proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "DECLINED"
    }

    def 'should fail when accept or decline proposal which is deleted'() {
        given:
            def subjectUuid = anySubjectUuid()

        when:
            handleCreationOfProposalFor(subjectUuid)
        and:
            handleDeletionOfProposalFor(subjectUuid)
        and:
            def proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "DELETED"

        when:
            facade.acceptProposal(subjectUuid)

        then:
            def error = thrown(ProposalIllegalStateException.class)
            error.getMessage().contains("accept")

        when:
            facade.declineProposal(subjectUuid)

        then:
            error = thrown(ProposalIllegalStateException.class)
            error.getMessage().contains("decline")
    }

    def 'should fail when delete proposal which is already deleted'() {
        given:
            def subjectUuid = anySubjectUuid()

        when:
            handleCreationOfProposalFor(subjectUuid)
        and:
            handleDeletionOfProposalFor(subjectUuid)
        and:
            handleDeletionOfProposalFor(subjectUuid)

        then:
            def error = thrown(ProposalIllegalStateException.class)
            error.getMessage().contains("delete")
    }

    private handleCreationOfProposalFor(UUID subjectUuid) {
        handler.handleCreatedAnimal(new AnimalCreatedEvent(subjectUuid))
    }

    private handleDeletionOfProposalFor(UUID subjectUuid) {
        handler.handleDeletedAnimal(new AnimalDeletedEvent(subjectUuid))
    }

    private Proposal getProposal(UUID subjectUuid) {
        repository.findProposalBy(SubjectId.of(subjectUuid))
                .orElseThrow(IllegalStateException::new)
    }

    private UUID anySubjectUuid() {
        UUID.randomUUID()
    }

}