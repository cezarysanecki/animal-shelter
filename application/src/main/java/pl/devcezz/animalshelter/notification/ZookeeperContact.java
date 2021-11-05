package pl.devcezz.animalshelter.notification;

import pl.devcezz.animalshelter.notification.dto.ZookeeperContactDetails;

record ZookeeperContact(ZookeeperId zookeeperId, String email) {

    ZookeeperContactDetails toZookeeperContactDetails() {
        return new ZookeeperContactDetails(email);
    }
}
