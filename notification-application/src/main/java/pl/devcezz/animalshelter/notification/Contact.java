package pl.devcezz.animalshelter.notification;

import io.vavr.collection.List;
import pl.devcezz.animalshelter.notification.dto.ContactDetails;

record Contact(ContactId contactId, String email, Source source) {

    enum Source {
        Zookeeper;

        static Source of(String value) {
            if (value == null) {
                throw new IllegalArgumentException("source cannot be null");
            }

            String trimmedValue = value.trim();
            if (trimmedValue.isEmpty()) {
                throw new IllegalArgumentException("source cannot be empty");
            }

            return List.of(Source.values())
                    .find(source -> source.name().equals(trimmedValue))
                    .getOrElseThrow(() -> new IllegalArgumentException("source cannot be of value " + trimmedValue));
        }
    }

    ContactDetails toContactDetails() {
        return new ContactDetails(email);
    }
}
