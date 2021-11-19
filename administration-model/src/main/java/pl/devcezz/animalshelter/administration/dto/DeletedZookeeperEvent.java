package pl.devcezz.animalshelter.administration.dto;

import pl.devcezz.cqrs.event.Event;

import java.util.UUID;

public record DeletedZookeeperEvent(UUID zookeeperId) implements Event {}
