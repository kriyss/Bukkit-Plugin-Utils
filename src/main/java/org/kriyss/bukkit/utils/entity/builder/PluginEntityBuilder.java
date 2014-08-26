package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;

import java.util.List;

public class PluginEntityBuilder implements HasPermission<PluginEntityBuilder> {
    private String name;
    private String permission;
    private String permissionMessage;
    private String version;
    private String completeClassName;
    private List<CommandGroupEntity> commandGroups;

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

    public PluginEntityBuilder withPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public PluginEntityBuilder withPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    public PluginEntity build() {
        PluginEntity pluginEntity = new PluginEntity();
        pluginEntity.setName(name);
        pluginEntity.setVersion(version);
        pluginEntity.setCompleteClassName(completeClassName);
        pluginEntity.setCommandGroups(commandGroups);
        pluginEntity.setPermission(permission);
        pluginEntity.setPermissionMessage(permissionMessage);
        return pluginEntity;
    }
}
