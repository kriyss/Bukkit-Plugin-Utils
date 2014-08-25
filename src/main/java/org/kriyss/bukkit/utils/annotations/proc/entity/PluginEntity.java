package org.kriyss.bukkit.utils.annotations.proc.entity;

import java.util.List;

public class PluginEntity {
    private String name;
    private String version;
    private String completeClassName;
    private List<CommandGroupEntity> commandGroups;

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
    @Override
    public String toString() {
        return "PluginEntity{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", completeClassName='" + completeClassName + '\'' +
                ", commandGroups=" + commandGroups +
                '}';
    }
}
