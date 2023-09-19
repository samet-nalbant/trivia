package com.adaptionsoft.games.uglytrivia;

public class MaxNumberOfUserViolationError extends Throwable {
    public MaxNumberOfUserViolationError() {
    }

    public MaxNumberOfUserViolationError(String message) {
        super(message);
    }

    public MaxNumberOfUserViolationError(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxNumberOfUserViolationError(Throwable cause) {
        super(cause);
    }
}
