package org.kriyss.bukkit.utils.processing;

import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.ParamEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;

import java.text.MessageFormat;

/**
 * Created on 28/08/2014.
 */
public class GenerateCommand {

    static final String COMMAND_EXECUTOR_PATTERN =
            "package {0};\n\n"
                    + "import {1};\n"
                    + "import org.apache.commons.lang.StringUtils;\n"
                    + "import org.bukkit.command.Command;\n"
                    + "import org.bukkit.command.CommandExecutor;\n"
                    + "import org.bukkit.ChatColor;\n"
                    + "import org.bukkit.command.CommandSender;\n\n"
                    + "public class {2}"
                    + Const.SUFFIX_COMMAND_CLASS
                    +"  extends {3} implements CommandExecutor'{'\n"
                    + "\t@Override\n"
                    + "\tpublic boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) '{'\n"
                    + "{4}"
                    + "{5}"
                    + "\t\treturn super.{6};\n"
                    + "\t'}'\n"

                    + "'}'";

    public static String generate(PluginEntity plugin, CommandGroupEntity commandGroup, CommandEntity command) {
        final String completeClassName = commandGroup.getCompleteClassName();
        String packageTarget = completeClassName.substring(0, completeClassName.lastIndexOf('.'));


        String className = Character.toUpperCase(command.getCommandMethodName().charAt(0)) + command.getCommandMethodName().substring(1);

        String extendsOf = completeClassName.substring(completeClassName.lastIndexOf('.') + 1, completeClassName.length());

        StringBuilder sb1 = new StringBuilder();
        int i = 0;
        for (ParamEntity paramEntity : command.getParamEntities()) {
            if (paramEntity.getType().equals("java.lang.String")) {
                sb1.append("\t\tString ")
                        .append(paramEntity.getName())
                        .append(" = strings[")
                        .append(i)
                        .append("];\n");
            } else if (paramEntity.getType().equals("int")) {
                sb1.append("\t\tint ")
                        .append(paramEntity.getName())
                        .append(" = Integer.valueOf(strings[")
                        .append(i)
                        .append("]);\n");
            }
            i++;
        }
        //TODO permission avant !!!!

        // verfication
        StringBuilder sb2 = new StringBuilder();
        for (ParamEntity paramEntity : command.getParamEntities()) {
            if (paramEntity.getType().equals("java.lang.String")) {
                if (paramEntity.isRequired()) {
                    sb2.append("\t\tif(StringUtils.isBlank(")
                            .append(paramEntity.getName())
                            .append(")){\n\t\t\tcommandSender.sendMessage(\"[")
                            .append(paramEntity.getName())
                            .append("] is required\");\n\t\t\treturn false;\n\t\t}\n");
                }
                sb2.append("\t\tif(").append(paramEntity.getName()).append(".length() <").append(paramEntity.getMin())
                        .append("){\n\t\t\tcommandSender.sendMessage(\"[")
                        .append(paramEntity.getName()).append("] has to be highter than ").append(paramEntity.getMin()).append("\");\n\t\t\treturn false;\n\t\t}\n");

                sb2.append("\t\tif(").append(paramEntity.getName()).append(".length() >").append(paramEntity.getMax())
                        .append("){\n\t\t\tcommandSender.sendMessage(\"[")
                        .append(paramEntity.getName()).append("] has to be smaller than ").append(paramEntity.getMax()).append("\");\n\t\t\treturn false;\n\t\t}\n");
            }else if(paramEntity.getType().equals("int")){
                sb2.append("\t\tif(").append(paramEntity.getName()).append(" <").append(paramEntity.getMin())
                        .append("){\n\t\t\tcommandSender.sendMessage(\"[")
                        .append(paramEntity.getName()).append("] has to be highter than ").append(paramEntity.getMin()).append("\");\n\t\t\treturn false;\n\t\t}\n");

                sb2.append("\t\tif(").append(paramEntity.getName()).append(" >").append(paramEntity.getMax())
                        .append("){\n\t\t\tcommandSender.sendMessage(\"[")
                        .append(paramEntity.getName()).append("] has to be smaller than ").append(paramEntity.getMax()).append("\");\n\t\t\treturn false;\n\t\t}\n");
            }
        }

        StringBuilder sb = new StringBuilder(command.getCommandMethodName()+"(commandSender");
        for (ParamEntity paramEntity : command.getParamEntities()) {
            sb.append(", ").append(paramEntity.getName());
        }
        sb.append(")");
        return MessageFormat.format(COMMAND_EXECUTOR_PATTERN, packageTarget, completeClassName, className, extendsOf, sb1,sb2, sb);
    }
}
