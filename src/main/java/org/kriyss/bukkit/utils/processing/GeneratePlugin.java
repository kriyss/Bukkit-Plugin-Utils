package org.kriyss.bukkit.utils.processing;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.kriyss.bukkit.utils.Const;
import org.kriyss.bukkit.utils.annotations.Plugin;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.PluginEntity;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;
import org.kriyss.bukkit.utils.processing.utils.PluginYMLUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("org.kriyss.bukkit.utils.annotations.Plugin")
public class GeneratePlugin extends AbstractProcessor{

    private Filer filer;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
       // Generation of Plugin
        Set<? extends Element> pluginElements = roundEnv.getElementsAnnotatedWith(Plugin.class);
        BukkitUtils.hasJustOneResult(pluginElements);

        for (Element element : pluginElements) {
            // Retrieve information for generate plugin
            PluginEntity pluginEntity = ProjectScanner.getPluginEntityBuilder(roundEnv, element);
            // Generation of file 'plugin.yml'
            BukkitUtils.createNewPluginConfigFile(filer, messager, PluginYMLUtils.generateConfigFileSource(pluginEntity));
            // Generation of CommandExecutor
            Map<String, String> commandExecutorsClasses = generateCommandExecutors(pluginEntity);
            // Generation of Plugin class with CommandExcecutor
            String pluginSrc = generatePluginSource(commandExecutorsClasses, element);
            BukkitUtils.createNewJavaFile(filer, messager, BukkitUtils.getClassName(element), pluginSrc, Const.SUFFIX_PLUGIN_CLASS );
        }
        return true;
    }

    private String generatePluginSource(Map<String, String> commandExecutorsClasses, Element element) {
        String imortClasses = generateCommandExecutorImport(commandExecutorsClasses.values());
        String commandEx = generategetCommand(commandExecutorsClasses);
        return MessageFormat.format(Const.COMMAND_EXECUTOR_HEADER, BukkitUtils.getPackageName(element), imortClasses, BukkitUtils.getClassName(element),commandEx );
    }

    private String generategetCommand(Map<String, String> commandExecutorsClasses) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> executorsClass : commandExecutorsClasses.entrySet()) {
            sb.append(MessageFormat.format(Const.GET_COMMAND, executorsClass.getKey(), BukkitUtils.getClassFromCompleteName(executorsClass.getValue())));
        }
        return sb.toString();
    }

    private String generateCommandExecutorImport(Collection<String> commandExecutorsClasses) {
        StringBuilder sb = new StringBuilder();
        for (String executorsClass : commandExecutorsClasses) {
            sb.append(MessageFormat.format(Const.IMPORT_EXECUTOR, executorsClass));
        }
        return sb.toString();

    }

    private Map<String, String> generateCommandExecutors(PluginEntity pluginEntity) {
        Map<String, String> completeCommandExecutorClass = Maps.newHashMap();
        for (CommandGroupEntity groupEntity : pluginEntity.getCommandGroups()) {
            for (CommandEntity commandEntity : groupEntity.getCommands()) {
                completeCommandExecutorClass.put(
                        groupEntity.getRootCommand() + commandEntity.getCommandValue(), generateCommandExecutor(groupEntity, commandEntity) + Const.SUFFIX_COMMAND_CLASS);
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
