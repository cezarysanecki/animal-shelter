package pl.devcezz.animalshelter.notification;

import pl.devcezz.animalshelter.notification.ZookeeperContact;
import pl.devcezz.animalshelter.notification.ZookeeperId;

import java.util.UUID;

class NotificationFixture {

    static ZookeeperContact zookeeperContact() {
        return new ZookeeperContact(new ZookeeperId(UUID.randomUUID()), "test@mail.com");
    }
}
