package org.kriyss.bukkit.utils.annotations.proc.utils;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.annotations.proc.entity.ParamEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.PluginEntity;

import static java.text.MessageFormat.format;

public class PluginYMLUtils {

    private PluginYMLUtils() {
        // YOUUU SHAAALLLL INSTANNCCIIIAAAATTTEEE MMEEEEEEE!!!!
    }

    private static final String TAB                             = "    ";
    private static final String YML_HEADER                      = "name: {0}\nmain: {1}\nversion: {2}\n";
    private static final String COMMAND_YML_HEADER              = "commands: \n";
    private static final String COMMAND_YML                     = TAB + "{0}:\n";
    private static final String COMMAND_YML_DESCRIPTION         = TAB + TAB + "description: {0}\n";
    private static final String COMMAND_YML_USAGE               = TAB + TAB + "usage: {0}\n";
    private static final String COMMAND_YML_PERMISSION          = TAB + TAB + "permission: {0}\n";
    private static final String COMMAND_YML_PERMISSION_MESSAGE  = TAB + TAB + "permission-message: {0}\n";


    private static final String ARG_COMMAND_PATTERN             = "/{0} ";
    private static final String ARG_FIELD_PATTERN               = "[{0}] ";
    private static final String ARG_FIELD_PATTERN_OPTIONNAL     = "[{0}(optionnal)] ";

    public static String generateConfigFileSource(PluginEntity plug) {
        String ymlHeader = format(YML_HEADER, plug.getName(), plug.getCompleteClassName(), plug.getVersion());
        StringBuilder ymlFileBuilder = new StringBuilder(ymlHeader).append(COMMAND_YML_HEADER);

        for (CommandGroupEntity groupEntity : plug.getCommandGroups()) {
            for (CommandEntity commandEntity : groupEntity.getCommands()) {
                ymlFileBuilder = getCommandYmlPart(ymlFileBuilder, groupEntity, commandEntity);
            }
        }
        return ymlFileBuilder.toString();
    }

    private static StringBuilder getCommandYmlPart(StringBuilder sb, CommandGroupEntity group, CommandEntity command) {
        // This part are always valued
        sb.append(format(COMMAND_YML, group.getRootCommand() + command.getCommandValue()));
        sb.append(format(COMMAND_YML_DESCRIPTION, command.getDescription()));
        sb.append(format(COMMAND_YML_USAGE, getUsage(group, command)));
        // Permission may be optionnal
        if (!StringUtils.isBlank(command.getPermission()) && !StringUtils.isBlank(command.getPermissionMessage())){
            sb.append(format(COMMAND_YML_PERMISSION, command.getPermission()));
            sb.append(format(COMMAND_YML_PERMISSION_MESSAGE, command.getPermissionMessage()));
        }
        return sb;
    }

    private static String getUsage(CommandGroupEntity groupEntity, CommandEntity commandEntity) {
        StringBuilder sbArg = new StringBuilder(format(ARG_COMMAND_PATTERN, groupEntity.getRootCommand() + commandEntity.getCommandValue()));
        for (ParamEntity paramEntity : commandEntity.getArgEntities()) {
            sbArg.append(format(paramEntity.isRequired() ? ARG_FIELD_PATTERN : ARG_FIELD_PATTERN_OPTIONNAL, paramEntity.getName()));
        }
        return sbArg.toString();
    }
}
