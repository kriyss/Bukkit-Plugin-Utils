package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

import java.util.List;

public class CommandEntity implements HasPermission {

    private PermissionEntity permission;
    private String commandValue;
    private String description;
    private List<ParamEntity> paramEntities;


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

    public List<ParamEntity> getParamEntities() {
        return paramEntities;
    }

    public void setParamEntities(List<ParamEntity> paramEntities) {
        this.paramEntities = paramEntities;
    }

    public PermissionEntity getPermission() {
        return permission;
    }

    public void setPermission(PermissionEntity permission) {
        this.permission = permission;
    }
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("permission", permission)
                .add("commandValue", commandValue)
                .add("description", description)
                .add("paramEntities", paramEntities)
                .toString();
    }
}
