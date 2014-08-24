package org.kriyss.bukkit.utils.annotations.proc;

import org.kriyss.bukkit.utils.annotations.command.Arg;
import org.kriyss.bukkit.utils.annotations.command.Command;
import org.kriyss.bukkit.utils.annotations.command.CommandGroup;
import org.kriyss.bukkit.utils.annotations.permission.Admin;
import org.kriyss.bukkit.utils.annotations.permission.Console;
import org.kriyss.bukkit.utils.annotations.permission.Permission;
import org.kriyss.bukkit.utils.annotations.proc.entity.ArgEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
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

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedWithCommandGroup = roundEnv.getElementsAnnotatedWith(CommandGroup.class);
        for (Element commGr : annotatedWithCommandGroup) {
            CommandGroupEntityBuilder builder = getCommandGroupEntityBuilder(commGr)
                    .withCommands(getCommandEntities(commGr));
            System.out.println(builder.build());
        }
        return true;
    }

    private CommandGroupEntityBuilder getCommandGroupEntityBuilder(Element commGr) {
        System.out.println("- Class scanned : "+ commGr.getSimpleName());
        return CommandGroupEntityBuilder.aCommandGroupEntity()
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withFordAdmin(commGr.getAnnotation(Admin.class) != null)
                .withForConsole(commGr.getAnnotation(Console.class) != null)
                .withPermissions(getPermissionsForElement(commGr));
    }

    private List<CommandEntity> getCommandEntities(Element commGr) {
        List<CommandEntity> commandEntities = new ArrayList<CommandEntity>();
        for (Element elementmethod : commGr.getEnclosedElements()) {
            if (elementmethod != null && elementmethod.getAnnotation(Command.class) != null){
                CommandEntityBuilder commandBuilder = getCommandEntityBuilder(elementmethod)
                    .withArgEntities(getArgEntities(elementmethod));
                commandEntities.add(commandBuilder.build());
            }
        }
        return commandEntities;
    }

    private CommandEntityBuilder getCommandEntityBuilder(Element elementmethod) {
        System.out.println("-- Command scanned : "+ elementmethod.getSimpleName());
        Command command = elementmethod.getAnnotation(Command.class);
        return CommandEntityBuilder.aCommandEntity()
                .withCommandValue(command.name().equals("") ? elementmethod.getSimpleName().toString().toLowerCase() : command.name())
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
        Permission permissionForMethod = commGr.getAnnotation(Permission.class);
        if(permissionForMethod != null){
            return Arrays.asList(permissionForMethod.value());
        }
        return new ArrayList<String>();
    }
}
