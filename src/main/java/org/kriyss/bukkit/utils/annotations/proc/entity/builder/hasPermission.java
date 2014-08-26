package org.kriyss.bukkit.utils.annotations.proc.entity.builder;

public interface HasPermission<T> {
    T withPermission(String permission);
    T withPermissionMessage(String permissionMessage);
}
