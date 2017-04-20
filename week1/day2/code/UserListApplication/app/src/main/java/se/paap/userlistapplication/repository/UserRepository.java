package se.paap.userlistapplication.repository;

import java.util.List;

import se.paap.userlistapplication.model.User;

public interface UserRepository {
    List<User> getUsers();
    User getUser(String id);
    String addUser(User user);
    User removeUser(String id);
}
