package se.paap.solutionfragmentsexercise.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import se.paap.solutionfragmentsexercise.model.Contact;

public class InMemoryContactRepository implements ContactsRepository {
    private static final AtomicLong idGenerator = new AtomicLong();
    private static final Map<Long, Contact> contacts = new HashMap<>();

    static {
        for(int i = 0; i < 1000; i++) {
            contacts.put((long) i, new Contact(i, String.valueOf(i), "Contact", getRandomPhoneNumber(i)));
        }
    }

    @Override
    public Contact getContact(long id) {
        return contacts.get(id);
    }

    @Override
    public long addContact(Contact contact) {
        Contact addedContact = new Contact(
                idGenerator.getAndIncrement(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhoneNumber());

        contacts.put(addedContact.getId(), addedContact);

        return addedContact.getId();
    }

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    private static String getRandomPhoneNumber(int number) {
        String template = "0701234";
        if(number > 99) {
            return template + number;
        } else if(number > 9) {
            return template + "5" + number;
        } else {
            return template + "56" + number;
        }
    }
}
