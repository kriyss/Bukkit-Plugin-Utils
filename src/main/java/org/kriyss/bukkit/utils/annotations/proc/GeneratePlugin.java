package org.kriyss.bukkit.utils.annotations.proc;

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
import org.kriyss.bukkit.utils.annotations.proc.entity.PluginEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.ArgEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.CommandEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.CommandGroupEntityBuilder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.*;

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
        Set<? extends Element> annotatedWithPlugin = roundEnv.getElementsAnnotatedWith(Plugin.class);
        if (annotatedWithPlugin.size() > 1) throw new IllegalArgumentException("Multiple annotation Plugin found");
        PluginEntity pluginEntity = new PluginEntity();
        for (Element element : annotatedWithPlugin) {
            Plugin plugin = element.getAnnotation(Plugin.class);
            pluginEntity.setName(plugin.name());
            pluginEntity.setVersion(plugin.version());
            pluginEntity.setCompleteClassName(element.toString());
            pluginName = pluginEntity.getName();
        }

        List<CommandGroupEntity> commandGroupEntities = new ArrayList<CommandGroupEntity>();
        Set<? extends Element> annotatedWithCommandGroup = roundEnv.getElementsAnnotatedWith(CommandGroup.class);
        for (Element commGr : annotatedWithCommandGroup) {
            CommandGroupEntity commandGroupEntity = getCommandGroupEntityBuilder(commGr)
                    .withCommands(getCommandEntities(commGr))
                    .build();
            commandGroupEntities.add(commandGroupEntity);
        }
        pluginEntity.setCommandGroups(commandGroupEntities);
        System.out.println(pluginEntity);
        return true;
    }

    private CommandGroupEntityBuilder getCommandGroupEntityBuilder(Element commGr) {
        System.out.println("- Class scanned : "+ commGr.getSimpleName());
        return CommandGroupEntityBuilder.aCommandGroupEntity()
                .withCompleteClassName(commGr.toString())
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withFordAdmin(commGr.getAnnotation(Admin.class) != null)
                .withForConsole(commGr.getAnnotation(Console.class) != null)
                .withPermissions(getPermissionsForElement(commGr));
    }

    private List<CommandEntity> getCommandEntities(Element commGr) {
        List<CommandEntity> commandEntities = new ArrayList<CommandEntity>();
        for (Element elementmethod : commGr.getEnclosedElements()) {
            if (elementmethod != null && elementmethod.getAnnotation(Command.class) != null){
                CommandEntity commandBuilder = getCommandEntityBuilder(elementmethod)
                    .withArgEntities(getArgEntities(elementmethod))
                    .build();
                commandEntities.add(commandBuilder);
            }
        }
        return commandEntities;
    }

    private CommandEntityBuilder getCommandEntityBuilder(Element elementmethod) {
        System.out.println("-- Command scanned : "+ elementmethod.getSimpleName());
        Command command = elementmethod.getAnnotation(Command.class);
        return CommandEntityBuilder.aCommandEntity()
                .withCommandValue("".equals(command.name()) ? elementmethod.getSimpleName().toString().toLowerCase() : command.name())
                .withFordAdmin(elementmethod.getAnnotation(Admin.class) != null)
                .withForConsole(elementmethod.getAnnotation(Console.class) != null)
                .withDescription(command.description())
                .withPermissions(getPermissionsForElement(elementmethod));
    }

    private List<ArgEntity> getArgEntities(Element elementmethod) {
        List<ArgEntity> argEntities = new ArrayList<ArgEntity>();

        if(elementmethod.getKind() == ElementKind.METHOD) {
            ExecutableElement methodElement = (ExecutableElement) elementmethod;
            List<? extends VariableElement> parameters = methodElement.getParameters();
            for (VariableElement parameter : parameters) {
                Arg arg = parameter.getAnnotation(Arg.class);
                if (arg != null){
                    ArgEntity argEntity = ArgEntityBuilder.anArgEntity()
                            .withName("".equals(arg.value()) ? parameter.getSimpleName().toString().toLowerCase() : arg.value())
                            .withMax(arg.max())
                            .withMin(arg.min())
                            .withIsRequired(arg.required())
                            .build();
                    System.out.println("--- Param scanned : "+ argEntity);

                    argEntities.add(argEntity);
                }
            }
        }
        return argEntities;
    }

    private List<String> getPermissionsForElement(Element commGr) {
        List<String> permissions = new ArrayList<String>();
        Permission permissionForMethod = commGr.getAnnotation(Permission.class);
        //TODO refund this
        if(permissionForMethod != null){
            if(permissionForMethod.value() == null || permissionForMethod.value().length == 0){
                permissions = Arrays.asList(pluginName.toLowerCase() + "." + commGr.getSimpleName().toString().toLowerCase());
            }else{
                permissions = Arrays.asList(permissionForMethod.value());
            }
        }
        return permissions;
    }

    public static String getClassName(Element element){
        return element.getSimpleName().toString();
    }
    public static String getPackageName(Element element){
        return element.toString().substring(0,element.toString().lastIndexOf("."));
    }
}
