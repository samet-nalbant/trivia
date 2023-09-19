package com.adaptionsoft.games.uglytrivia;

public class MinNumberOfUserViolationError extends Exception{
    public MinNumberOfUserViolationError() {
    }

    public MinNumberOfUserViolationError(String message) {
        super(message);

    }

    public MinNumberOfUserViolationError(String message, Throwable cause) {
        super(message, cause);
    }

    public MinNumberOfUserViolationError(Throwable cause) {
        super(cause);
    }
}
