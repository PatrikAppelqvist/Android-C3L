package se.paap.networkuserlistapplication.repository;

import java.util.List;

import se.paap.networkuserlistapplication.model.User;

public interface UserRepository {
    void getUsers(OnResultListener<List<User>> listener);
    void getUser(long id, OnResultListener<User> listener);
    void addOrUpdateUser(User user, OnResultListener<Long> listener);
    void removeUser(long id, OnResultListener<Void> listener);

    interface OnResultListener<T> {
        void onResult(T result);
    }
}
