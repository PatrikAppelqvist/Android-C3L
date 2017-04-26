package se.paap.solutionfragmentsexercise.repository;

import java.util.List;

import se.paap.solutionfragmentsexercise.model.Contact;

/**
 * Created by patrik on 2017-04-26.
 */

public interface ContactsRepository {
    Contact getContact(long id);
    long addContact(Contact contact);
    List<Contact> getAllContacts();
}
