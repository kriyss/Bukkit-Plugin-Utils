package org.kriyss.bukkit.utils.entity;

import com.google.common.base.Objects;

public class ParamEntity {
    private String name;
    private boolean required;
    private int min;
    private int max;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("required", required)
                .add("min", min)
                .add("max", max)
                .add("type", type)
                .toString();
    }
}
