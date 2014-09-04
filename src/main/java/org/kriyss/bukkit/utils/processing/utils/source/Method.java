package org.kriyss.bukkit.utils.processing.utils.source;

import java.util.List;

/**
 * Created on 04/09/2014.
 */
public class Method {
    private Visibility visibility;
    private String name;
    private Class<?> returnClazz;
    private List<Parameter> parameters;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getReturnClazz() {
        return returnClazz;
    }

    public void setReturnClazz(Class<?> returnClazz) {
        this.returnClazz = returnClazz;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}