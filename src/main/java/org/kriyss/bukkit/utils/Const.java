package org.kriyss.bukkit.utils;

public interface Const {
    static final String DEFAULT_FORBIDEN_MESSAGE = "You are not allowed to execute this command"; // TODO externalize this to property file
    static final String SUFFIX_PLUGIN_CLASS = "Launcher";
    static final String SUFFIX_COMMAND_CLASS = "Executor";

    static final String COMMAND_EXECUTOR_HEADER =
            "package {0};\n\n"
                    + "{1}\n\n"
                    + "public class {2}" + SUFFIX_PLUGIN_CLASS  +" extends {2} '{'\n"
                    + "\t@Override\n"
                    + "\tpublic void onEnable() '{'\n"
                    + "\t\t// register the differents commands\n"
                    + "{3}\n"
                    + "\t\t// register the differents event handler\n"
                    + "{4}\n"
                    + "\t'}'\n'}'";

    static final String IMPORT = "import {0};\n";

    static final String GET_COMMAND = "\t\tgetCommand(\"{0}\")" + ".setExecutor(new {1}());\n";
}