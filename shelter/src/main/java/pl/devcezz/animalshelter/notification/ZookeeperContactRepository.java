package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;

interface ZookeeperContactRepository {

    Set<ZookeeperContact> findAll();
}
