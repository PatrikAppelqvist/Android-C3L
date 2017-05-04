package se.paap.networkuserlistapplication.http.api;

public interface ApiCommand<T> {
    T execute(Api api);
}
