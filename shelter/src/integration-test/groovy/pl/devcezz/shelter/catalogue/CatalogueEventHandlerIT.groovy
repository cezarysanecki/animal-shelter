package pl.devcezz.shelter.catalogue


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyConfirmed.proposalAlreadyConfirmedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.catalogue.AnimalFixture.dog

@SpringBootTest(classes = CatalogueTestContext.class)
class CatalogueEventHandlerIT extends Specification {

    @Autowired
    CatalogueDatabase catalogueDatabase

    @Autowired
    CatalogueEventHandler eventHandler

    def 'should register animal after accepting it'() {
        given:
            Animal animal = dog()
        and:
            catalogueDatabase.saveNew(animal)
        when:
            eventHandler.handle(proposalAcceptedNow(prepareProposalId(animal.getAnimalId())))
        then:
            def dog = catalogueDatabase.findBy(animal.getAnimalId())
            dog.get().getStatus() == Status.REGISTERED
    }

    def 'should register animal after confirming it'() {
        given:
            Animal animal = dog()
        and:
            catalogueDatabase.saveNew(animal)
        when:
            eventHandler.handle(proposalAlreadyConfirmedNow(prepareProposalId(animal.getAnimalId())))
        then:
            def dog = catalogueDatabase.findBy(animal.getAnimalId())
            dog.get().getStatus() == Status.REGISTERED
    }

    private ProposalId prepareProposalId(AnimalId animalId) {
        return ProposalId.of(animalId.getValue())
    }

}
