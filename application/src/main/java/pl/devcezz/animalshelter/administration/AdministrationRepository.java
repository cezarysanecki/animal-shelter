package pl.devcezz.animalshelter.administration;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.administration.dto.ZookeeperContact;

public interface AdministrationRepository {

    Set<ZookeeperContact> findAllZookeepersContacts();
}
