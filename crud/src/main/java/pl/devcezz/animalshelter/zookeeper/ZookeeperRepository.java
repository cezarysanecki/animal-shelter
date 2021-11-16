package pl.devcezz.animalshelter.zookeeper;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface ZookeeperRepository extends CrudRepository<Zookeeper, UUID> {}
