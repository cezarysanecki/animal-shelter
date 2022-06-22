package pl.devcezz.shelter.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.CrudRepository;
import pl.devcezz.shelter.shelter.model.Shelter;
import pl.devcezz.shelter.shelter.model.ShelterEvent;
import pl.devcezz.shelter.shelter.model.ShelterFactory;
import pl.devcezz.shelter.shelter.model.Shelters;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SheltersDatabaseRepository implements Shelters {

    private final AcceptedProposalsDatabaseRepository acceptedProposalsDatabaseRepository;
    private final DomainModelMapper domainModelMapper;
    private final ApplicationEventPublisher publisher;

    @Override
    public Shelter prepareShelter() {
        ShelterDatabaseEntity entity = prepareShelterEntity();
        return domainModelMapper.map(entity);
    }

    @Override
    public Shelter publish(ShelterEvent event) {
        Shelter result = handleEvent(event);
        publisher.publishEvent(event);
        return result;
    }

    private Shelter handleEvent(ShelterEvent event) {
        ShelterDatabaseEntity entity = prepareShelterEntity();
        entity = entity.handle(event);
        entity = save(entity);
        return domainModelMapper.map(entity);
    }

    private ShelterDatabaseEntity prepareShelterEntity() {
        return new ShelterDatabaseEntity(acceptedProposalsDatabaseRepository.findAll());
    }

    private ShelterDatabaseEntity save(ShelterDatabaseEntity entity) {
        acceptedProposalsDatabaseRepository.saveAll(entity.acceptedProposals);
        return entity;
    }
}

interface AcceptedProposalsDatabaseRepository extends CrudRepository<AcceptedProposalDatabaseEntity, Long> {

    Set<AcceptedProposalDatabaseEntity> findAll();
}

@AllArgsConstructor
class DomainModelMapper {

    private final ShelterFactory shelterFactory;

    Shelter map(ShelterDatabaseEntity entity) {
        return shelterFactory.create(
                entity.countAcceptedProposals());
    }
}
