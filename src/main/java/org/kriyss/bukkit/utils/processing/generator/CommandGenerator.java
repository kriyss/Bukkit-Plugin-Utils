package org.kriyss.bukkit.utils.processing.generator;

import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.ParamEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;
import org.kriyss.bukkit.utils.processing.utils.FileSaver;
import org.kriyss.bukkit.utils.processing.utils.source.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

import static org.kriyss.bukkit.utils.processing.utils.BukkitUtils.*;

/**
 * Created by kriyss on 02/09/2014.
 */
public class CommandGenerator {

    private static final String SUFFIX_COMMAND_CLASS = "Executor";
    private static final String PERM_SRC =  "\t\tif (!commandSender.hasPermission(\"{0}\")) '{' errors.add(ChatColor.RED + \"{1}\");'}' else if(!haveRight)'{' haveRight = true; '}'\n";
    private static final String PERM_CONSO =  "\t\tif (!(commandSender instanceof Player)) { errors.add(ChatColor.RED + \"You aren't CONSOLE\");}else if(!haveRight){ haveRight = true; }\n";
    private static final String PERM_ADM =  "\t\tif (!commandSender.isOp()) { errors.add(ChatColor.RED + \"You aren't OP.\");}else if(!haveRight){ haveRight = true; }\n\n";

    private static final String STRING_VARIABLE_DECLARATION = "\t\tString {0} = ({1} < strings.length) ? strings[{1}] : null;\n";
    private static final String INTEGER_VARIABLE_DECLARATION = "\t\tInteger {0} = ({1} < strings.length) && NumberUtils.isNumber(strings[{1}]) ? Integer.valueOf(strings[{1}]) : null;\n";
    private static final String COMMAND_EXECUTOR_PATTERN =
            "package {0};\n\n"
                    + "import {1};\n"
                    + "import org.bukkit.entity.Player;\n"
                    + "import org.apache.commons.lang.math.NumberUtils;\n"
                    + "import org.apache.commons.lang.StringUtils;\n"
                    + "import java.util.List;\n"
                    + "import java.util.ArrayList;\n"
                    + "import org.bukkit.command.Command;\n"
                    + "import org.bukkit.command.CommandExecutor;\n"
                    + "import org.bukkit.ChatColor;\n"
                    + "import org.bukkit.command.CommandSender;\n\n"
                    + "public class {2}"
                    +  SUFFIX_COMMAND_CLASS
                    +"  extends {3} implements CommandExecutor'{'\n"
                    + "\t@Override\n"
                    + "\tpublic boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) '{'\n"
                    + "\t\tList<String> errors = new ArrayList<String>();\n"
                    + "\t\t // Variables declaration\n{4}\n"
                    + "{5}\n"
                    + "\t\tif(!errors.isEmpty()) '{' \n"
                    + "\t\t\tfor(String message : errors)'{' \n"
                    + "\t\t\t\tcommandSender.sendMessage(message);\n"
                    + "\t\t\t'}'\n"
                    + "\t\t\treturn true;\n"
                    + "\t\t'}'\n"
                    + "\t\treturn super.{6};\n"
                    + "\t'}'\n"
                    + "'}'";
    private static final String CHECK_STRING_REQUIRED = "\t\tif(StringUtils.isBlank({0}))'{'\n" +
            "\t\t\terrors.add(\"[{0}] is required\");\n" +
            "\t\t'}' else '{'\n" +
            "{1} \t\t'}'\n";

    private static final String CHECK_STRING = "\t\tif({0}.length() < {1})\n"
            + "\t\t\t\terrors.add(\"[{0}] has to be highter than {1}\");\n"
            + "\t\t\tif({0}.length() > {2})\n"
            + "\t\t\t\terrors.add(\"[{0}] has to be smaller than {2}\");\n";
    private static final String CHECK_INTEGER_REQUIRED = "\t\tif({0} == null)'{'\n" +
            "\t\t\terrors.add(\"[{0}] is required\");\n" +
            "\t\t'}' else '{'\n" +
            "{1} \t\t'}'\n";
    private static final String CHECK_INTEGER_NOT_EQUIRED = "\t\tif({0} != null)'{'\n" +
            "{1} \t\t'}'\n";

    private static final String CHECK_INTEGER = "\t\tif({0} < {1})\n"
            + "\t\t\terrors.add(\"[{0}] has to be highter than {1}\");\n"
            + "\t\t\n"
            + "\t\tif({0} > {2})\n"
            + "\t\t\terrors.add(\"[{0}] has to be smaller than {2}\");\n";

