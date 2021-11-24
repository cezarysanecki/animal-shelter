package pl.devcezz.animalshelter.notification.mail;

import org.springframework.http.MediaType;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;

record EmailAttachment(byte[] content, String filename) {

    void attachTo(MimeMessageHelper message) throws MessagingException {
        message.addAttachment(filename, new ByteArrayDataSource(content, MediaType.APPLICATION_OCTET_STREAM_VALUE));
    }
}
