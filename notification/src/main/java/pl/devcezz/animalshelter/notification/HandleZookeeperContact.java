package pl.devcezz.animalshelter.notification;

import pl.devcezz.animalshelter.administration.dto.AddedZookeeperEvent;
import pl.devcezz.animalshelter.administration.dto.DeletedZookeeperEvent;
import pl.devcezz.cqrs.event.EventHandler;

class HandleZookeeperAdded implements EventHandler<AddedZookeeperEvent> {

    private final ContactRepository contactRepository;

    HandleZookeeperAdded(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void handle(final AddedZookeeperEvent event) {
        contactRepository.save(new Contact(
                new ContactId(event.zookeeperId()),
                event.email(),
                Contact.Source.Zookeeper
        ));
    }
}

class HandleZookeeperDeleted implements EventHandler<DeletedZookeeperEvent> {

    private final ContactRepository contactRepository;

    HandleZookeeperDeleted(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void handle(final DeletedZookeeperEvent event) {
        contactRepository.delete(
                new ContactId(event.zookeeperId()),
                Contact.Source.Zookeeper
        );
    }
}