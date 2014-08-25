package org.kriyss.bukkit.utils.annotations.proc.entity;

import com.google.common.base.Objects;

import java.util.List;

public class CommandEntity {
    private String commandValue;
    private String description;
    private String permission;
    private String permissionMessage;
    private boolean fordAdmin = false;
    private boolean forConsole = false;
    private List<ArgEntity> argEntities;


    public String getCommandValue() {
        return commandValue;
    }

    public void setCommandValue(String commandValue) {
        this.commandValue = commandValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isFordAdmin() {
        return fordAdmin;
    }

    public void setFordAdmin(boolean fordAdmin) {
        this.fordAdmin = fordAdmin;
    }

    public boolean isForConsole() {
        return forConsole;
    }

    public void setForConsole(boolean forConsole) {
        this.forConsole = forConsole;
    }

    public List<ArgEntity> getArgEntities() {
        return argEntities;
    }

    public void setArgEntities(List<ArgEntity> argEntities) {
        this.argEntities = argEntities;
    }
    public String getPermissionMessage() {
        return permissionMessage;
    }

    public void setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("commandValue", commandValue)
                .add("description", description)
                .add("permission", permission)
                .add("permissionMessage", permissionMessage)
                .add("fordAdmin", fordAdmin)
                .add("forConsole", forConsole)
                .add("argEntities", argEntities)
                .toString();
    }
}
