package org.kriyss.bukkit.utils.processing;

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
            for (CommandGroupEntity groupEntity : pluginEntity.getCommandGroups()) {
                for (CommandEntity commandEntity : groupEntity.getCommands()) {
                    String src = GenerateCommand.generate(pluginEntity, groupEntity, commandEntity);
                    BukkitUtils.createNewJavaFile(filer, messager,
                            Character.toUpperCase(commandEntity.getCommandMethodName().charAt(0)) + commandEntity.getCommandMethodName().substring(1),
                            groupEntity.getCompleteClassName().substring(0, groupEntity.getCompleteClassName().lastIndexOf('.'))
                            ,src,
                            Const.SUFFIX_COMMAND_CLASS);
                }

            }

            // Generation of Plugin class with CommandExcecutor
        }
        return true;
    }
}
