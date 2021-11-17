package pl.devcezz.animalshelter.zookeeper.dto;

import pl.devcezz.cqrs.event.Event;

import java.util.UUID;

public record AddedZookeeperEvent(UUID zookeeperId, String name,
                                  String email) implements Event {}
