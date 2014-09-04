package org.kriyss.bukkit.utils.processing.utils.source;

/**
 * Created on 04/09/2014.
 */
public class Parameter {
    private String name;
    private Class<?> clazz;
    private boolean isArray;

    public Parameter(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
        this.isArray = false;
    }
    public Parameter(String name, Class<?> clazz, boolean isArray) {
        this.name = name;
        this.clazz = clazz;
        this.isArray = isArray;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public boolean isArray() {
        return isArray;
    }
}