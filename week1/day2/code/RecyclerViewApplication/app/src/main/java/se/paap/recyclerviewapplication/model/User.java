package se.paap.recyclerviewapplication.model;

import java.util.UUID;

public final class User {
    private final String id;
    private final String username;
    private final int age;

    public User(String id, String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public User(String username, int age) {
        this(UUID.randomUUID().toString(), username, age);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
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

        return age == user.age && id.equals(user.id) && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + age;
        return result;
    }
}
