package se.paap.userlistapplication.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.paap.userlistapplication.model.User;

public final class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();

        for(int i = 0; i < 1000; i++) {
            User user = new User("Username" + i, i * 3);
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
        User removedUser = users.remove(id);
        return removedUser;
    }
}
