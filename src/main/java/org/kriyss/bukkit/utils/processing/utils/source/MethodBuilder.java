package org.kriyss.bukkit.utils.processing.utils.source;

import java.util.List;

/**
 * Created by kriyss on 04/09/2014.
 */
public class MethodBuilder {
    private Visibility visibility;
    private String name;
    private Class<?> returnClazz;
    private List<Parameter> parameters;
    private String body;

    private MethodBuilder() {
    }

    public static MethodBuilder aMethod() {
        return new MethodBuilder();
    }

    public MethodBuilder withVisibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public MethodBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MethodBuilder withReturnClazz(Class<?> returnClazz) {
        this.returnClazz = returnClazz;
        return this;
    }

    public MethodBuilder withParameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public MethodBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public Method build() {
        Method method = new Method();
        method.setVisibility(visibility);
        method.setName(name);
        method.setReturnClazz(returnClazz);
        method.setParameters(parameters);
        method.setBody(body);
        return method;
    }
}
