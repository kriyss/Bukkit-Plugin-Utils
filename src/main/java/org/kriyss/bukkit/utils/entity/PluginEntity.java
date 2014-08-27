package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

import java.util.List;

public class PluginEntity {
    private String name;
    private String version;
    private String completeClassName;
    private List<CommandGroupEntity> commandGroups;
    private PermissionEntity permission;

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

    public void setPermission(PermissionEntity permission) {
        this.permission = permission;
    }

    public PermissionEntity getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("version", version)
                .add("completeClassName", completeClassName)
                .add("commandGroups", commandGroups)
                .add("permission", permission)
                .toString();
    }
}
