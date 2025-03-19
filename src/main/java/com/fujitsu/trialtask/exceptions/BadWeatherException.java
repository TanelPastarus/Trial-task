package com.fujitsu.trialtask.exceptions;

public class BadWeatherException extends RuntimeException {
    public BadWeatherException() {
        super();
    }

    public BadWeatherException(String message) {
        super(message);
    }

    public BadWeatherException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadWeatherException(Throwable cause) {
        super(cause);
    }
}