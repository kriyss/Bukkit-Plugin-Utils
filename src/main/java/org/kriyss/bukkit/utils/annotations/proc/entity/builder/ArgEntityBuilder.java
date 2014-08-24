package org.kriyss.bukkit.utils.annotations.proc.entity.builder;

import org.kriyss.bukkit.utils.annotations.proc.entity.ArgEntity;

public class ArgEntityBuilder {
    private String name;
    private int min;
    private int max;
    private boolean required;

    private ArgEntityBuilder() {
    }

    public static ArgEntityBuilder anArgEntity() {
        return new ArgEntityBuilder();
    }

    public ArgEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ArgEntityBuilder withMin(int min) {
        this.min = min;
        return this;
    }

    public ArgEntityBuilder withMax(int max) {
        this.max = max;
        return this;
    }

    public ArgEntityBuilder withIsRequired(boolean required) {
        this.required = required;
        return this;
    }

    public ArgEntity build() {
        ArgEntity argEntity = new ArgEntity();
        argEntity.setName(name);
        argEntity.setMin(min);
        argEntity.setMax(max);
        argEntity.setRequired(required);
        return argEntity;
    }
}
