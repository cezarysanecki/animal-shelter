package pl.devcezz.animalshelter.notification.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "mail")
record EmailProperties(EmailServer server, EmailCredentials credentials, EmailSettings settings) {}

record EmailServer(String host, Integer port, String encoding) {}

record EmailCredentials(String from, String password) {}

record EmailSettings(String transportProtocol, String startTlsEnabled, String sslEnabled,
                     String authEnabled, String debugEnabled) {}