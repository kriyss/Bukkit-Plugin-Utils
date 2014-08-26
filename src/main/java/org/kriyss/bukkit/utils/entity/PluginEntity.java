package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

import java.util.List;

public class PluginEntity {
    private String permission;
    private String permissionMessage;
    private String name;
    private String version;
    private String completeClassName;
    private List<CommandGroupEntity> commandGroups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CommandGroupEntity> getCommandGroups() {
        return commandGroups;
    }

    public void setCommandGroups(List<CommandGroupEntity> commandGroups) {
        this.commandGroups = commandGroups;
    }

    public String getCompleteClassName() {
        return completeClassName;
    }

    public void setCompleteClassName(String completeClassName) {
        this.completeClassName = completeClassName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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
                .add("permission", permission)
                .add("permissionMessage", permissionMessage)
                .add("name", name)
                .add("version", version)
                .add("completeClassName", completeClassName)
                .add("commandGroups", commandGroups)
                .toString();
    }
}
