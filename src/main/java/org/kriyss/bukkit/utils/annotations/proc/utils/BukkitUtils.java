package org.kriyss.bukkit.utils.annotations.proc.utils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Set;

public class BukkitUtils {

    private BukkitUtils() {}

    public static void createNewPluginConfigFile(Filer filer, Messager messager, String sourceCode) {
        try {
            messager.printMessage(Diagnostic.Kind.NOTE, "Generate Plugin.yml file");

            FileObject sourceFile = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "plugin.yml");
            Writer writer = sourceFile.openWriter();
            writer.write(sourceCode);
            writer.close();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    public static void createNewJavaFile(Filer filer, Messager messager, String className, String packageName, String sourceCode, String suffix) {
        try {
            String name = packageName + "." + className + suffix;

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

    public static String getDefaultOrValue(Element element, String value) {
        return "".equals(value) ? getElementLower(element) : value;
    }
    public static String getElementLower(Element element) {
        return element.getSimpleName().toString().toLowerCase();
    }

    public static String getClassName(Element element){
        return element.getSimpleName().toString();
    }
    public static String getPackageName(Element element){
        return element.toString().substring(0,element.toString().lastIndexOf("."));
    }
}
