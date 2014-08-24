package org.kriyss.bukkit.utils.annotations.proc.entity;

public class ArgEntity {
    private String name;
    private boolean required;
    private int min;
    private int max;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "ArgEntity{" +
                "name='" + name + '\'' +
                ", required=" + required +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
