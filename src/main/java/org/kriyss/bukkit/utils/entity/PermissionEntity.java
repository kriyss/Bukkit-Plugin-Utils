package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

public class PermissionEntity {
    private String value = "";
    private String message = "";
    private boolean forAdmin = false;
    private boolean forConsole = false;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isForAdmin() {
        return forAdmin;
    }

    public void setForAdmin(boolean forAdmin) {
        this.forAdmin = forAdmin;
    }

    public boolean isForConsole() {
        return forConsole;
    }

    public void setForConsole(boolean forConsole) {
        this.forConsole = forConsole;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .add("message", message)
                .add("forAdmin", forAdmin)
                .add("forConsole", forConsole)
                .toString();
    }
}
