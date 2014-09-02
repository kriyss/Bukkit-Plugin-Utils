package org.kriyss.bukkit.utils.processing.utils;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.entity.*;

/**
 * Created on 27/08/2014.
 */
public class PermissionUtils {

    private PermissionUtils() {
    }

    public static String generatePermission(String pluginName, HasPermission... permissions){
        String separator = ".";
        StringBuilder sb = new StringBuilder(pluginName).append(separator);
        for (HasPermission perm : permissions) {
            if(null != perm
                    && null != perm.getPermission()
                    && StringUtils.isNotBlank(perm.getPermission().getValue())) {
                sb.append(perm.getPermission().getValue()).append(separator);
            }
        }
        final String permission = sb.substring(0, sb.lastIndexOf(separator));
        return permission.equals(pluginName) ? "" : permission;
    }

    public static String getPermissionMessage(HasPermission... permissions){
        String permissionMessage = Const.DEFAULT_FORBIDEN_MESSAGE;
        for (HasPermission perm : permissions) {
            final String message = perm.getPermission().getMessage();
            if(StringUtils.isNotBlank(message)){
                permissionMessage = message;
                break;
            }
        }
        return permissionMessage;
    }
}