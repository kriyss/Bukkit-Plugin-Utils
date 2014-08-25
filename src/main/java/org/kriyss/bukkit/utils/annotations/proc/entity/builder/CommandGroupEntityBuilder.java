package org.kriyss.bukkit.utils.annotations.proc.entity.builder;

import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandGroupEntity;

import java.util.List;

public class CommandGroupEntityBuilder {
    private String rootCommand;
    private boolean fordAdmin = false;
    private boolean forConsole = false;
    private List<String> permissions;
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

    public CommandGroupEntityBuilder withPermissions(List<String> permissions) {
        this.permissions = permissions;
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
        commandGroupEntity.setPermissions(permissions);
        commandGroupEntity.setCommands(commands);
        commandGroupEntity.setCompleteClassName(completeClassName);
        return commandGroupEntity;
    }
}
