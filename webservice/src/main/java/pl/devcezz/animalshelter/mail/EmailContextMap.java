package pl.devcezz.animalshelter.mail;

import pl.devcezz.animalshelter.commons.notification.Notification;

import java.util.Map;

interface EmailContextMap {

    Map<String, Object> contextMap();

    record AdoptionEmailContextMap(Notification.AdoptionNotification mail) implements EmailContextMap {

        @Override
        public Map<String, Object> contextMap() {
            return Map.of(
                    "animalName", mail.animalName()
            );
        }
    }
}
