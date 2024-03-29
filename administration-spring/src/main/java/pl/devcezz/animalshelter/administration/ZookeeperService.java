package pl.devcezz.animalshelter.administration;

import org.springframework.stereotype.Service;
import pl.devcezz.animalshelter.administration.dto.AddedZookeeperEvent;
import pl.devcezz.animalshelter.administration.dto.DeletedZookeeperEvent;
import pl.devcezz.cqrs.event.EventsBus;

import java.util.UUID;

@Service
class ZookeeperService {

    private final ZookeeperRepository zookeeperRepository;
    private final EventsBus eventsBus;

    ZookeeperService(final ZookeeperRepository zookeeperRepository, final EventsBus eventsBus) {
        this.zookeeperRepository = zookeeperRepository;
        this.eventsBus = eventsBus;
    }

    void addZookeeper(String name, String email) {
        Zookeeper savedZookeeper = zookeeperRepository.save(new Zookeeper(name, email));

        eventsBus.publish(new AddedZookeeperEvent(
                savedZookeeper.toZookeeperId(),
                savedZookeeper.getName(),
                savedZookeeper.getEmail()
        ));
    }

    void deleteZookeeper(UUID zookeeperId) {
        zookeeperRepository.deleteById(zookeeperId.toString());
        eventsBus.publish(new DeletedZookeeperEvent(zookeeperId));
    }
}
