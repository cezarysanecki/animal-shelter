package pl.devcezz.animalshelter.notification;

import java.util.UUID;

class NotificationFixture {

    static ZookeeperContact zookeeperContact() {
        return new ZookeeperContact(new ZookeeperId(UUID.randomUUID()), "test@mail.com");
    }
}
