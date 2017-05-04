package se.paap.networkuserlistapplication.repository.http;

import java.util.List;

import se.paap.networkuserlistapplication.http.api.ApiCommand;
import se.paap.networkuserlistapplication.http.api.Api;
import se.paap.networkuserlistapplication.model.User;
import se.paap.networkuserlistapplication.repository.UserRepository;

public final class HttpUserRepository implements UserRepository {
    @Override
    public void getUsers(OnResultListener<List<User>> listener) {
        ApiCommand<List<User>> command = new ApiCommand<List<User>>() {
            @Override
            public List<User> execute(Api api) {
                return api.getUsers();
            }
        };

        executeCommand(command, listener);
    }

    @Override
    public void getUser(final long id, OnResultListener<User> listener) {
        if(id < 0) {
            return;
        }

        ApiCommand<User> command = new ApiCommand<User>() {
            @Override
            public User execute(Api api) {
                return api.getUser(id);
            }
        };

        executeCommand(command, listener);
    }

    @Override
    public void addOrUpdateUser(final User user, OnResultListener<Long> listener) {
        ApiCommand<Long> command = new ApiCommand<Long>() {
            @Override
            public Long execute(Api api) {
                if(user.hasBeenPersisted()) {
                    return api.updateUser(user);
                }

                return api.addUser(user);
            }
        };

        executeCommand(command, listener);
    }

    @Override
    public void removeUser(final long id, OnResultListener<Void> listener) {
        ApiCommand<Void> command = new ApiCommand<Void>() {
            @Override
            public Void execute(Api api) {
                api.removeUser(id);
                return null;
            }
        };

        executeCommand(command, listener);
    }

    private <T> void executeCommand(ApiCommand<T> command, OnResultListener<T> listener) {
        new BackgroundTask<T>(command, listener).execute();
    }
}
