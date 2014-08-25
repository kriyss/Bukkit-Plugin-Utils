package org.kriyss.bukkit.utils.annotations.proc;

import com.google.common.collect.Lists;
import org.kriyss.bukkit.utils.annotations.Plugin;
import org.kriyss.bukkit.utils.annotations.command.Arg;
import org.kriyss.bukkit.utils.annotations.command.Command;
import org.kriyss.bukkit.utils.annotations.command.CommandGroup;
import org.kriyss.bukkit.utils.annotations.permission.Admin;
import org.kriyss.bukkit.utils.annotations.permission.Console;
import org.kriyss.bukkit.utils.annotations.permission.Permission;
import org.kriyss.bukkit.utils.annotations.proc.entity.ArgEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.ArgEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.CommandEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.CommandGroupEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.PluginEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.utils.PluginYMLUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.List;
import java.util.Set;

import static org.kriyss.bukkit.utils.annotations.proc.utils.BukkitUtils.*;

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
        PluginEntityBuilder pluginBuilder = PluginEntityBuilder.aPluginEntity();
        for (Element element : roundEnv.getElementsAnnotatedWith(Plugin.class)) {
            Plugin plugin = element.getAnnotation(Plugin.class);
            String name = getDefaultOrValue(element, plugin.name());
            pluginBuilder.withName(name)
                   .withVersion(plugin.version())
                   .withCompleteClassName(element.toString());
            pluginName = name;
            pluginBuilder.withCommandGroups(getCommandGroupEntities(roundEnv));
            System.out.println(pluginBuilder.build());
            String sourceCode = PluginYMLUtils.generateConfigFileSource(pluginBuilder.build());
            createNewPluginConfigFile(filer, messager, sourceCode);
        }

        return true;
    }

    private List<CommandGroupEntity> getCommandGroupEntities(RoundEnvironment roundEnv) {
        List<CommandGroupEntity> commandGroupEntities = Lists.newArrayList();
        for (Element commGr : roundEnv.getElementsAnnotatedWith(CommandGroup.class)) {
            commandGroupEntities.add(
                    getCommandGroupEntityBuilder(commGr)
                        .withCommands(getCommandEntities(commGr))
                        .build());
        }
        return commandGroupEntities;
    }

    private CommandGroupEntityBuilder getCommandGroupEntityBuilder(Element commGr) {
        System.out.println("- Class scanned : "+ commGr.getSimpleName());
        CommandGroupEntityBuilder groupEntityBuilder = CommandGroupEntityBuilder.aCommandGroupEntity()
                .withCompleteClassName(commGr.toString())
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withFordAdmin(containsAnnotation(commGr, Admin.class))
                .withForConsole(containsAnnotation(commGr, Console.class));

        Permission permission = getPermissionForElement(commGr);
        boolean permissionIsPresent = permission != null;
        if(permissionIsPresent){
            groupEntityBuilder.withPermission(permission.value())
                    .withPermissionMessage(permission.message());
        }
        return groupEntityBuilder;
    }

    private List<CommandEntity> getCommandEntities(Element commandGroupClass) {
        List<CommandEntity> commandEntities = Lists.newArrayList();
        for (Element method : commandGroupClass.getEnclosedElements()) {
            if (containsAnnotation(method, Command.class)){
                commandEntities.add(
                        getCommandEntityBuilder(method)
                            .withArgEntities(getArgEntities(method))
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

        Permission permission = getPermissionForElement(methodElement);
        boolean permissionIsPresent = permission != null;
        if(permissionIsPresent){
            commandEntityBuilder.withPermission(permission.value())
                                .withPermissionMessage(permission.message());
        }

        return commandEntityBuilder;
    }

    private List<ArgEntity> getArgEntities(Element elementmethod) {
        List<ArgEntity> argEntities = Lists.newArrayList();
        if(elementmethod.getKind() == ElementKind.METHOD) {
            for (VariableElement parameter : ((ExecutableElement) elementmethod).getParameters()) {
                Arg arg = parameter.getAnnotation(Arg.class);
                if (arg != null){
                    argEntities.add(
                            ArgEntityBuilder.anArgEntity()
                                .withName(getDefaultOrValue(parameter, arg.value()))
                                .withMax(arg.max())
                                .withMin(arg.min())
                                .withIsRequired(arg.required())
                                .build());
                }
            }
        }
        return argEntities;
    }

    private Permission getPermissionForElement(Element commGr) {
        Permission permissionForMethod = commGr.getAnnotation(Permission.class);
        if(containsAnnotation(commGr, Permission.class)) {
            return permissionForMethod;
        }
        return null;
    }
}
