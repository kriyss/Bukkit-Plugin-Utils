package org.nylmod.economy.annotations.proc.utils;

import org.nylmod.economy.annotations.Command;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

public class BukkitUtils {

    private BukkitUtils() {}

    public static String generateConfigFile(String pluginName, String pluginVersion, String clazzpackage) {
        return "name: " + pluginName + "\nmain: " + clazzpackage + "\nversion: " + pluginVersion + "\n";
    }

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

    public static String generateCommandYml(Command commd) {
        return "    "+commd.name() + ":\n        description: "+commd.description()+"\n" +
                "        usage: "+ commd.usage()+"\n        permission-message: "+commd.permissionMessage()+"\n";
    }

    public static void createNewJavaFile(Filer filer, Messager messager, String className, String packageName, String sourceCode, String suffix) {
        try {
            JavaFileObject sourceFile = filer.createSourceFile(packageName +"."+ className + suffix);
            Writer writer = sourceFile.openWriter();
            writer.write(sourceCode);
            writer.close();

        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }
    public static void hasJustOneResult(Set<?> elements) {
        if (elements.size() > 1 ){
            throw new IllegalArgumentException("Multiple Annotations found");
        }
    }


    public static String getClassName(Element element){
        return element.getSimpleName().toString();
    }
    public static String getPackageName(Element element){
        return element.toString().substring(0,element.toString().lastIndexOf("."));
    }
}
