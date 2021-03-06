package org.kriyss.bukkit.utils.entity.builder;


import org.kriyss.bukkit.utils.entity.ParamEntity;

import javax.lang.model.type.TypeMirror;

/**
 * Created on 28/08/2014.
 */
public class ParamEntityBuilder {
    private String name;
    private boolean required;
    private int min;
    private int max;
    private TypeMirror type;

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

    public ParamEntityBuilder withType(TypeMirror type) {
        this.type = type;
        return this;
    }

    public ParamEntity build() {
        ParamEntity paramEntity = new ParamEntity();
        paramEntity.setName(name);
        paramEntity.setRequired(required);
        paramEntity.setMin(min);
        paramEntity.setMax(max);
        paramEntity.setType(type);
        return paramEntity;
    }
}
