package se.paap.translateexample.repository;

import java.util.List;

import se.paap.translateexample.model.User;

public interface UserRepository {
    List<User> getUsers();
    User getUser(long id);
    long addOrUpdateUser(User user);
    User removeUser(long id);
}
