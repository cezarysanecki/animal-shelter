package pl.devcezz.animalshelter.commons.mail;

import pl.devcezz.animalshelter.commons.mail.model.AdoptionMail;

public interface EmailSender {

    void sendEmail(AdoptionMail mail);
}
