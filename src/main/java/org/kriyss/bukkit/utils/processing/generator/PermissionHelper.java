package org.kriyss.bukkit.utils.processing.generator;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.entity.*;
import org.kriyss.bukkit.utils.entity.builder.PermissionEntityBuilder;

/**
 * Created on 27/08/2014.
 */
public class PermissionHelper {

    private PermissionHelper() {
    }

    public static String generatePermission(String pluginName, HasPermission... permissions) {
        String separator = ".";
        StringBuilder sb = new StringBuilder(pluginName).append(separator);
        for (HasPermission perm : permissions) {
            if (null != perm
                    && null != perm.getPermission()
                    && StringUtils.isNotBlank(perm.getPermission().getValue())) {
                sb.append(perm.getPermission().getValue()).append(separator);
            }
        }
        final String permission = sb.substring(0, sb.lastIndexOf(separator));
        return permission.equals(pluginName) ? "" : permission;
    }

    public static String getPermissionMessage(HasPermission... permissions) {
        String permissionMessage = Const.DEFAULT_FORBIDEN_MESSAGE;
        for (HasPermission perm : permissions) {
            final String message = perm.getPermission().getMessage();
            if (StringUtils.isNotBlank(message)) {
                permissionMessage = message;
                break;
            }
        }
        return permissionMessage;
    }

    public static boolean getPermissionConsole(HasPermission... permissions) {
        for (HasPermission permission : permissions) {
            if (permission != null && permission.getPermission() != null)
                return permission.getPermission().isForConsole();
        }
        return false;
    }

    public static boolean getPermissionAdmin(HasPermission... permissions) {
        for (HasPermission permission : permissions) {
            if (permission != null && permission.getPermission() != null)
                return permission.getPermission().isForAdmin();
        }
        return false;
    }

    public static PluginEntity populatePermissions(PluginEntity plugin) {
        for (CommandGroupEntity commandGroup : plugin.getCommandGroups()) {
            for (CommandEntity command : commandGroup.getCommands()) {
                String permission = generatePermission(plugin.getName(), plugin, commandGroup, command);
                if (StringUtils.isNotBlank(permission)) {
                    command.setPermission(PermissionEntityBuilder.aPermissionEntity()
                            .withValue(permission)
                            .withMessage(getPermissionMessage(command, commandGroup))
                            .withForAdmin(getPermissionAdmin(command, commandGroup, plugin))
                            .withForConsole(getPermissionConsole(command, commandGroup, plugin))
                            .build());
                }
            }
        }
        return plugin;
    }
}