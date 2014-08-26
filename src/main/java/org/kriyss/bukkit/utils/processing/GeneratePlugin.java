package org.kriyss.bukkit.utils.processing;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.annotations.Plugin;
import org.kriyss.bukkit.utils.annotations.command.Command;
import org.kriyss.bukkit.utils.annotations.command.CommandGroup;
import org.kriyss.bukkit.utils.annotations.command.Param;
import org.kriyss.bukkit.utils.annotations.permission.Admin;
import org.kriyss.bukkit.utils.annotations.permission.Console;
import org.kriyss.bukkit.utils.annotations.permission.Permission;
import org.kriyss.bukkit.utils.entity.builder.*;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.entity.ParamEntity;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;
import org.kriyss.bukkit.utils.processing.utils.PluginYMLUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.List;
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
        for (Element element : roundEnv.getElementsAnnotatedWith(Plugin.class)) {
            Plugin plugin = element.getAnnotation(Plugin.class);

            // Retrieve information for generate plugin
            PluginEntityBuilder pluginBuilder = PluginEntityBuilder.aPluginEntity()
                    .withName(BukkitUtils.getDefaultOrValue(element, plugin.name()))
                    .withVersion(plugin.version())
                    .withCompleteClassName(element.toString())
                    .withCommandGroups(getCommandGroupEntities(roundEnv));

            // Creation of file 'plugin.yml'
            BukkitUtils.createNewPluginConfigFile(filer, messager, PluginYMLUtils.generateConfigFileSource(pluginBuilder.build()));
        }
        return true;
    }

    private List<CommandGroupEntity> getCommandGroupEntities(RoundEnvironment roundEnv) {
        List<CommandGroupEntity> commandGroupEntities = Lists.newArrayList();
        for (Element commGr : roundEnv.getElementsAnnotatedWith(CommandGroup.class)) {
            commandGroupEntities.add(populateCommandGroup(commGr));
        }
        return commandGroupEntities;
    }

    private CommandGroupEntity populateCommandGroup(Element commGr) {
        System.out.println("- Class scanned : "+ commGr.getSimpleName());
        CommandGroupEntityBuilder builder = CommandGroupEntityBuilder.aCommandGroupEntity()
                .withCompleteClassName(commGr.toString())
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withFordAdmin(BukkitUtils.containsAnnotation(commGr, Admin.class))
                .withForConsole(BukkitUtils.containsAnnotation(commGr, Console.class))
                .withCommands(getCommandEntities(commGr));
        return populatePermission(commGr, builder).build();
    }

    private List<CommandEntity> getCommandEntities(Element commandGroupClass) {
        List<CommandEntity> commandEntities = Lists.newArrayList();
        for (Element method : commandGroupClass.getEnclosedElements()) {
            if (BukkitUtils.containsAnnotation(method, Command.class)){
                commandEntities.add(populateCommand(method));
            }
        }
        return commandEntities;
    }

    private List<ParamEntity> getParamEntities(Element elementmethod) {
        List<ParamEntity> paramEntities = Lists.newArrayList();
        if(elementmethod.getKind() == ElementKind.METHOD) {
            for (VariableElement parameter : ((ExecutableElement) elementmethod).getParameters()) {
                Param param = parameter.getAnnotation(Param.class);
                if (param != null){
                    paramEntities.add(populateParam(parameter, param));
                }
            }
        }
        return paramEntities;
    }

    private ParamEntity populateParam(VariableElement parameter, Param param) {
        return ParamEntityBuilder.anParamEntity()
                .withName(BukkitUtils.getDefaultOrValue(parameter, param.value()))
                .withMax(param.max())
                .withMin(param.min())
                .withIsRequired(param.required())
                .build();
    }

    private CommandEntity populateCommand(Element methodElement) {
        System.out.println("-- Command scanned : " + methodElement.getSimpleName());
        Command command = methodElement.getAnnotation(Command.class);

        CommandEntityBuilder commandEntityBuilder = CommandEntityBuilder.aCommandEntity()
                .withCommandValue(BukkitUtils.getDefaultOrValue(methodElement, command.name()))
                .withFordAdmin(BukkitUtils.containsAnnotation(methodElement, Admin.class))
                .withForConsole(BukkitUtils.containsAnnotation(methodElement, Console.class))
                .withDescription(command.description())
                .withParamEntities(getParamEntities(methodElement));

        return populatePermission(methodElement, commandEntityBuilder).build();
    }

    private <T extends HasPermission> T populatePermission(Element element, T hasPermission) {
        Permission permission = element.getAnnotation(Permission.class);
        if(permission != null){
            String permissionValue = StringUtils.isNotBlank(permission.value()) ? permission.value() : BukkitUtils.getElementLower(element);
            hasPermission.withPermission(permissionValue);
            hasPermission.withPermissionMessage(permission.message());
        }
        return hasPermission;
    }

}
