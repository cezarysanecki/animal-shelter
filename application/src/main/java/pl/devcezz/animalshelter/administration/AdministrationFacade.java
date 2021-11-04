package pl.devcezz.animalshelter.administration;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.administration.dto.ZookeeperContact;

public class AdministrationFacade {

    private final AdministrationRepository administrationRepository;

    public AdministrationFacade(final AdministrationRepository administrationRepository) {
        this.administrationRepository = administrationRepository;
    }

    public Set<ZookeeperContact> enquireZookeepersContacts() {
        return administrationRepository.findAllZookeepersContacts();
    }
}
