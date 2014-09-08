package org.kriyss.bukkit.utils.exception;

public class InvalidPermissionException extends Exception{

    public InvalidPermissionException() {}

    public InvalidPermissionException(String message) {
        super(message);
    }

    public InvalidPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
