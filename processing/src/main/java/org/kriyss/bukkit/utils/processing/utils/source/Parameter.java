package org.kriyss.bukkit.utils.processing.utils.source;

/**
 * Created on 04/09/2014.
 */
public class Parameter {
    private String name;
    private String clazz;
    private boolean isArray;

    public Parameter(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
        this.isArray = false;
    }
    public Parameter(String name, String clazz, boolean isArray) {
        this.name = name;
        this.clazz = clazz;
        this.isArray = isArray;
    }

    public Parameter(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz.getName();
        this.isArray = false;
    }
    public Parameter(String name, Class<?> clazz, boolean isArray) {
        this.name = name;
        this.clazz = clazz.getName();
        this.isArray = isArray;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    public boolean isArray() {
        return isArray;
    }
}