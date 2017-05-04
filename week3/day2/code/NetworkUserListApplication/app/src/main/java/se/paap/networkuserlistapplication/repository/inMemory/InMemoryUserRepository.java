package se.paap.networkuserlistapplication.repository.inMemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import se.paap.networkuserlistapplication.model.User;
import se.paap.networkuserlistapplication.repository.UserRepository;

public final class InMemoryUserRepository implements UserRepository {
    private static final AtomicLong idGenerator = new AtomicLong();
    private static final Map<Long, User> users = new HashMap<>();

    @Override
    public void getUsers(OnResultListener<List<User>> listener) {
        List<User> usersList = new ArrayList<>(users.values());
        listener.onResult(usersList);
    }

    @Override
    public void getUser(long id, OnResultListener<User> listener) {
        User user = users.get(id);
        listener.onResult(user);
    }

    @Override
    public void addOrUpdateUser(User userToAdd, OnResultListener<Long> listener) {
        if(userToAdd.hasBeenPersisted()) {
            // Update user

            users.put(userToAdd.getId(), userToAdd);
            listener.onResult(userToAdd.getId());

            return;
        }

        // Add user
        User user = new User(idGenerator.getAndIncrement(), userToAdd.getUsername(), userToAdd.getAge());
        users.put(user.getId(), user);

        listener.onResult(user.getId());
    }

    @Override
    public void removeUser(long id, OnResultListener<Void> listener) {
        users.remove(id);
        listener.onResult(null);
    }
}
