package org.nylmod.economy.annotations.proc;

import org.nylmod.economy.annotations.AdminPermission;
import org.nylmod.economy.annotations.Command;
import org.nylmod.economy.annotations.EnableCommandScan;
import org.nylmod.economy.annotations.proc.utils.BukkitUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.text.MessageFormat;
import java.util.Set;

import static org.nylmod.economy.annotations.proc.utils.BukkitUtils.*;
import static org.nylmod.economy.annotations.proc.utils.GenerationConstant.*;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("org.nylmod.economy.annotations.EnableCommandScan")
public class GeneratePlugin extends AbstractProcessor{


    private Filer filer;
    private Messager messager;

    private Set<? extends Element> commandsElements;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        commandsElements = roundEnv.getElementsAnnotatedWith(Command.class);
        Set<? extends Element> commandScanElements = roundEnv.getElementsAnnotatedWith(EnableCommandScan.class);

        for (Element command : commandsElements) {
            generateExecutorForCommand(command);
        }

        // Ensure we hav only one EnableCommandScan on the source
        BukkitUtils.hasJustOneResult(commandScanElements);
        for (Element command : commandScanElements) {
            generatePluginLauncherClass(command);
        }
        return true;
    }

    private void generateExecutorForCommand(Element command) {
        String className = getClassName(command);
        String packageName = getPackageName(command);

        String source = generateExecutorForCommandSource(command, className, packageName);
        createNewJavaFile(filer, messager, className, packageName, source, COMMAND_SUFFIX);
    }

    private String generateExecutorForCommandSource(Element command, String className, String packageName) {
        String sourcePermission;
        Command annotation = command.getAnnotation(Command.class);
        boolean isOnlyAdminCommand = command.getAnnotation(AdminPermission.class) != null;

        if (isOnlyAdminCommand){
            sourcePermission =  MessageFormat.format(COMMAND_EXECUTOR_PERMISSION_ADMIN, annotation.permissionMessage());
        }else{
            sourcePermission = MessageFormat.format(COMMAND_EXECUTOR_PERMISSION, annotation.permissionMessage(), annotation.permission() );
        }
        return MessageFormat.format(COMMAND_EXECUTOR_PATTERN, packageName, className, sourcePermission);
    }

    private void generatePluginLauncherClass(Element command) {
        String className = getClassName(command);
        String packageName = getPackageName(command);
        String source = generatePluginSourceCode(className, packageName);
        createNewJavaFile(filer, messager, className, packageName, source, PLUGIN_SUFFIX);
    }

    private String generatePluginSourceCode(String className, String packageName) {
        String header = MessageFormat.format(COMMAND_EXECUTOR_HEADER, packageName, generateImportCommand(), className);
        StringBuilder sourceCode = new StringBuilder(header);

        for (Element command : commandsElements) {
            Command commandAnot = command.getAnnotation(Command.class);
            String commandSource =  MessageFormat.format(GET_COMMAND, commandAnot.name(),command.getSimpleName());
            sourceCode.append(commandSource);
        }
        return sourceCode.append("\t}\n}").toString();
    }

    private String generateImportCommand() {
        StringBuilder sourceCode = new StringBuilder();
        for (Element command : commandsElements) {
            sourceCode.append(MessageFormat.format(IMPORT_EXECUTOR, command.getEnclosingElement(), command.getSimpleName()));
        }
        return sourceCode.toString();
    }
}
