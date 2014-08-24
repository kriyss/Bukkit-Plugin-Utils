package org.kriyss.bukkit.utils.annotations.proc.entity;

import java.util.List;

public class CommandEntity {
    private String commandValue;
    private String description;
    private List<String> permissions;
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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
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

    @Override
    public String toString() {
        return "\nCommandEntity{" +
                "\ncommandValue='" + commandValue + '\'' +
                ",\ndescription='" + description + '\'' +
                ",\npermissions=" + permissions +
                ",\nfordAdmin=" + fordAdmin +
                ",\nforConsole=" + forConsole +
                ",\nargEntities=" + argEntities +
                '}';
    }
}
