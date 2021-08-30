package pl.devcezz.animalshelter.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import pl.devcezz.animalshelter.commons.mail.EmailSender;
import pl.devcezz.animalshelter.commons.mail.model.AdoptionMail;

public class EmailSenderImpl implements EmailSender {

    private final EmailFactory emailFactory;
    private final MailSender mailSender;

    public EmailSenderImpl(final EmailFactory emailFactory, final MailSender mailSender) {
        this.emailFactory = emailFactory;
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(final AdoptionMail mail) {
        SimpleMailMessage message = emailFactory.adoptionMail(mail);
        mailSender.send(message);
    }
}
