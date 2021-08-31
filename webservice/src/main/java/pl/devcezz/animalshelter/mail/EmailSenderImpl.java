package pl.devcezz.animalshelter.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import pl.devcezz.animalshelter.commons.mail.EmailSender;
import pl.devcezz.animalshelter.commons.mail.model.AdoptionMail;

public class EmailSenderImpl implements EmailSender {

    private final EmailContentFactory emailContentFactory;
    private final EmailContentProperties properties;
    private final JavaMailSender mailSender;

    public EmailSenderImpl(final EmailContentFactory emailContentFactory, final EmailContentProperties properties, final JavaMailSender mailSender) {
        this.emailContentFactory = emailContentFactory;
        this.properties = properties;
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(final AdoptionMail mail) {
        EmailContent emailContent = emailContentFactory.adoptionMail(mail);
        MimeMessagePreparator message = EmailBuilder.builder()
                .content(emailContent)
                .props(properties)
                .build("csanecki@gmail.com");
        mailSender.send(message);
    }
}
