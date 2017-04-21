package se.paap.solutionrecyclerviewexercise.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import se.paap.solutionrecyclerviewexercise.model.User;

public final class InMemoryUserRepository implements UserRepository {
    private static final AtomicLong idGenerator = new AtomicLong();
    private static final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(long id) {
        return users.get(id);
    }

    @Override
    public long addOrUpdateUser(User userToAdd) {
        if(userToAdd.hasBeenPersisted()) {
            // Update user

            users.put(userToAdd.getId(), userToAdd);
            return userToAdd.getId();
        }

        // Add user
        User user = new User(idGenerator.getAndIncrement(), userToAdd.getUsername(), userToAdd.getAge());
        users.put(user.getId(), user);

        return user.getId();
    }

    @Override
    public User removeUser(long id) {
        User removedUser = users.remove(id);
        return removedUser;
    }
}
