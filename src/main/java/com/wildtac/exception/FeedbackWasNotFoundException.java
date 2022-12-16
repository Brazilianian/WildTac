package com.wildtac.exception;

public class FeedbackWasNotFoundException extends RuntimeException {
    public FeedbackWasNotFoundException() {
        super();
    }

    public FeedbackWasNotFoundException(String message) {
        super(message);
    }
}
