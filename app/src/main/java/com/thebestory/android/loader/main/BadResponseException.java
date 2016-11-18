package com.thebestory.android.loader.main;

/**
 * Created by Октай on 16.11.2016.
 */

public class BadResponseException extends Exception {

    public BadResponseException(String message) {
        super(message);
    }

    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadResponseException(Throwable cause) {
        super(cause);
    }
}
