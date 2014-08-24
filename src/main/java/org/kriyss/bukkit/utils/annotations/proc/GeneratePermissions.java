//package org.kriyss.bukkit.utils.annotations.proc;
//
//import org.kriyss.bukkit.utils.annotations.command.Command;
//import org.kriyss.bukkit.utils.annotations.proc.utils.BukkitUtils;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static org.kriyss.bukkit.utils.annotations.proc.utils.GenerationConstant.PERMISSION;
//
///**
// * Created on 06/08/2014.
// */
//
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
//@SupportedAnnotationTypes("org.kriyss.bukkit.utils.annotations.EnablePermissions")
//public class GeneratePermissions extends AbstractProcessor {
//
//    private Filer filer;
//    private Messager messager;
//    private static boolean allreadyGenerated = false;
//
//    @Override
//    public void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        messager = processingEnv.getMessager();
//        filer = processingEnv.getFiler();
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//
//        Set<? extends Element> annotatedPermissions = roundEnv.getElementsAnnotatedWith(EnablePermissions.class);
//        if (!allreadyGenerated && annotatedPermissions != null) {
//            allreadyGenerated = true;
//            StringBuilder sourceCode = new StringBuilder("package ")
//                    .append("org.nylmod.config;")
//                    .append(";\n\n")
//                    .append("import org.bukkit.permissions.Permission;\n\n")
//                    .append("public class ")
//                    .append("PluginPermissions {\n\n");
//            List<String> droits = new ArrayList<String>();
//            for (Element commands : roundEnv.getElementsAnnotatedWith(Command.class)) {
//                String permission = commands.getAnnotation(Command.class).permission();
//                if (!droits.contains(permission)) droits.add(permission);
//            }
//
//            for (String droit : droits) {
//                String name = droit.toUpperCase().replace(".", "_");
//                sourceCode.append(
//                        PERMISSION.replace("$NAME$", name)
//                                .replace("$VALUE", droit));
//            }
//            sourceCode.append("\n}");
//            BukkitUtils.createNewJavaFile(filer, messager, "Plugin", "org.nylmod.config", sourceCode.toString(), "Permissions");
//            return true;
//        }
//        return false;
//    }
//}
