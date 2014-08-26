package org.kriyss.bukkit.utils.annotations.proc.entity.builder;

import org.kriyss.bukkit.utils.annotations.proc.entity.ParamEntity;

public class ParamEntityBuilder {
    private String name;
    private int min;
    private int max;
    private boolean required;

    private ParamEntityBuilder() {
    }

    public static ParamEntityBuilder anParamEntity() {
        return new ParamEntityBuilder();
    }

    public ParamEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ParamEntityBuilder withMin(int min) {
        this.min = min;
        return this;
    }

    public ParamEntityBuilder withMax(int max) {
        this.max = max;
        return this;
    }

    public ParamEntityBuilder withIsRequired(boolean required) {
        this.required = required;
        return this;
    }

    public ParamEntity build() {
        ParamEntity paramEntity = new ParamEntity();
        paramEntity.setName(name);
        paramEntity.setMin(min);
        paramEntity.setMax(max);
        paramEntity.setRequired(required);
        return paramEntity;
    }
}
