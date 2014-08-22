package org.nylmod.economy.annotations.proc.utils;

public interface GenerationConstant {

    static final String PERMISSION = "\tpublic static final Permission $NAME$ = new Permission(\"$VALUE\");\n";

    static final String COMMAND_EXECUTOR_PERMISSION_ADMIN =
            "\t\tif(false) '{'\n"
                    + "\t\t\tcommandSender.sendMessage(ChatColor.RED+\"{0}\");\n"
                    + "\t\t\treturn true;\n"
                    + "\t\t'}'\n";

    static final String COMMAND_EXECUTOR_PERMISSION =
            "\t\tif(!(commandSender instanceof Player && (commandSender.hasPermission(\"{1}\") || commandSender.isOp()))) '{'\n"
                    + "\t\t\tcommandSender.sendMessage(ChatColor.RED+\"{0} CONNARD\");\n"
                    + "\t\t\treturn true;\n"
                    + "\t\t'}'\n";

    static final String COMMAND_EXECUTOR_PATTERN =
            "package {0};\n\n"
                    + "import org.bukkit.command.Command;\n"
                    + "import org.bukkit.ChatColor;\n"
                    + "import org.bukkit.entity.Player;\n"
                    + "import org.bukkit.command.CommandSender;\n\n"
                    + "public class {1}Executor  extends {2} '{'\n"
                    + "\t@Override\n"
                    + "\tpublic boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) '{'\n"
                    + "{3}"
                    + "\t\treturn super.onCommand(commandSender, command, s, strings);\n"
                    + "\t'}'\n"
                    + "'}'";

    static final String COMMAND_EXECUTOR_HEADER =
            "package {0};\n\n"
                    + "{1}\n\n"
                    + "public class {2}Launcher extends {2} '{'\n"
                    + "\tpublic static {2} plugin;\n"
                    + "\t@Override\n"
                    + "\tpublic void onEnable() '{'\n"
                    + "\t\tplugin = this;\n";

    static final String IMPORT_EXECUTOR = "import {0}.{1}Executor;\n";

    static final String GET_COMMAND = "\t\tgetCommand(\"{0}\")" + ".setExecutor(new {1}Executor" + "());\n";

}
