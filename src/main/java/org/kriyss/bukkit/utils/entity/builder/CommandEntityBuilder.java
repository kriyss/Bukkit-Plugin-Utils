package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.ParamEntity;
import org.kriyss.bukkit.utils.entity.PermissionEntity;

import java.util.List;

public class CommandEntityBuilder {
    private PermissionEntity permission;
    private String commandValue;
    private String description;
    private List<ParamEntity> paramEntities;

    private CommandEntityBuilder() {
    }

    public static CommandEntityBuilder aCommandEntity() {
        return new CommandEntityBuilder();
    }

    public CommandEntityBuilder withPermission(PermissionEntity permission) {
        this.permission = permission;
        return this;
    }

    public CommandEntityBuilder withCommandValue(String commandValue) {
        this.commandValue = commandValue;
        return this;
    }

    public CommandEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandEntityBuilder withParamEntities(List<ParamEntity> paramEntities) {
        this.paramEntities = paramEntities;
        return this;
    }

    public CommandEntity build() {
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setPermission(permission);
        commandEntity.setCommandValue(commandValue);
        commandEntity.setDescription(description);
        commandEntity.setParamEntities(paramEntities);
        return commandEntity;
    }
}
