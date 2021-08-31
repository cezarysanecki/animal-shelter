package pl.devcezz.animalshelter.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "message-config")
record EmailContentProperties(Boolean multipart, String encoding, String from, Boolean mailHtml) {}
