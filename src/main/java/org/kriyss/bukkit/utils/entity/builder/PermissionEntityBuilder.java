package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.PermissionEntity;

/**
 * Created on 27/08/2014.
 */
public class PermissionEntityBuilder {
    private String value;
    private String message;
    private boolean forAdmin = false;
    private boolean forConsole = false;

    private PermissionEntityBuilder() {
    }

    public static PermissionEntityBuilder aPermissionEntity() {
        return new PermissionEntityBuilder();
    }

    public PermissionEntityBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public PermissionEntityBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public PermissionEntityBuilder withForAdmin(boolean forAdmin) {
        this.forAdmin = forAdmin;
        return this;
    }

    public PermissionEntityBuilder withForConsole(boolean forConsole) {
        this.forConsole = forConsole;
        return this;
    }

    public PermissionEntity build() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setValue(value);
        permissionEntity.setMessage(message);
        permissionEntity.setForAdmin(forAdmin);
        permissionEntity.setForConsole(forConsole);
        return permissionEntity;
    }
}
