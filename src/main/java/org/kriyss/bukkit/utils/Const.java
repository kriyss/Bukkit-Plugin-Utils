package org.kriyss.bukkit.utils;

public interface Const {
    static final String DEFAULT_FORBIDEN_MESSAGE = "You are not allowed to execute this command"; // TODO externalize this to property file
    static final String SUFFIX_PLUGIN_CLASS = "Launcher";
    static final String SUFFIX_COMMAND_CLASS = "Executor";

        static final String COMMAND_EXECUTOR_PATTERN =
            "package {0};\n\n"
                    + "import org.bukkit.command.Command;\n"
                    + "import org.bukkit.ChatColor;\n"
                    + "import org.bukkit.entity.Player;\n"
                    + "import org.bukkit.command.CommandSender;\n\n"
                    + "public class {1}"
                    + SUFFIX_COMMAND_CLASS
                    +"  extends {1} '{'\n"
                    + "\t@Override\n"
                    + "\tpublic boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) '{'\n"
                    + "{2}"
                    + "\t\treturn super.onCommand(commandSender, command, s, strings);\n"
                    + "\t'}'\n"
                    + "'}'";

    static final String COMMAND_EXECUTOR_HEADER =
            "package {0};\n\n"
                    + "{1}\n\n"
                    + "public class {2}" + SUFFIX_PLUGIN_CLASS  +" extends {2} '{'\n"
                    + "\t@Override\n"
                    + "\tpublic void onEnable() '{'\n"
                    + "{3}\n"
                    + "\t'}'\n'}'";

    static final String IMPORT_EXECUTOR = "import {0};\n";

    static final String GET_COMMAND = "\t\tgetCommand(\"{0}\")" + ".setExecutor(new {1}());\n";
}
