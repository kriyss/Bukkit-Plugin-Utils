package org.kriyss.bukkit.utils.processing;

import com.google.common.collect.Lists;
import org.bukkit.event.EventHandler;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

public class EventHanlderScanner {

    public static final String REGISTER_LISTENER = "\t\tgetServer().getPluginManager().registerEvents(new {0}(), this);\n";

    public static List<String> getEvents(RoundEnvironment roundEnv) {
        final Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(EventHandler.class);
        List<String> events = Lists.newArrayList();
        for (Element element : elementsAnnotatedWith) {
            final String name = BukkitUtils.getCompleteClassName(element.getEnclosingElement());
            if (!events.contains(name)) events.add(name);
        }
        return events;
    }

    public static String generateEventsHandler(List<String> events) {
        StringBuilder sb = new StringBuilder();
        for (String event : events) {
            sb.append(MessageFormat.format(REGISTER_LISTENER, BukkitUtils.getClassFromCompleteName(event)));
        }
        return sb.toString();
    }
}
