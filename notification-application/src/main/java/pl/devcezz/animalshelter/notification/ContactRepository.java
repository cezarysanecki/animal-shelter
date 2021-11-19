package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;

interface ContactRepository {

    Set<Contact> findAll();

    void save(Contact contact);

    void delete(ContactId contactId, Contact.Source source);
}
