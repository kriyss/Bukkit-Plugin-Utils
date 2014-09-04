package org.kriyss.bukkit.utils.processing.utils.source;

/**
 * Created on 04/09/2014.
 */
public class Parameter {
    private String name;
    private Class<?> clazz;

    public Parameter(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public Parameter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}