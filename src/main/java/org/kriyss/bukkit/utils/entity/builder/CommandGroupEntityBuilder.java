package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.PermissionEntity;

import java.util.List;

/**
 * Created on 27/08/2014.
 */
public class CommandGroupEntityBuilder {
    private PermissionEntity permission;
    private String rootCommand;
    private List<CommandEntity> commands;
    private String completeClassName;

    private CommandGroupEntityBuilder() {
    }

    public static CommandGroupEntityBuilder aCommandGroupEntity() {
        return new CommandGroupEntityBuilder();
    }

    public CommandGroupEntityBuilder withPermission(PermissionEntity permission) {
        this.permission = permission;
        return this;
    }

    public CommandGroupEntityBuilder withRootCommand(String rootCommand) {
        this.rootCommand = rootCommand;
        return this;
    }

    public CommandGroupEntityBuilder withCommands(List<CommandEntity> commands) {
        this.commands = commands;
        return this;
    }

    public CommandGroupEntityBuilder withCompleteClassName(String completeClassName) {
        this.completeClassName = completeClassName;
        return this;
    }

    public CommandGroupEntity build() {
        CommandGroupEntity commandGroupEntity = new CommandGroupEntity();
        commandGroupEntity.setPermission(permission);
        commandGroupEntity.setRootCommand(rootCommand);
        commandGroupEntity.setCommands(commands);
        commandGroupEntity.setCompleteClassName(completeClassName);
        return commandGroupEntity;
    }
}
