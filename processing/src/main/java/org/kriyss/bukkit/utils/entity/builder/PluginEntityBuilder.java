package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.PermissionEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;

import java.util.List;

/**
 * Created by kriyss on 02/09/2014.
 */
public class PluginEntityBuilder {
    private String name;
    private String version;
    private String completeClassName;
    private List<CommandGroupEntity> commandGroups;
    private PermissionEntity permission;
    private List<String> eventHandler;

    private PluginEntityBuilder() {
    }

    public static PluginEntityBuilder aPluginEntity() {
        return new PluginEntityBuilder();
    }

    public PluginEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PluginEntityBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public PluginEntityBuilder withCompleteClassName(String completeClassName) {
        this.completeClassName = completeClassName;
        return this;
    }

    public PluginEntityBuilder withCommandGroups(List<CommandGroupEntity> commandGroups) {
        this.commandGroups = commandGroups;
        return this;
    }

    public PluginEntityBuilder withPermission(PermissionEntity permission) {
        this.permission = permission;
        return this;
    }

    public PluginEntityBuilder withEventHandler(List<String> eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    public PluginEntity build() {
        PluginEntity pluginEntity = new PluginEntity();
        pluginEntity.setName(name);
        pluginEntity.setVersion(version);
        pluginEntity.setCompleteClassName(completeClassName);
        pluginEntity.setCommandGroups(commandGroups);
        pluginEntity.setPermission(permission);
        pluginEntity.setEventHandler(eventHandler);
        return pluginEntity;
    }
}
