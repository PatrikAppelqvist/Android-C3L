package se.paap.recyclerviewapplication.repository;

import java.util.List;

import se.paap.recyclerviewapplication.model.User;

public interface UserRepository {
    List<User> getUsers();
    User getUser(String id);
    String addUser(User user);
    User removeUser(String id);
}
