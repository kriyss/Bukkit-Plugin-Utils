//package org.kriyss.bukkit.utils.annotations.proc;
//
//import org.kriyss.bukkit.utils.annotations.command.Command;
//import org.kriyss.bukkit.utils.annotations.Plugin;
//import org.kriyss.bukkit.utils.annotations.proc.utils.BukkitUtils;
//
//import javax.annotation.processing.*;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import java.util.Set;
//
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
//@SupportedAnnotationTypes("org.kriyss.bukkit.utils.annotations.Plugin")
//public class GenerateYmlFile extends AbstractProcessor {
//
//    private Filer filer;
//    private Messager messager;
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
//        Set<? extends Element> pluginElements = roundEnv.getElementsAnnotatedWith(Plugin.class);
//        for (Element plugin : pluginElements) {
//            String source = generateYMLfileConfig(roundEnv, plugin);
//            BukkitUtils.createNewPluginConfigFile(filer, messager, source);
//        }
//        return true;
//    }
//
//    private String generateYMLfileConfig(RoundEnvironment roundEnv, Element plugin) {
//        StringBuilder sb = new StringBuilder();
//        Plugin pluginAnnot = plugin.getAnnotation(Plugin.class);
//        if (pluginAnnot != null){
//            sb.append(getPluginInfo(plugin, pluginAnnot));
//        }
//        sb.append(getCommandsInfo(roundEnv));
//        return sb.toString();
//    }
//
//    private String getCommandsInfo(RoundEnvironment roundEnv) {
//        StringBuilder sb = new StringBuilder("commands:\n");
//        Set<? extends Element> commands = roundEnv.getElementsAnnotatedWith(Command.class);
//
//        for (Element command : commands) {
//            Command commandAnnot = command.getAnnotation(Command.class);
//            if (commandAnnot == null) continue;
//            sb.append(BukkitUtils.generateCommandYml(commandAnnot));
//        }
//        return sb.toString();
//    }
//
//    private String getPluginInfo(Element plugin, Plugin pluginAnnot) {
//        String pluginName = pluginAnnot.name();
//        String pluginVersion = pluginAnnot.version();
//
//        String clazzpackage = plugin.getEnclosingElement().toString() + "." + plugin.getSimpleName().toString()+"Launcher";
//        return BukkitUtils.generateConfigFile(pluginName, pluginVersion, clazzpackage);
//    }
//}
