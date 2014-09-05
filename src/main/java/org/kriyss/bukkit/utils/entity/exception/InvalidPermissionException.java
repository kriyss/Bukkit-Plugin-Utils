package org.kriyss.bukkit.utils.entity.exception;

public class InvalidPermissionException extends InvalidCallException{

    public InvalidPermissionException() {}

    public InvalidPermissionException(String message) {
        super(message);
    }

    public InvalidPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
