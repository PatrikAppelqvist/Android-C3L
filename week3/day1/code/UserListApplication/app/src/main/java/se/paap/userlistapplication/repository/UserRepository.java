package se.paap.userlistapplication.repository;

import java.util.List;

import se.paap.userlistapplication.model.User;

public interface UserRepository {
    List<User> getUsers();
    User getUser(long id);
    long addOrUpdateUser(User user);
    User removeUser(long id);
}
