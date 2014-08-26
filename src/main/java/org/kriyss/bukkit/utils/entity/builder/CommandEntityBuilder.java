package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.ParamEntity;

import java.util.List;

public class CommandEntityBuilder implements HasPermission<CommandEntityBuilder> {
    private String commandValue;
    private String description;
    private String permission;
    private String permissionMessage;
    private List<ParamEntity> argEntities;
    private boolean fordAdmin = false;
    private boolean forConsole = false;

    private CommandEntityBuilder() {
    }

    public static CommandEntityBuilder aCommandEntity() {
        return new CommandEntityBuilder();
    }

    public CommandEntityBuilder withCommandValue(String commandValue) {
        this.commandValue = commandValue;
        return this;
    }

    public CommandEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandEntityBuilder withPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandEntityBuilder withPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    public CommandEntityBuilder withFordAdmin(boolean fordAdmin) {
        this.fordAdmin = fordAdmin;
        return this;
    }

    public CommandEntityBuilder withForConsole(boolean forConsole) {
        this.forConsole = forConsole;
        return this;
    }

    public CommandEntityBuilder withParamEntities(List<ParamEntity> argEntities) {
        this.argEntities = argEntities;
        return this;
    }

    public CommandEntity build() {
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandValue(commandValue);
        commandEntity.setDescription(description);
        commandEntity.setPermission(permission);
        commandEntity.setPermissionMessage(permissionMessage);
        commandEntity.setFordAdmin(fordAdmin);
        commandEntity.setForConsole(forConsole);
        commandEntity.setArgEntities(argEntities);
        return commandEntity;
    }
}
