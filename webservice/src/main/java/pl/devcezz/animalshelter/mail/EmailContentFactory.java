package pl.devcezz.animalshelter.mail;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.devcezz.animalshelter.commons.mail.model.AdoptionMail;

public class EmailContentFactory {

    private final TemplateEngine templateEngine;

    public EmailContentFactory(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public EmailContent adoptionMail(AdoptionMail mail) {
        String subject = prepareSubject(mail);
        String text = prepareText(mail);
        return new EmailContent(subject, text);
    }

    private String prepareSubject(final AdoptionMail mail) {
        return "HAHA - " + mail.getName();
    }

    private String prepareText(final AdoptionMail mail) {
        Context context = new Context();
        context.setVariable("header", mail.getName() + " finally found home!");
        context.setVariable("title", "We are glad to inform you that our pupil finally found home!");
        context.setVariable("description", "We are waiting for such days :)");

        return templateEngine.process("adoption-mail", context);
    }
}
