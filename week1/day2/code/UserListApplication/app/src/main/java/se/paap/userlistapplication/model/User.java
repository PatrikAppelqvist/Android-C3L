package se.paap.userlistapplication.model;

import java.util.UUID;

public final class User {
    private final String username;
    private final int age;
    private final String id;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
        this.id = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + age;
        return result;
    }
}
