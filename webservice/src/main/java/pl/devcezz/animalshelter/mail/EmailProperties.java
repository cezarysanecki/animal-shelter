package pl.devcezz.animalshelter.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/mail-${spring.profiles.active}.yaml")
@ConstructorBinding
@ConfigurationProperties(prefix = "mail")
public record EmailProperties(EmailServer server, EmailCredentials credentials, EmailSettings settings) {}

record EmailServer(String host, Integer port) {}

record EmailCredentials(String from, String password) {}

record EmailSettings(String transportProtocol, String startTlsEnabled, String sslEnabled,
                     String authEnabled, String debugEnabled) {}