package org.kriyss.bukkit.utils.processing.generator;

import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;

import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by kriyss on 02/09/2014.
 */
public class PluginGenerator {

    private static final String SUFFIX_PLUGIN_CLASS = "Launcher";

    private static final String COMMAND_EXECUTOR_HEADER =
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

    private static final String IMPORT = "import {0};\n";

    private static final String GET_COMMAND = "\t\tgetCommand(\"{0}\")" + ".setExecutor(new {1}());\n";
    private static final String REGISTER_LISTENER = "\t\tgetServer().getPluginManager().registerEvents(new {0}(), this);\n";


    public String generate(Map<String, String> commandExecutorsClasses, Element element, List<String> events) {
        String importClasses = generateImports(commandExecutorsClasses.values(), events);
        String commandEx = generategetCommand(commandExecutorsClasses);
        String eventsEx = generateEventsHandler(events);
        return MessageFormat.format(COMMAND_EXECUTOR_HEADER,
                BukkitUtils.getPackageName(element),
                importClasses,
                BukkitUtils.getClassName(element),
                commandEx,
                eventsEx);
    }

    private String generategetCommand(Map<String, String> commandExecutorsClasses) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> executorsClass : commandExecutorsClasses.entrySet()) {
            sb.append(MessageFormat.format(GET_COMMAND, executorsClass.getKey(), BukkitUtils.getClassFromCompleteName(executorsClass.getValue())));
        }
        return sb.toString();
    }
    private String generateImports(Collection<String> commandExecutorsClasses, List<String> events) {
        StringBuilder sb = new StringBuilder();
        for (String executorsClass : commandExecutorsClasses) {
            sb.append(MessageFormat.format(IMPORT, executorsClass));
        }
        for (String event : events) {
            sb.append(MessageFormat.format(IMPORT, event));
        }
        return sb.toString();
    }

    public static String generateEventsHandler(List<String> events) {
        StringBuilder sb = new StringBuilder();
        for (String event : events) {
            sb.append(MessageFormat.format(REGISTER_LISTENER, BukkitUtils.getClassFromCompleteName(event)));
        }
        return sb.toString();
    }
}
