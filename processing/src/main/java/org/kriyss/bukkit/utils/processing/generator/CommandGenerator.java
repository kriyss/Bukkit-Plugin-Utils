package org.kriyss.bukkit.utils.processing.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.kriyss.bukkit.utils.entity.*;
import org.kriyss.bukkit.utils.processing.utils.FileSaver;
import org.kriyss.bukkit.utils.processing.utils.source.ClassBuilder;
import org.kriyss.bukkit.utils.processing.utils.source.Method;
import org.kriyss.bukkit.utils.processing.utils.source.Parameter;
import org.kriyss.bukkit.utils.processing.utils.source.Visibility;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.kriyss.bukkit.utils.processing.utils.BukkitUtils.*;

/**
 * Created by kriyss on 02/09/2014.
 */
public class CommandGenerator {

    private static final String SUFFIX_COMMAND_CLASS = "Executor";
    private static final String STRING_VARIABLE_DECLARATION = "\t\tString {0} = ({1} < strings.length) ? strings[{1}] : null;\n";
    private static final String INTEGER_VARIABLE_DECLARATION = "\t\tInteger {0} = ({1} < strings.length) && StringUtils.isNumeric(strings[{1}]) ? Integer.valueOf(strings[{1}]) : null;\n";
    private static final String CHECK_STRING_REQUIRED = "\t\tif(StringUtils.isBlank({0}))'{'\n" +
            "\t\t\tthrow new InvalidParameterException(\"{0} is required\");\n" +
            "\t\t'}' else '{'\n" +
            "{1} \t\t'}'\n";

    private static final String CHECK_STRING = "\t\tif({0}.length() < {1})\n"
            + "\t\t\t\tthrow new InvalidParameterException(\"[{0}] has to be highter than {1}\");\n"
            + "\t\t\tif({0}.length() > {2})\n"
            + "\t\t\t\tthrow new InvalidParameterException(\"[{0}] has to be smaller than {1}\");\n";
    private static final String CHECK_INTEGER_REQUIRED = "\t\tif({0} == null)'{'\n" +
            "\t\t\tthrow new InvalidParameterException(\"[{0}] is required\");\n" +
            "\t\t'}' else '{'\n" +
            "{1} \t\t'}'\n";
    private static final String CHECK_INTEGER_NOT_EQUIRED = "\t\tif({0} != null)'{'\n" +
            "{1} \t\t'}'\n";

    private static final String CHECK_INTEGER = "\t\tif({0} < {1})\n"
            + "\t\t\tthrow new InvalidParameterException(\"[{0}] has to be highter than {1}\");\n"
            + "\t\t\n"
            + "\t\tif({0} > {2})\n"
            + "\t\t\tthrow new InvalidParameterException(\"[{0}] has to be smaller than {2}\");\n";

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
                .withImports(Arrays.asList(
                        "org.bukkit.entity.Player",
                        "org.bukkit.ChatColor",
                        "org.apache.commons.lang.StringUtils",
                        "org.kriyss.bukkit.utils.exception.exception.InvalidParameterException",
                        "org.kriyss.bukkit.utils.exception.exception.InvalidPermissionException"))
                .withMethods(Arrays.asList(
                        getOnCommandMethod(command),
                        getCheckParameterMethod(command),
                        getCheckPermissionMethod(command)
                ))
                .build();
    }

    private static Method getCheckPermissionMethod(CommandEntity command) {
        String permissionBody = "\t\tif(!(sender.isOp() || !(sender instanceof Player) || sender.hasPermission(\"{0}\")))\n" +
                "\t\t\tthrow new InvalidPermissionException(\"{1}\");\n";
        PermissionEntity permission = command.getPermission();
        return Method.MethodBuilder.aMethod()
                    .withName("checkPermission")
                    .withVisibility(Visibility.PRIVATE)
                    .withReturnClazz(void.class)
                    .withExceptionsClazz(Arrays.asList("InvalidPermissionException"))
                    .withBody(MessageFormat.format(permissionBody, permission.getValue(), permission.getMessage()))
                    .withParameters(Arrays.asList(new Parameter("sender", CommandSender.class)))
                    .build();
    }

    private static Method getCheckParameterMethod(CommandEntity command) {
        List<Parameter> params = Lists.newArrayList();
        for (ParamEntity paramEntity : command.getParamEntities()) {
            params.add(new Parameter(paramEntity.getName(), paramEntity.getType().toString()));
        }

        return Method.MethodBuilder.aMethod()
                    .withName("checkParameters")
                    .withVisibility(Visibility.PRIVATE)
                    .withReturnClazz(void.class)
                    .withExceptionsClazz(Arrays.asList("org.kriyss.bukkit.utils.exception.exception.InvalidParameterException"))
                    .withParameters(params)
                    .withBody(generateChecks(command))
                    .build();
    }

    private static Method getOnCommandMethod(CommandEntity command) {
        String body = generateVariablesSource(command)
                + "\t\ttry {\n"
                + "\t\t\tcheckPermission(sender);\n"
                + "\t\t\tcheckParameters("
                + formatParameter(command.getParamEntities())
                + ");\n"
                + "\t\t\t" + generateSuperMethodCall(command)
                + "\t\t}catch(InvalidParameterException e) {\n"
                + "\t\t\tsender.sendMessage(ChatColor.YELLOW + e.getMessage());\n"
                + "\t\t\treturn false;\n"
                + "\t\t}catch(InvalidPermissionException e) {\n"
                + "\t\t\tsender.sendMessage(ChatColor.RED + e.getMessage());\n"
                + "\t\t\treturn true;\n"
                + "\t\t}\n";
        return Method.MethodBuilder.aMethod()
                    .withName("onCommand")
                    .withVisibility(Visibility.PUBLIC)
                    .withReturnClazz(boolean.class)
                    .withBody(body)
                    .withParameters(Arrays.asList(
                            new Parameter("sender", CommandSender.class),
                            new Parameter("command", Command.class),
                            new Parameter("s", String.class),
                            new Parameter("strings", String.class, true)
                    ))
                    .build();
    }

    private static String formatParameter(List<ParamEntity> paramEntities) {
        StringBuilder sb = new StringBuilder();
        for (ParamEntity paramEntity : paramEntities) {
            sb.append(paramEntity.getName()).append(", ");
        }
        return sb.substring(0, sb.lastIndexOf(", ")
        );
    }


    private static String generateSuperMethodCall(CommandEntity command) {
        StringBuilder sb = new StringBuilder("return super.")
                .append(command.getCommandMethodName())
                .append("(sender");
        for (ParamEntity paramEntity : command.getParamEntities()) {
            sb.append(", ").append(paramEntity.getName());
        }
        return sb.append(");\n").toString();
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
