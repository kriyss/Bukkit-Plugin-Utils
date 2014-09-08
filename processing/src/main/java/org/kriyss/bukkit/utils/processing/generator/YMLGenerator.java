package org.kriyss.bukkit.utils.processing.generator;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.entity.*;

import static java.text.MessageFormat.format;

public class YMLGenerator {

    private YMLGenerator() {
        // YOUUU SHAAALLLL NOOOOOTTTTT INSTANNCCIIIAAAATTTEEE MMEEEEEEE!!!!
    }

    private static final String TAB                             = "    ";
    private static final String YML_HEADER                      = "name: {0}\nmain: {1}"+PluginGenerator.SUFFIX_PLUGIN_CLASS+"\nversion: {2}\n";
    private static final String COMMAND_YML_HEADER              = "commands: \n";
    private static final String COMMAND_YML_NAME                = TAB + "{0}:\n";
    private static final String COMMAND_YML_DESCRIPTION         = TAB + TAB + "description: {0}\n";
    private static final String COMMAND_YML_USAGE               = TAB + TAB + "usage: {0}\n";
    private static final String COMMAND_YML_PERMISSION          = TAB + TAB + "permission: {0}\n";
    private static final String COMMAND_YML_PERMISSION_MESSAGE  = TAB + TAB + "permission-message: {0}\n";

    private static final String PARAM_FIELD_PATTERN             = "[{0}] ";
    private static final String PARAM_FIELD_PATTERN_OPTIONNAL   = "[{0}(optionnal)] ";
    private static final String PARAM_COMMAND_PATTERN           = "/{0} ";

    public static String generate(PluginEntity plugin) {
        StringBuilder ymlFileBuilder =
                new StringBuilder(format(YML_HEADER,
                        plugin.getName(),
                        plugin.getCompleteClassName(),
                        plugin.getVersion()))
                    .append(COMMAND_YML_HEADER);
        for (CommandGroupEntity group : plugin.getCommandGroups()) {
            for (CommandEntity command : group.getCommands()) {
                ymlFileBuilder.append(getCommandYmlPart(group, command));
            }
        }
        return ymlFileBuilder.toString();
    }

    private static String getCommandYmlPart(CommandGroupEntity group, CommandEntity command) {
        // This part are always valued
        StringBuilder sb = new StringBuilder(format(COMMAND_YML_NAME, group.getRootCommand() + command.getCommandValue()));
        sb.append(format(COMMAND_YML_DESCRIPTION, command.getDescription()));
        sb.append(format(COMMAND_YML_USAGE, getUsage(group, command)));

        PermissionEntity perm = command.getPermission();
        // Permission may be optionnal
        if (null != perm && StringUtils.isNotBlank(perm.getValue())) {
            sb.append(format(COMMAND_YML_PERMISSION, command.getPermission().getValue()));
            sb.append(format(COMMAND_YML_PERMISSION_MESSAGE, command.getPermission().getMessage()));
        }
        return sb.toString();
    }

    private static String getUsage(CommandGroupEntity groupEntity, CommandEntity commandEntity) {
        StringBuilder sbArg = new StringBuilder(format(PARAM_COMMAND_PATTERN, groupEntity.getRootCommand() + commandEntity.getCommandValue()));
        for (ParamEntity paramEntity : commandEntity.getParamEntities()) {
            sbArg.append(format(paramEntity.isRequired() ? PARAM_FIELD_PATTERN : PARAM_FIELD_PATTERN_OPTIONNAL, paramEntity.getName()));
        }
        return sbArg.toString();
    }
}
