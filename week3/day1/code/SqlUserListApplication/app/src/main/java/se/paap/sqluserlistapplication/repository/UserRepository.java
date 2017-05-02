package se.paap.sqluserlistapplication.repository;

import java.util.List;

import se.paap.sqluserlistapplication.model.User;

public interface UserRepository {
    List<User> getUsers();
    User getUser(long id);
    long addOrUpdateUser(User user);
    User removeUser(long id);
}
