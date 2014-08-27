package org.kriyss.bukkit.utils.entity.builder;

import org.kriyss.bukkit.utils.entity.ParamEntity;

/**
 * Created on 27/08/2014.
 */
public class ParamEntityBuilder {
    private String name;
    private boolean required;
    private int min;
    private int max;

    private ParamEntityBuilder() {
    }

    public static ParamEntityBuilder aParamEntity() {
        return new ParamEntityBuilder();
    }

    public ParamEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ParamEntityBuilder withRequired(boolean required) {
        this.required = required;
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

    public ParamEntity build() {
        ParamEntity paramEntity = new ParamEntity();
        paramEntity.setName(name);
        paramEntity.setRequired(required);
        paramEntity.setMin(min);
        paramEntity.setMax(max);
        return paramEntity;
    }
}
