package org.kriyss.bukkit.utils.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlPropertyReader extends AbstractPropertyReader{

    private static final Logger LOGGER = Logger.getLogger("XmlPropertyReader");

    public XmlPropertyReader() {
        super("resources.xml");
    }

    public XmlPropertyReader(String fileName) {
        super(fileName);
    }

    @Override
    public void load(){
        try {
            FileInputStream fileInput = new FileInputStream(new File(FILE_NAME));
            properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Can't load properties file");
        }
    }
}
