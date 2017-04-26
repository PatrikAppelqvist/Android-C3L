package se.paap.solutionfragmentsexercise.model;

import java.util.Locale;

public final class Contact {
    private static final long DEFAULT_ID = -1L;

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String phoneNumber;

    public Contact(long id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = String.format("%s %s", firstName, lastName);
        this.phoneNumber = phoneNumber;
    }

    public Contact(String firstName, String lastName, String phoneNumber) {
        this(DEFAULT_ID, firstName, lastName, phoneNumber);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null)
            return false;
        return fullName != null ? fullName.equals(contact.fullName) : contact.fullName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Contact(%d, %s, %s, %s)", id, firstName, lastName, fullName);
    }
}
