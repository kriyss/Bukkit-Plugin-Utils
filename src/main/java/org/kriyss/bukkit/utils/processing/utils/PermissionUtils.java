package org.kriyss.bukkit.utils.processing.utils;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.entity.*;

/**
 * Created on 27/08/2014.
 */
public class PermissionUtils {

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
        return sb.substring(0, sb.lastIndexOf(separator));
    }

    public static String getPermissionMessage(HasPermission... permissions){
        for (HasPermission perm : permissions) {
            if(StringUtils.isNotBlank(perm.getPermission().getMessage())) return perm.getPermission().getMessage();
        }
        return null;
    }
}