    public static Map<String, String> generate(PluginEntity pluginEntity, FileSaver saver) {
        Map<String, String> completeCommandExecutorClass = Maps.newHashMap();
        for (CommandGroupEntity group : pluginEntity.getCommandGroups()) {
            for (CommandEntity command : group.getCommands()) {
                completeCommandExecutorClass.put(
                        group.getRootCommand() + command.getCommandValue(),
                        generateCommandExecutor(group, command, saver) + SUFFIX_COMMAND_CLASS);
            }
        }
        return completeCommandExecutorClass;
    }

    private static String generateCommandExecutor(CommandGroupEntity groupEntity, CommandEntity commandEntity, FileSaver saver) {
        String src = generate(groupEntity, commandEntity);
        final String commandExecutorcompleteClass = getCompleteCommandExecutorClass(groupEntity, commandEntity);
        saver.createNewJavaFile(commandExecutorcompleteClass, src, SUFFIX_COMMAND_CLASS);
        return commandExecutorcompleteClass;
    }

    private static String generate(CommandGroupEntity group, CommandEntity command) {

        return ClassBuilder.aClassBuilder()
                .withVisibility(Visibility.PUBLIC)
                .withClassName(getCommandExecutorClass(group, command) + SUFFIX_COMMAND_CLASS)
                .withExtendOf(getClassFromCompleteName(group.getCompleteClassName()))
                .withPackageName(getPackageFromCompleteClass(group))
                .withImplementsOf(Arrays.asList("org.bukkit.command.CommandExecutor"))
                .withMethods(Arrays.asList(
                        MethodBuilder.aMethod()
                                .withName("onCommand")
                                .withVisibility(Visibility.PUBLIC)
                                .withReturnClazz(boolean.class)
                                .withBody("\t\treturn super."+generateSuperMethodCall(command)+";\n")
                                .withParameters(Arrays.asList(
                                        new Parameter("sender", CommandSender.class),
                                        new Parameter("command", Command.class),
                                        new Parameter("s", String.class),
                                        new Parameter("strings", String.class, true)
                                ))
                        .build()
                ))
                .build();
    }


    private static String generateSuperMethodCall(CommandEntity command) {
        StringBuilder sb = new StringBuilder(command.getCommandMethodName()+"(sender");
        for (ParamEntity paramEntity : command.getParamEntities()) {
            sb.append(", ").append(paramEntity.getName());
        }
        return sb.append(")").toString();
    }

    private static String generateChecks(CommandEntity command) {
        StringBuilder checks = new StringBuilder();
        for (ParamEntity paramEntity : command.getParamEntities()) {
            if (isString(paramEntity.getType())) {
                checks.append(generateStringChecks(paramEntity));
            }else if(isInteger(paramEntity.getType())){
                checks.append(generateIntegerChecks(paramEntity));
            }
        }
        return checks.toString();
    }

    private static String generateIntegerChecks(ParamEntity paramEntity) {
        String check = MessageFormat.format(CHECK_INTEGER, paramEntity.getName(), Integer.toString(paramEntity.getMin()), Integer.toString(paramEntity.getMax()));
        if (paramEntity.isRequired()){
            return MessageFormat.format(CHECK_INTEGER_REQUIRED, paramEntity.getName(), check);
        } else {
            return MessageFormat.format(CHECK_INTEGER_NOT_EQUIRED, paramEntity.getName(), check);
        }
    }

    private static String generateStringChecks(ParamEntity paramEntity) {
        String check = MessageFormat.format(CHECK_STRING, paramEntity.getName(), Integer.toString(paramEntity.getMin()), Integer.toString(paramEntity.getMax()));
        return paramEntity.isRequired() ? MessageFormat.format(CHECK_STRING_REQUIRED, paramEntity.getName(), check) : check;
    }

    private static String generateVariablesSource(CommandEntity command) {
        StringBuilder declaratedVariablesSource = new StringBuilder();
        int argsNumber = 0;
        for (ParamEntity paramEntity : command.getParamEntities()) {
            if (isString(paramEntity.getType())) {
                declaratedVariablesSource.append(MessageFormat.format(STRING_VARIABLE_DECLARATION, paramEntity.getName(), argsNumber));
            } else if (isInteger(paramEntity.getType())) {
                declaratedVariablesSource.append(MessageFormat.format(INTEGER_VARIABLE_DECLARATION, paramEntity.getName(), argsNumber));
            }
            argsNumber++;
        }
        return declaratedVariablesSource.toString();
    }
}
