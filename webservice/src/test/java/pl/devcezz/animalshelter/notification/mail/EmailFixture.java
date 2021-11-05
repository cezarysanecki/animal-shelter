package pl.devcezz.animalshelter.notification.mail;

import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;

import java.util.UUID;

class EmailFixture {

    static SuccessfulAdoptionNotification successfulAdoptionNotification() {
        return new SuccessfulAdoptionNotification(UUID.randomUUID(), "Azor", 2, "Dog");
    }
}
