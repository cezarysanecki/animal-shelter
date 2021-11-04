package pl.devcezz.animalshelter.notification.mail;

import pl.devcezz.animalshelter.notification.mail.dto.EmailData;

record EmailSchema(EmailData template, EmailContext context) {}
