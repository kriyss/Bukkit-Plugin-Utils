package org.kriyss.bukkit.utils.processing.scanner;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.HasPermission;
import org.kriyss.bukkit.utils.entity.PluginEntity;
import org.kriyss.bukkit.utils.entity.builder.PermissionEntityBuilder;

public class PermissionHelper {

    private PermissionHelper() {
    }

    private static String generatePermission(String pluginName, HasPermission... permissions) {
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

    private static String getPermissionMessage(HasPermission... permissions) {
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

    private static boolean getPermissionConsole(HasPermission... permissions) {
        for (HasPermission permission : permissions) {
            if (permission != null && permission.getPermission() != null)
                return permission.getPermission().isForConsole();
        }
        return false;
    }

    private static boolean getPermissionAdmin(HasPermission... permissions) {
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