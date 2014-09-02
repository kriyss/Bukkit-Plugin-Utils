package org.kriyss.bukkit.utils.processing;

import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;

import java.text.MessageFormat;
import java.util.List;

public class EventHanlderScanner {

    public static final String REGISTER_LISTENER = "\t\tgetServer().getPluginManager().registerEvents(new {0}(), this);\n";

    public static String generateEventsHandler(List<String> events) {
        StringBuilder sb = new StringBuilder();
        for (String event : events) {
            sb.append(MessageFormat.format(REGISTER_LISTENER, BukkitUtils.getClassFromCompleteName(event)));
        }
        return sb.toString();
    }
}