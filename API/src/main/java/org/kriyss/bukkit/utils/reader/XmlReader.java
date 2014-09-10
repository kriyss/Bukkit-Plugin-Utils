package org.kriyss.bukkit.utils.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class XmlReader extends AbstractPropertyReader{

    public void load(){
        try {
            FileInputStream fileInput = new FileInputStream(new File(FILE_NAME));
            properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
