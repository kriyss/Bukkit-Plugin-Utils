package org.kriyss.bukkit.utils.processing;

import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.ParamEntity;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;

import java.text.MessageFormat;

public class GenerateCommand {

    private static final String STRING_VARIABLE_DECLARATION = "\t\tString {0} = strings[{1}];\n";
    private static final String INTEGER_VARIABLE_DECLARATION = "\t\tint {0} = Integer.valueOf(strings[{1}]);\n";
    private static final String COMMAND_EXECUTOR_PATTERN =
            "package {0};\n\n"
                    + "import {1};\n"
                    + "import org.apache.commons.lang.StringUtils;\n"
                    + "import java.util.List;\n"
                    + "import java.util.ArrayList;\n"
                    + "import org.bukkit.command.Command;\n"
                    + "import org.bukkit.command.CommandExecutor;\n"
                    + "import org.bukkit.ChatColor;\n"
                    + "import org.bukkit.command.CommandSender;\n\n"
                    + "public class {2}"
                    + Const.SUFFIX_COMMAND_CLASS
                    +"  extends {3} implements CommandExecutor'{'\n"
                    + "\t@Override\n"
                    + "\tpublic boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) '{'\n"
                    + "\t\tList<String> errors = new ArrayList<String>();\n"
                    + "{4}"
                    + "{5}"
                    + "\t\treturn super.{6};\n"
                    + "\t'}'\n"
                    + "'}'";

    public static String generate(CommandGroupEntity commandGroup, CommandEntity command) {
        String completeClassName = commandGroup.getCompleteClassName();
        String packageTarget = BukkitUtils.getPackageFromCompleteClass(commandGroup);
        String className = BukkitUtils.getCommandExecutorClass(commandGroup, command);
        String extendsOf = BukkitUtils.getClassFromCompleteName(completeClassName);

        //TODO permission avant !!!!
        // verfication
        String declaratedVariablesSource = generateVariablesSource(command);
        String checks = generateChecks(command);
        String methodCall = generateSuperMethodCall(command);

        return MessageFormat.format(COMMAND_EXECUTOR_PATTERN,
                packageTarget,
                completeClassName,
                className,
                extendsOf,
                declaratedVariablesSource,
                checks,
                methodCall);
    }

    private static String generateSuperMethodCall(CommandEntity command) {
        StringBuilder sb = new StringBuilder(command.getCommandMethodName()+"(commandSender");
        for (ParamEntity paramEntity : command.getParamEntities()) {
            sb.append(", ").append(paramEntity.getName());
        }
        return sb.append(")").toString();
    }

    private static String generateChecks(CommandEntity command) {
        StringBuilder checks = new StringBuilder();
        for (ParamEntity paramEntity : command.getParamEntities()) {
            if (BukkitUtils.isString(paramEntity.getType())) {
                checks.append(generateStringChecks(paramEntity));
            }else if(BukkitUtils.isInteger(paramEntity.getType())){
                checks.append(generateIntegerChecks(paramEntity));
            }
        }
        return checks.toString();
    }

    private static String generateIntegerChecks(ParamEntity paramEntity) {
        StringBuilder sb2 = new StringBuilder("\t\tif(")
                .append(paramEntity.getName())
                .append(" <").append(paramEntity.getMin())
                .append("){\n\t\t\terrors.add(\"[")
                .append(paramEntity.getName())
                .append("] has to be highter than ")
                .append(paramEntity.getMin())
                .append("\");\n\t\t}\n")
                .append("\t\tif(")
                .append(paramEntity.getName())
                .append(" >")
                .append(paramEntity.getMax())
                .append("){\n\t\t\terrors.add(\"[")
                .append(paramEntity.getName())
                .append("] has to be smaller than ")
                .append(paramEntity.getMax())
                .append("\");\n\t\t}\n");
        return sb2.toString();
    }

    private static String generateStringChecks(ParamEntity paramEntity) {
        StringBuilder checks = new StringBuilder();
        if (paramEntity.isRequired()) {
            checks.append("\t\tif(StringUtils.isBlank(")
                    .append(paramEntity.getName())
                    .append(")){\n\t\t\terrors.add(\"[")
                    .append(paramEntity.getName())
                    .append("] is required\");\n\t\t}\n");
        }
        checks.append("\t\tif(").append(paramEntity.getName()).append(".length() <").append(paramEntity.getMin())
                .append("){\n\t\t\terrors.add(\"[")
                .append(paramEntity.getName()).append("] has to be highter than ").append(paramEntity.getMin()).append("\");\n\t\t}\n")
                .append("\t\tif(").append(paramEntity.getName()).append(".length() >").append(paramEntity.getMax())
                .append("){\n\t\t\terrors.add(\"[")
                .append(paramEntity.getName()).append("] has to be smaller than ").append(paramEntity.getMax()).append("\");\n\t\t}\n");
        return checks.toString();
    }

    private static String generateVariablesSource(CommandEntity command) {
        StringBuilder declaratedVariablesSource = new StringBuilder();
        int argsNumber = 0;
        for (ParamEntity paramEntity : command.getParamEntities()) {
            if (BukkitUtils.isString(paramEntity.getType())) {
                declaratedVariablesSource.append(MessageFormat.format(STRING_VARIABLE_DECLARATION, paramEntity.getName(), argsNumber));
            } else if (BukkitUtils.isInteger(paramEntity.getType())) {
                declaratedVariablesSource.append(MessageFormat.format(INTEGER_VARIABLE_DECLARATION, paramEntity.getName(), argsNumber));
            }
            argsNumber++;
        }
        return declaratedVariablesSource.toString();
    }
}
