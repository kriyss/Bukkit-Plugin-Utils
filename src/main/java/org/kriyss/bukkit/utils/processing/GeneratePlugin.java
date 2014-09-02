package org.kriyss.bukkit.utils.processing;

import org.kriyss.bukkit.utils.annotations.Plugin;
import org.kriyss.bukkit.utils.entity.PluginEntity;
import org.kriyss.bukkit.utils.processing.generator.CommandGenerator;
import org.kriyss.bukkit.utils.processing.generator.PluginGenerator;
import org.kriyss.bukkit.utils.processing.generator.YMLGenerator;
import org.kriyss.bukkit.utils.processing.scanner.ProjectScanner;
import org.kriyss.bukkit.utils.processing.utils.BukkitUtils;
import org.kriyss.bukkit.utils.processing.utils.FileSaver;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("org.kriyss.bukkit.utils.annotations.Plugin")
class GeneratePlugin extends AbstractProcessor{

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
        FileSaver saver = new FileSaver(filer, messager);
       // Generation of Plugin
        Set<? extends Element> pluginElements = roundEnv.getElementsAnnotatedWith(Plugin.class);
        BukkitUtils.hasJustOneResult(pluginElements);

        for (Element element : pluginElements) {
            // get All information
            PluginEntity pluginEntity = ProjectScanner.scanPlugin(roundEnv, element);

            // create YML file
            String ymlSourceCode = YMLGenerator.generate(pluginEntity);
            saver.createNewPluginConfigFile(ymlSourceCode, "plugin.yml");

            final Map<String, String> executorsClasses = new CommandGenerator(filer, messager).generateCommandExecutors(pluginEntity);
            String pluginSrc = PluginGenerator.generate(executorsClasses, element, pluginEntity.getEventHandler());

            saver.createNewJavaFile(
                    BukkitUtils.getClassName(element),
                    pluginSrc,
                    PluginGenerator.SUFFIX_PLUGIN_CLASS );
        }
        return true;
    }






}

