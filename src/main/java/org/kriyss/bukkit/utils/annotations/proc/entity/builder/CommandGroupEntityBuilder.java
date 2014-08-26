package org.kriyss.bukkit.utils.annotations.proc.entity.builder;

import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandGroupEntity;

import java.util.List;

public class CommandGroupEntityBuilder implements HasPermission<CommandGroupEntityBuilder> {
    private String rootCommand;
    private boolean fordAdmin = false;
    private boolean forConsole = false;
    private String permission;
    private String permissionMessage;
    private List<CommandEntity> commands;
    private String completeClassName;

    private CommandGroupEntityBuilder() {
    }

    public static CommandGroupEntityBuilder aCommandGroupEntity() {
        return new CommandGroupEntityBuilder();
    }

    public CommandGroupEntityBuilder withRootCommand(String rootCommand) {
        this.rootCommand = rootCommand;
        return this;
    }

    public CommandGroupEntityBuilder withFordAdmin(boolean fordAdmin) {
        this.fordAdmin = fordAdmin;
        return this;
    }

    public CommandGroupEntityBuilder withForConsole(boolean forConsole) {
        this.forConsole = forConsole;
        return this;
    }

    public CommandGroupEntityBuilder withCommands(List<CommandEntity> commands) {
        this.commands = commands;
        return this;
    }

    public CommandGroupEntityBuilder withPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandGroupEntityBuilder withPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    public CommandGroupEntityBuilder withCompleteClassName(String completeClassName) {
        this.completeClassName = completeClassName;
        return this;
    }

    public CommandGroupEntity build() {
        CommandGroupEntity commandGroupEntity = new CommandGroupEntity();
        commandGroupEntity.setRootCommand(rootCommand);
        commandGroupEntity.setFordAdmin(fordAdmin);
        commandGroupEntity.setForConsole(forConsole);
        commandGroupEntity.setPermission(permission);
        commandGroupEntity.setPermissionMessage(permissionMessage);
        commandGroupEntity.setCommands(commands);
        commandGroupEntity.setCompleteClassName(completeClassName);
        return commandGroupEntity;
    }
}
