package org.kriyss.bukkit.utils.processing.utils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;

public class FileSaver {
    private final Filer filer;
    private final Messager messager;

    public FileSaver(Filer filer, Messager messager) {
        this.filer = filer;
        this.messager = messager;
    }

    public void createNewPluginConfigFile(String sourceCode, String fileName) {
        try {
            FileObject sourceFile = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", fileName);
            Writer writer = sourceFile.openWriter();
            writer.write(sourceCode);
            writer.close();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    public void createNewJavaFile(String completeClass, String sourceCode, String suffix) {
        try {
            JavaFileObject sourceFile = filer.createSourceFile(completeClass + suffix);

            Writer writer = sourceFile.openWriter();
            writer.write(sourceCode);
            writer.close();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }
}
