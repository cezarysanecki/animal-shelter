package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.dto.ContactDetails;
import pl.devcezz.animalshelter.notification.mail.EmailFacade;

class EmailNotifier implements Notifier {

    private final EmailFacade emailFacade;

    EmailNotifier(final EmailFacade emailFacade) {
        this.emailFacade = emailFacade;
    }

    @Override
    public void notify(final Set<ContactDetails> zookeepersContactDetails, final Notification notification) {
        emailFacade.sendEmail(getEmailAddresses(zookeepersContactDetails), notification);
    }

    private Set<String> getEmailAddresses(final Set<ContactDetails> zookeepersContactDetails) {
        return zookeepersContactDetails.map(ContactDetails::email);
    }
}
