package pl.devcezz.animalshelter.mail;

import org.springframework.mail.SimpleMailMessage;
import pl.devcezz.animalshelter.commons.mail.model.AdoptionMail;

public class EmailFactory {

    public SimpleMailMessage adoptionMail(AdoptionMail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("animalshelter@gmail.com");
        message.setTo("csanecki@gmail.com");
        message.setSubject(mail.getName() + " found home!");
        message.setText("Finally!!!");
        return message;
    }
}
