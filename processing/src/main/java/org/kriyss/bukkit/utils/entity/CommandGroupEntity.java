package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

import java.util.List;

public class CommandGroupEntity implements HasPermission{


    private PermissionEntity permission;
    private String rootCommand;
    private List<CommandEntity> commands;
    private String completeClassName;

    public String getRootCommand() {
        return rootCommand;
    }

    public void setRootCommand(String rootCommand) {
        this.rootCommand = rootCommand;
    }

    public List<CommandEntity> getCommands() {
        return commands;
    }

    public void setCommands(List<CommandEntity> commands) {
        this.commands = commands;
    }

    public String getCompleteClassName() {
        return completeClassName;
    }

    public void setCompleteClassName(String completeClassName) {
        this.completeClassName = completeClassName;
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
                .add("rootCommand", rootCommand)
                .add("commands", commands)
                .add("completeClassName", completeClassName)
                .toString();
    }
}
