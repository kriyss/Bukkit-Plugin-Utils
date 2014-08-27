package org.kriyss.bukkit.utils.processing.utils;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.entity.ParamEntity;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;

import static java.text.MessageFormat.format;

public class PluginYMLUtils {

    private PluginYMLUtils() {
        // YOUUU SHAAALLLL NOOOOOTTTTT INSTANNCCIIIAAAATTTEEE MMEEEEEEE!!!!
    }

    private static final String TAB                             = "    ";
    private static final String YML_HEADER                      = "name: {0}\nmain: {1}\nversion: {2}\n";
    private static final String COMMAND_YML_HEADER              = "commands: \n";
    private static final String COMMAND_YML_NAME = TAB + "{0}:\n";
    private static final String COMMAND_YML_DESCRIPTION         = TAB + TAB + "description: {0}\n";
    private static final String COMMAND_YML_USAGE               = TAB + TAB + "usage: {0}\n";
    private static final String COMMAND_YML_PERMISSION          = TAB + TAB + "permission: {0}\n";
    private static final String COMMAND_YML_PERMISSION_MESSAGE  = TAB + TAB + "permission-message: {0}\n";


    private static final String ARG_COMMAND_PATTERN             = "/{0} ";
    private static final String ARG_FIELD_PATTERN               = "[{0}] ";
    private static final String ARG_FIELD_PATTERN_OPTIONNAL     = "[{0}(optionnal)] ";

    public static String generateConfigFileSource(PluginEntity plug) {
        StringBuilder ymlFileBuilder =
                new StringBuilder(format(YML_HEADER, plug.getName(), plug.getCompleteClassName(), plug.getVersion()))
                                .append(COMMAND_YML_HEADER);

        for (CommandGroupEntity groupEntity : plug.getCommandGroups()) {
            for (CommandEntity commandEntity : groupEntity.getCommands()) {
                ymlFileBuilder.append(getCommandYmlPart(groupEntity, commandEntity, plug.getName()));
            }
        }
        return ymlFileBuilder.toString();
    }

    private static String getCommandYmlPart(CommandGroupEntity group, CommandEntity command, String pluginName) {
        // This part are always valued
        StringBuilder sb = new StringBuilder(format(COMMAND_YML_NAME, group.getRootCommand() + command.getCommandValue()));
        sb.append(format(COMMAND_YML_DESCRIPTION, command.getDescription()));
        sb.append(format(COMMAND_YML_USAGE, getUsage(group, command)));

        // Permission may be optionnal
        String permission = concatIfNotNull(".", pluginName, group.getPermission().getValue(), command.getPermission().getValue());
        if (StringUtils.isNotBlank(permission)) {
            sb.append(format(COMMAND_YML_PERMISSION, permission));
            sb.append(format(COMMAND_YML_PERMISSION_MESSAGE,
                    getPermissionMessageOrDefault("You can't do that", command.getPermission().getMessage(), group.getPermission().getMessage())));
        }
        return sb.toString();
    }

    private static String getUsage(CommandGroupEntity groupEntity, CommandEntity commandEntity) {
        StringBuilder sbArg = new StringBuilder(format(ARG_COMMAND_PATTERN, groupEntity.getRootCommand() + commandEntity.getCommandValue()));
        for (ParamEntity paramEntity : commandEntity.getParamEntities()) {
            sbArg.append(format(paramEntity.isRequired() ? ARG_FIELD_PATTERN : ARG_FIELD_PATTERN_OPTIONNAL, paramEntity.getName()));
        }
        return sbArg.toString();
    }

    private static String concatIfNotNull(String separator, String... element){
        StringBuilder sb = new StringBuilder();
        for (String s : element) {
            if(StringUtils.isNotBlank(s)) sb.append(s).append(separator);
        }
        return sb.substring(0, sb.lastIndexOf(separator));
    }

    private static String getPermissionMessageOrDefault(String defaultMessage, String... permissionMessages){
        for (String s : permissionMessages) {
            if(StringUtils.isNotBlank(s)) return s;
        }
        return defaultMessage;
    }
}
