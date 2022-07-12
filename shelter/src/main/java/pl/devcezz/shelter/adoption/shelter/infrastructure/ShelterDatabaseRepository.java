package pl.devcezz.shelter.adoption.shelter.infrastructure;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent;
import pl.devcezz.shelter.adoption.shelter.model.ShelterFactory;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;
import pl.devcezz.shelter.commons.events.DomainEvents;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterDatabaseRepository implements Shelters {

    private final AcceptedProposalsDatabaseRepository acceptedProposalsDatabaseRepository;
    private final DomainModelMapper domainModelMapper;
    private final DomainEvents publisher;

    @Override
    public Shelter prepareShelter() {
        ShelterDatabaseEntity entity = prepareShelterEntity();
        return domainModelMapper.map(entity);
    }

    @Override
    public Shelter publish(ShelterEvent event) {
        Shelter result = handleEvent(event);
        publisher.publish(event.normalize());
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
        Set<AcceptedProposalDatabaseEntity> acceptedProposals = acceptedProposalsDatabaseRepository.findAll();

        acceptedProposalsDatabaseRepository.saveAll(entity.acceptedProposals);
        for (AcceptedProposalDatabaseEntity acceptedProposal : acceptedProposals) {
            if (!entity.acceptedProposals.contains(acceptedProposal)) {
                acceptedProposalsDatabaseRepository.delete(acceptedProposal);
            }
        }

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
                List.ofAll(entity.extractAcceptedProposalsIds()).toSet(),
                List.ofAll(entity.extractPendingProposalsIds()).toSet());
    }
}
