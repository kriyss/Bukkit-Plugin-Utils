package org.kriyss.bukkit.utils.annotations.proc;

import com.google.common.collect.Lists;
import org.kriyss.bukkit.utils.annotations.Plugin;
import org.kriyss.bukkit.utils.annotations.command.Command;
import org.kriyss.bukkit.utils.annotations.command.CommandGroup;
import org.kriyss.bukkit.utils.annotations.command.Param;
import org.kriyss.bukkit.utils.annotations.permission.Admin;
import org.kriyss.bukkit.utils.annotations.permission.Console;
import org.kriyss.bukkit.utils.annotations.permission.Permission;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.ParamEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.List;
import java.util.Set;

import static org.kriyss.bukkit.utils.annotations.proc.utils.BukkitUtils.*;
import static org.kriyss.bukkit.utils.annotations.proc.utils.PluginYMLUtils.generateConfigFileSource;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("org.kriyss.bukkit.utils.annotations.Plugin")
public class GeneratePlugin extends AbstractProcessor{

    private Filer filer;
    private Messager messager;
    private String pluginName;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
       // Generation of Plugin
        for (Element element : roundEnv.getElementsAnnotatedWith(Plugin.class)) {
            Plugin plugin = element.getAnnotation(Plugin.class);

            // Retrieve information for generate plugin
            PluginEntityBuilder pluginBuilder = PluginEntityBuilder.aPluginEntity()
                    .withName(getDefaultOrValue(element, plugin.name()))
                    .withVersion(plugin.version())
                    .withCompleteClassName(element.toString())
                    .withCommandGroups(getCommandGroupEntities(roundEnv));

            // Get Permission Global
            pluginBuilder = populatePermission(element, pluginBuilder);

            // Creation of file 'plugin.yml'
            createNewPluginConfigFile(filer, messager, generateConfigFileSource(pluginBuilder.build()));
        }
        return true;
    }

    private <T extends HasPermission> T populatePermission(Element element, T t) {
        Permission permission = element.getAnnotation(Permission.class);
        if(permission != null){
            System.out.println("---- Permission value : " + permission.value());
            System.out.println("---- Permission messa : " + permission.message());
            t.withPermission(permission.value());
            t.withPermissionMessage(permission.message());
        }
        return t;
    }

    private List<CommandGroupEntity> getCommandGroupEntities(RoundEnvironment roundEnv) {
        List<CommandGroupEntity> commandGroupEntities = Lists.newArrayList();
        for (Element commGr : roundEnv.getElementsAnnotatedWith(CommandGroup.class)) {
            commandGroupEntities.add(getCommandGroupEntityBuilder(commGr).build());
        }
        return commandGroupEntities;
    }

    private CommandGroupEntityBuilder getCommandGroupEntityBuilder(Element commGr) {
        System.out.println("- Class scanned : "+ commGr.getSimpleName());
        CommandGroupEntityBuilder groupEntityBuilder = CommandGroupEntityBuilder.aCommandGroupEntity()
                .withCompleteClassName(commGr.toString())
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withFordAdmin(containsAnnotation(commGr, Admin.class))
                .withForConsole(containsAnnotation(commGr, Console.class))
                .withCommands(getCommandEntities(commGr));
        return populatePermission(commGr, groupEntityBuilder);
    }

    private List<CommandEntity> getCommandEntities(Element commandGroupClass) {
        List<CommandEntity> commandEntities = Lists.newArrayList();
        for (Element method : commandGroupClass.getEnclosedElements()) {
            if (containsAnnotation(method, Command.class)){
                commandEntities.add(
                        getCommandEntityBuilder(method)
                            .withParamEntities(getParamEntities(method))
                            .build());
            }
        }
        return commandEntities;
    }

    private CommandEntityBuilder getCommandEntityBuilder(Element methodElement) {
        System.out.println("-- Command scanned : " + methodElement.getSimpleName());
        Command command = methodElement.getAnnotation(Command.class);

        CommandEntityBuilder commandEntityBuilder = CommandEntityBuilder.aCommandEntity()
                .withCommandValue(getDefaultOrValue(methodElement, command.name()))
                .withFordAdmin(containsAnnotation(methodElement, Admin.class))
                .withForConsole(containsAnnotation(methodElement, Console.class))
                .withDescription(command.description());

        return populatePermission(methodElement, commandEntityBuilder);
    }

    private List<ParamEntity> getParamEntities(Element elementmethod) {
        List<ParamEntity> paramEntities = Lists.newArrayList();
        if(elementmethod.getKind() == ElementKind.METHOD) {
            for (VariableElement parameter : ((ExecutableElement) elementmethod).getParameters()) {
                Param param = parameter.getAnnotation(Param.class);
                if (param != null){
                    paramEntities.add(
                            ParamEntityBuilder.anParamEntity()
                                    .withName(getDefaultOrValue(parameter, param.value()))
                                    .withMax(param.max())
                                    .withMin(param.min())
                                    .withIsRequired(param.required())
                                    .build());
                }
            }
        }
        return paramEntities;
    }

}
