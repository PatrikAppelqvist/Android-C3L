package se.paap.recyclerviewapplication.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.paap.recyclerviewapplication.model.User;

public final class InMemoryUserRepository implements UserRepository {
    private static final Map<String, User> users = new HashMap<>();

    static {
        for(int i = 0; i < 100; i++) {
            User user = new User("Username " + i, i);
            users.put(user.getId(), user);
        }
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public String addUser(User user) {
        users.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public User removeUser(String id) {
        return users.remove(id);
    }
}
