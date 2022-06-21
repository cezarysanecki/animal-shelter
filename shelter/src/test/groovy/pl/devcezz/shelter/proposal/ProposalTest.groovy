package pl.devcezz.shelter.proposal

import org.springframework.context.ApplicationEventPublisher
import pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent
import pl.devcezz.shelter.shared.event.ProposalDecidedEvent
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
        and:
            proposal.getArchives().isEmpty()

        when:
            facade.acceptProposal(subjectUuid)
        and:
            proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "ACCEPTED"
        and:
            proposal.getArchives().size() == 1
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
        and:
            proposal.getArchives().isEmpty()

        when:
            facade.declineProposal(subjectUuid)
        and:
            proposal = getProposal(subjectUuid)

        then:
            proposal.getStatus().name() == "DECLINED"
        and:
            proposal.getArchives().size() == 1
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
        and:
            proposal.getArchives().size() == 1

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

    def 'should emit event when accepting proposal'() {
        given:
            def subjectUuid = anySubjectUuid()

        when:
            handleCreationOfProposalFor(subjectUuid)
        and:
            facade.acceptProposal(subjectUuid)

        then:
            1 * publisher.publishEvent(_ as ProposalDecidedEvent)
    }

    def 'should emit event when declining proposal'() {
        given:
            def subjectUuid = anySubjectUuid()

        when:
            handleCreationOfProposalFor(subjectUuid)
        and:
            facade.declineProposal(subjectUuid)

        then:
            1 * publisher.publishEvent(_ as ProposalDecidedEvent)
    }

    private handleCreationOfProposalFor(UUID subjectUuid) {
        handler.handleCreatedAnimal(new AnimalCreatedEvent(subjectUuid))
    }

    private handleDeletionOfProposalFor(UUID subjectUuid) {
        handler.handleDeletedAnimal(new AnimalDeletedEvent(subjectUuid))
    }

    private Proposal getProposal(UUID subjectUuid) {
        repository.findProposalBySubjectId(SubjectId.of(subjectUuid))
                .orElseThrow(IllegalStateException::new)
    }

    private UUID anySubjectUuid() {
        UUID.randomUUID()
    }

}