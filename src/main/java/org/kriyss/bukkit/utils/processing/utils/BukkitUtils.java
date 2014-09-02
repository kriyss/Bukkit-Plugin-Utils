package org.kriyss.bukkit.utils.processing.utils;

import org.apache.commons.lang.StringUtils;
import org.kriyss.bukkit.utils.entity.CommandEntity;
import org.kriyss.bukkit.utils.entity.CommandGroupEntity;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Set;

public class BukkitUtils {

    public static final String SEPARATOR = ".";

    private BukkitUtils() {}

    public static void createNewPluginConfigFile(Filer filer, Messager messager, String sourceCode, String fileName) {
        try {
            FileObject sourceFile = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", fileName);
            Writer writer = sourceFile.openWriter();
            writer.write(sourceCode);
            writer.close();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    public static void createNewJavaFile(Filer filer, Messager messager, String completeClass, String sourceCode, String suffix) {
        try {
            String name = completeClass + suffix;

            JavaFileObject sourceFile = filer.createSourceFile(name);

            Writer writer = sourceFile.openWriter();
            writer.write(sourceCode);
            writer.close();

            messager.printMessage(Diagnostic.Kind.NOTE, "generation of "+ name + " done!!");

        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }
    public static void hasJustOneResult(Set<?> elements) {
        if (elements.size() > 1 ){
            throw new IllegalArgumentException("Multiple Annotations found");
        }
    }
    public static <A extends Annotation> boolean containsAnnotation(Element element, Class<A> annotation){
        return element != null && element.getAnnotation(annotation) != null;
    }
    public static String getValueOrDefault(Element element, String value) {
        return "".equals(value) ? getElementLower(element) : value;
    }
    public static String getElementLower(Element element) {
        return element.getSimpleName().toString().toLowerCase();
    }
    public static String getClassName(Element element){
        return element.getSimpleName().toString();
    }
    public static String getCompleteClassName(Element element){
        return element.toString();
    }
    public static String getPackageName(Element element){
        return element.toString().substring(0,element.toString().lastIndexOf(SEPARATOR));
    }
    public static String getClassFromCompleteName(String completeClassName) {
        return completeClassName.substring(completeClassName.lastIndexOf('.') + 1, completeClassName.length());
    }
    public static String getCommandExecutorClass(CommandGroupEntity commandGroup, CommandEntity command) {
        return StringUtils.capitalize(commandGroup.getRootCommand()) +  StringUtils.capitalize(command.getCommandMethodName());
    }
    public static String getPackageFromCompleteClass(CommandGroupEntity groupEntity) {
        return groupEntity.getCompleteClassName().substring(0, groupEntity.getCompleteClassName().lastIndexOf('.'));
    }
    public static String getCompleteCommandExecutorClass(CommandGroupEntity group, CommandEntity command){
        return getPackageFromCompleteClass(group)+SEPARATOR+getCommandExecutorClass(group, command);
    }
    public static boolean isInteger(TypeMirror typeMirror) {
        return typeMirror.toString().equals("java.lang.Integer") || typeMirror.toString().equals("int");
    }

    public static boolean isString(TypeMirror typeMirror) {
        return typeMirror.toString().equals("java.lang.String");
    }
}
