package org.kriyss.bukkit.utils.annotations.proc.entity;

import java.util.List;

public class CommandGroupEntity {
    private String rootCommand;
    private boolean fordAdmin = false;
    private boolean forConsole = false;
    private List<String> permissions;
    private List<CommandEntity> commands;

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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<CommandEntity> getCommands() {
        return commands;
    }

    public void setCommands(List<CommandEntity> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "CommandGroupEntity{" +
                "rootCommand='" + rootCommand + '\'' +
                ", fordAdmin=" + fordAdmin +
                ", forConsole=" + forConsole +
                ", permissions=" + permissions +
                ", commands=" + commands +
                '}';
    }
}
