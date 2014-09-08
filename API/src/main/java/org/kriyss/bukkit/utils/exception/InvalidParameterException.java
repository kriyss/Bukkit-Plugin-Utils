package org.kriyss.bukkit.utils.exception;

/**
 * Created on 05/09/2014.
 */
public class InvalidParameterException extends Exception {

    public InvalidParameterException() {
    }

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
