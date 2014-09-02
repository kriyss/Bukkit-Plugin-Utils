package org.kriyss.bukkit.utils.processing.generator;

import com.google.common.collect.Maps;
import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;
import org.kriyss.bukkit.utils.processing.GenerateCommand;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import java.util.Map;

/**
 * Created by kriyss on 02/09/2014.
 */
public class CommandGenerator {
    private Filer filer;
    private Messager messager;

    public CommandGenerator(Filer filer, Messager messager) {
        this.filer = filer;
        this.messager = messager;
    }

    public Map<String, String> generateCommandExecutors(PluginEntity pluginEntity) {
        Map<String, String> completeCommandExecutorClass = Maps.newHashMap();
        for (CommandGroupEntity groupEntity : pluginEntity.getCommandGroups()) {
            for (CommandEntity commandEntity : groupEntity.getCommands()) {
                completeCommandExecutorClass.put(
                        groupEntity.getRootCommand() + commandEntity.getCommandValue(),
                        generateCommandExecutor(groupEntity, commandEntity) + Const.SUFFIX_COMMAND_CLASS);
            }
        }
        return completeCommandExecutorClass;
    }

    private String generateCommandExecutor(CommandGroupEntity groupEntity, CommandEntity commandEntity) {
        String src = GenerateCommand.generate(groupEntity, commandEntity);
        final String commandExecutorcompleteClass = BukkitUtils.getCompleteCommandExecutorClass(groupEntity, commandEntity);
        BukkitUtils.createNewJavaFile(filer, messager, commandExecutorcompleteClass, src, Const.SUFFIX_COMMAND_CLASS);
        return commandExecutorcompleteClass;
    }
}
