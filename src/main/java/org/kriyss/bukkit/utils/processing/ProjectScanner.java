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
import org.kriyss.bukkit.utils.entity.*;
import org.kriyss.bukkit.utils.entity.builder.*;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

import static org.kriyss.bukkit.utils.processing.utils.BukkitUtils.*;

public class ProjectScanner {

    public static PluginEntity getPluginEntityBuilder(RoundEnvironment roundEnv, Element element) {
        Plugin plugin = element.getAnnotation(Plugin.class);
        return PluginEntityBuilder.aPluginEntity()
                .withName(getValueOrDefault(element, plugin.name()))
                .withVersion(plugin.version())
                .withCompleteClassName(element.toString())
                .withCommandGroups(getCommandGroupEntities(roundEnv))
                .build();
    }

    private static List<CommandGroupEntity> getCommandGroupEntities(RoundEnvironment roundEnv) {
        List<CommandGroupEntity> commandGroupEntities = Lists.newArrayList();
        for (Element commGr : roundEnv.getElementsAnnotatedWith(CommandGroup.class)) {
            commandGroupEntities.add(populateCommandGroup(commGr));
        }
        return commandGroupEntities;
    }

    private static CommandGroupEntity populateCommandGroup(Element commGr) {
        return CommandGroupEntityBuilder.aCommandGroupEntity()
                .withCompleteClassName(commGr.toString())
                .withRootCommand(commGr.getAnnotation(CommandGroup.class).value())
                .withCommands(getCommandEntities(commGr))
                .withPermission(populatePermission(commGr))
                .build();
    }

    private static List<CommandEntity> getCommandEntities(Element commandGroupClass) {
        List<CommandEntity> commandEntities = Lists.newArrayList();
        for (Element method : commandGroupClass.getEnclosedElements()) {
            if (containsAnnotation(method, Command.class)){
                commandEntities.add(populateCommand(method));
            }
        }
        return commandEntities;
    }

    private static List<ParamEntity> getParamEntities(Element elementmethod) {
        List<ParamEntity> paramEntities = Lists.newArrayList();
        if(elementmethod.getKind() == ElementKind.METHOD) {
            for (VariableElement parameter : ((ExecutableElement) elementmethod).getParameters()) {
                Param param = parameter.getAnnotation(Param.class);
                if (param != null){
                    paramEntities.add(populateParam(parameter, param, parameter.asType().toString()));
                }
            }
        }
        return paramEntities;
    }

    private static ParamEntity populateParam(VariableElement parameter, Param param, String type) {
        return ParamEntityBuilder.aParamEntity()
                .withName(getValueOrDefault(parameter, param.value()))
                .withMax(param.max())
                .withMin(param.min())
                .withType(type)
                .withRequired(param.required())
                .build();
    }

    private static CommandEntity populateCommand(Element methodElement) {
        Command command = methodElement.getAnnotation(Command.class);
        return CommandEntityBuilder.aCommandEntity()
                .withCommandMethodName(methodElement.getSimpleName().toString())
                .withCommandValue(getValueOrDefault(methodElement, command.name()))
                .withDescription(command.description())
                .withPermission(populatePermission(methodElement))
                .withParamEntities(getParamEntities(methodElement))
                .build();
    }

    private static PermissionEntity populatePermission(Element element) {
        Permission permission = element.getAnnotation(Permission.class);
        PermissionEntity permissionEntity = new PermissionEntity();
        if(permission != null){
            String permissionValue = StringUtils.isNotBlank(permission.value()) ? permission.value() : getElementLower(element);
            permissionEntity = PermissionEntityBuilder.aPermissionEntity()
                    .withForAdmin(containsAnnotation(element, Admin.class))
                    .withForConsole(containsAnnotation(element, Console.class))
                    .withMessage(permission.message())
                    .withValue(permissionValue)
                    .build();
        }
        return permissionEntity;
    }
}
