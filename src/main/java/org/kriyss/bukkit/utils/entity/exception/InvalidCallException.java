package org.kriyss.bukkit.utils.entity.exception;

/**
 * Created on 05/09/2014.
 */
public abstract class InvalidCallException extends Exception {
    protected InvalidCallException() {
    }

    protected InvalidCallException(String message) {
        super(message);
    }

    protected InvalidCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
