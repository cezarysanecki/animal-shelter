package pl.devcezz.animalshelter.notification;

import java.util.UUID;

class NotificationFixture {

    static Contact zookeeperContact() {
        return new Contact(new ContactId(UUID.randomUUID()), "test@mail.com", Contact.Source.Zookeeper);
    }
}
