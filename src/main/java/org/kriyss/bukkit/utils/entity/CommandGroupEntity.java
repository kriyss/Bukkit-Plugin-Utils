package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

import java.util.List;

public class CommandGroupEntity {
    private String rootCommand;
    private boolean fordAdmin = false;
    private boolean forConsole = false;
    private String permission;
    private String permissionMessage;
    private List<CommandEntity> commands;
    private String completeClassName;

    public String getRootCommand() {
        return rootCommand;
    }

    public void setRootCommand(String rootCommand) {
        this.rootCommand = rootCommand;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public void setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
    }

    @Override
    public String toString() {
        return "\n\t"+Objects.toStringHelper(this)
                .add("\trootCommand", rootCommand)
                .add("\tfordAdmin", fordAdmin)
                .add("\tforConsole", forConsole)
                .add("\tpermission", permission)
                .add("\tpermissionMessage", permissionMessage)
                .add("\tcompleteClassName", completeClassName)
                .add("\tcommands", commands)
                .toString();
    }
}
