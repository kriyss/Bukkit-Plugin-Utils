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

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
            break;
        }

        List<CommandGroupEntity> commandGroupEntities = Lists.newArrayList();
        for (Element commGr : roundEnv.getElementsAnnotatedWith(CommandGroup.class)) {
            commandGroupEntities.add(
                    getCommandGroupEntityBuilder(commGr)
                        .withCommands(getCommandEntities(commGr))
                        .build());
        }
        pluginBuilder.withCommandGroups(commandGroupEntities);
        System.out.println(pluginBuilder.build());
        return true;
    }

    private String getDefaultOrValue(Element element, String value) {
        return "".equals(value) ? getElementLower(element) : value;
    }

    private String getElementLower(Element element) {
        return element.getSimpleName().toString().toLowerCase();
    }

    private CommandGroupEntityBuilder getCommandGroupEntityBuilder(Element commGr) {
        System.out.println("- Class scanned : "+ commGr.getSimpleName());
        return CommandGroupEntityBuilder.aCommandGroupEntity()
                .withCompleteClassName(commGr.toString())
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withFordAdmin(containsAnnotation(commGr, Admin.class))
                .withForConsole(containsAnnotation(commGr, Console.class))
                .withPermissions(getPermissionsForElement(commGr));
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

    private CommandEntityBuilder getCommandEntityBuilder(Element commandEntity) {
        System.out.println("-- Command scanned : " + commandEntity.getSimpleName());
        Command command = commandEntity.getAnnotation(Command.class);
        return CommandEntityBuilder.aCommandEntity()
                .withCommandValue( getDefaultOrValue(commandEntity, command.name()))
                .withFordAdmin( containsAnnotation(commandEntity, Admin.class))
                .withForConsole( containsAnnotation(commandEntity, Console.class))
                .withDescription( command.description())
                .withPermissions( getPermissionsForElement(commandEntity));
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

    private List<String> getPermissionsForElement(Element commGr) {
        List<String> permissions = Lists.newArrayList();
        Permission permissionForMethod = commGr.getAnnotation(Permission.class);
        //TODO refund this
        if(containsAnnotation(commGr, Permission.class)){
            if(permissionForMethod.value() != null && permissionForMethod.value().length < 0){
                permissions = Arrays.asList(pluginName.toLowerCase() + "." + getElementLower(commGr));
            }else{
                permissions = Arrays.asList(permissionForMethod.value());
            }
        }
        return permissions;
    }
    private <A extends Annotation> boolean containsAnnotation(Element element, Class<A> annotation){
        return element != null && element.getAnnotation(annotation) != null;
    }

    public static String getClassName(Element element){
        return element.getSimpleName().toString();
    }
    public static String getPackageName(Element element){
        return element.toString().substring(0,element.toString().lastIndexOf("."));
    }

}
