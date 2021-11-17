package pl.devcezz.animalshelter.notification;

import pl.devcezz.animalshelter.zookeeper.dto.AddedZookeeperEvent;
import pl.devcezz.animalshelter.zookeeper.dto.DeletedZookeeperEvent;
import pl.devcezz.cqrs.event.EventHandler;

class HandleZookeeperAdded implements EventHandler<AddedZookeeperEvent> {

    private final ZookeeperContactDatabaseRepository zookeeperContactRepository;

    HandleZookeeperAdded(final ZookeeperContactDatabaseRepository zookeeperContactRepository) {
        this.zookeeperContactRepository = zookeeperContactRepository;
    }

    @Override
    public void handle(final AddedZookeeperEvent event) {
        zookeeperContactRepository.save(new ZookeeperContact(
                new ZookeeperId(event.zookeeperId()),
                event.email()
        ));
    }
}

class HandleZookeeperDeleted implements EventHandler<DeletedZookeeperEvent> {

    private final ZookeeperContactDatabaseRepository zookeeperContactRepository;

    HandleZookeeperDeleted(final ZookeeperContactDatabaseRepository zookeeperContactRepository) {
        this.zookeeperContactRepository = zookeeperContactRepository;
    }

    @Override
    public void handle(final DeletedZookeeperEvent event) {
        zookeeperContactRepository.delete(new ZookeeperId(event.zookeeperId()));
    }
}