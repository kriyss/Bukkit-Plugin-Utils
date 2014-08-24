package org.kriyss.bukkit.utils.annotations.proc;

import org.kriyss.bukkit.utils.annotations.command.Command;
import org.kriyss.bukkit.utils.annotations.command.CommandGroup;
import org.kriyss.bukkit.utils.annotations.permission.Admin;
import org.kriyss.bukkit.utils.annotations.permission.Console;
import org.kriyss.bukkit.utils.annotations.permission.Permission;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.CommandGroupEntity;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.CommandEntityBuilder;
import org.kriyss.bukkit.utils.annotations.proc.entity.builder.CommandGroupEntityBuilder;
import org.reflections.ReflectionUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
        //get allCommandGroup
        Set<? extends Element> annotatedWithCommandGroup = roundEnv.getElementsAnnotatedWith(CommandGroup.class);
        for (Element commGr : annotatedWithCommandGroup) {

            Element element = commGr.getEnclosingElement();
            System.out.println("CLASS : "  + element.getSimpleName());
            System.out.println("CLASS : "  + commGr.getSimpleName());

            System.out.println("CLASS : "  + commGr.getEnclosedElements());

            Permission permission = element.getAnnotation(Permission.class);
            CommandGroupEntityBuilder builder = CommandGroupEntityBuilder.aCommandGroupEntity()
                    .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                    .withFordAdmin(element.getAnnotation(Admin.class) != null)
                    .withForConsole(element.getAnnotation(Console.class) != null);

            if(permission != null){
                builder.withPermissions(Arrays.asList(permission.value()));
            }

            List<CommandEntity> commandEntities = new ArrayList<CommandEntity>();
            for (Method method : ReflectionUtils.getMethods(element.getClass())) {
                if (method != null){
                    System.out.println("method aaa : " + method.getName());
                    Command command = method.getDeclaredAnnotation(Command.class);
                    if (command != null) {
                        System.out.println("command aaa : " + command);
                        CommandEntity commandEntity = CommandEntityBuilder.aCommandEntity()
                                .withCommandValue(command.name().equals("") ? method.getName() : command.name())
                                .withPermissions(Arrays.asList(method.getAnnotation(Permission.class).value()))
                                .withFordAdmin(method.getAnnotation(Admin.class) != null)
                                .withForConsole(method.getAnnotation(Console.class) != null)
                                .withDescription(command.description())
                                .build();
                        commandEntities.add(commandEntity);
                    }
                }

            }
            builder.withCommands(commandEntities);

            System.out.println(builder.build());



        }

        //get all command
            //generate regexp
        //create source
        //create plugin.yml
        //create permission.yml
        return true;
    }
}
