package org.kriyss.bukkit.utils.reader;

import java.util.Properties;

public abstract class AbstractPropertyReader {
    static String FILE_NAME;

    protected static Properties properties;

    protected AbstractPropertyReader(String filename) {
        FILE_NAME = filename;
        if (null == properties) load();
    }

    public abstract void load();

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public boolean getBool(String key) {
        return Boolean.valueOf(getString(key));
    }

    public boolean getBool(String key, boolean defaultValue) {
        final String result = getString(key);
        return result != null ? Boolean.valueOf(result) : defaultValue;
    }

    public int getInt(String key) {
        return Integer.valueOf(getString(key));
    }

    public int getInt(String key, int defaultValue) {
        final String result = getString(key);
        return result != null ? Integer.valueOf(result) : defaultValue;
    }
}
