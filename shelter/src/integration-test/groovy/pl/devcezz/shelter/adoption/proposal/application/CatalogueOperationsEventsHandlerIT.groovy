package pl.devcezz.shelter.adoption.proposal.application

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.AdoptionTestContext
import pl.devcezz.shelter.adoption.proposal.model.*
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.pendingProposal
import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalCreatedEvent.animalCreatedNow
import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalDeletedEvent.animalDeletedNow

@SpringBootTest(classes = AdoptionTestContext.class)
class AnimalOperationsEventsHandlerIT extends Specification {

    @Autowired
    Proposals proposalRepository

    @Autowired
    AnimalOperationsEventsHandler eventHandler

    def 'should register proposal after adding new animal'() {
        given:
            def animalId = anyAnimalId()
        when:
            eventHandler.handle(animalCreatedNow(animalId))
        then:
            proposalIsPersistedAs(PendingProposal.class, prepareProposalId(animalId))
    }

    def 'should delete proposal after deleting new animal'() {
        given:
            def animalId = anyAnimalId()
        and:
            proposalRepository.save(pendingProposal(prepareProposalId(animalId)))
        when:
            eventHandler.handle(animalDeletedNow(animalId))
        then:
            proposalIsPersistedAs(DeletedProposal.class, prepareProposalId(animalId))
    }

    void proposalIsPersistedAs(Class<?> clazz, ProposalId proposalId) {
        Proposal proposal = loadPersistedProposal(proposalId)
        assert proposal.class == clazz
    }

    Proposal loadPersistedProposal(ProposalId proposalId) {
        Option<Proposal> loaded = proposalRepository.findBy(proposalId)
        Proposal proposal = loaded.getOrElseThrow({
            new IllegalStateException("should have been persisted")
        })
        return proposal
    }

    private ProposalId prepareProposalId(UUID animalId) {
        return ProposalId.of(animalId)
    }

    private UUID anyAnimalId() {
        return UUID.randomUUID()
    }

}
