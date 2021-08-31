package pl.devcezz.animalshelter.mail;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

class EmailBuilder {

    private EmailContent emailContent;
    private EmailContentProperties props;

    private EmailBuilder() {}

    static EmailBuilder builder() {
        return new EmailBuilder();
    }

    EmailContentNeeded content(EmailContent emailContent) {
        this.emailContent = emailContent;
        return new EmailContentNeeded();
    }

    class EmailContentNeeded {
        private EmailContentNeeded() {}

        EmailPropertiesNeeded props(EmailContentProperties props) {
            EmailBuilder.this.props = props;
            return new EmailPropertiesNeeded();
        }
    }

    class EmailPropertiesNeeded {
        private EmailPropertiesNeeded() {}

        MimeMessagePreparator build(String userEmail) {
            return new EmailPreparator(userEmail, emailContent, props);
        }
    }

    class EmailPreparator implements MimeMessagePreparator {

        private final String userEmail;
        private final EmailContent setup;
        private final EmailContentProperties props;

        private EmailPreparator(final String userEmail, final EmailContent setup, final EmailContentProperties props) {
            this.userEmail = userEmail;
            this.setup = setup;
            this.props = props;
        }

        @Override
        public void prepare(final MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, props.multipart(), props.encoding());
            message.setFrom(props.from());
            message.setTo(userEmail);
            message.setSubject(setup.subject());
            message.setText(setup.text(), props.mailHtml());
        }
    }
}
