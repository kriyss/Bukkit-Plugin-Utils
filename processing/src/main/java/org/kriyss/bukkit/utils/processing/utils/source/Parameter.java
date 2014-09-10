package org.kriyss.bukkit.utils.processing.utils.source;

public class Parameter {
    private final String name;
    private final String clazz;
    private final boolean isArray;

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