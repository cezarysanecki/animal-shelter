package pl.devcezz.shelter.catalogue

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static pl.devcezz.shelter.catalogue.AnimalFixture.NON_PRESENT_ANIMAL_ID
import static pl.devcezz.shelter.catalogue.AnimalFixture.dog

@SpringBootTest(classes = CatalogueTestContext.class)
class CatalogueDatabaseRepositoryIT extends Specification {

    @Autowired
    CatalogueDatabaseRepository catalogueDatabase

    def 'should be able to save, update and load new animal'() {
        given: "Prepare dog."
            Animal animal = dog()
        when: "Save animal."
            catalogueDatabase.save(animal)
        and: "Update animal."
            catalogueDatabase.update(animal)
        and: "Find animal."
            Option<Animal> dog = catalogueDatabase.findBy(animal.getAnimalId())
        then: "Animal is present."
            dog.isDefined()
            dog.get() == animal
    }

    def 'should not load not present animal'() {
        when: "Find animal."
            Option<Animal> ddd = catalogueDatabase.findBy(NON_PRESENT_ANIMAL_ID)
        then: "There is no animal."
            ddd.isEmpty()
    }

}
