package org.kriyss.bukkit.utils.processing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class XmlReader {
    private static final String FILE_NAME = "properties.xml";
    private static Properties properties;

    public XmlReader() {
        if (null == properties) load();
    }

    void load(){
        try {
            FileInputStream fileInput = new FileInputStream(new File(FILE_NAME));
            properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String get(String key){
        return properties.getProperty(key);
    }
    public String get(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }
}
