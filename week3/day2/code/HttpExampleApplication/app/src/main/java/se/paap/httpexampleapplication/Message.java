package se.paap.httpexampleapplication;

/**
 * Created by patrik on 2017-05-04.
 */

public final class Message {
    private final String message;
    private final String from;

    public Message(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }
}
