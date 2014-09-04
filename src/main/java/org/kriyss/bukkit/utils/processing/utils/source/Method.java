package org.kriyss.bukkit.utils.processing.utils.source;

import com.google.common.collect.Lists;

import java.util.List;

public class Method {
    private Visibility visibility;
    private String name;
    private Class<?> returnClazz;
    private List<Parameter> parameters;
    private String body;
    private List<String> exceptionsClazz;

    public String getName() {
        return name;
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

    public List<String> getExceptionsClazz() {
        return exceptionsClazz;
    }

    public void setExceptionsClazz(List<String> exceptionClazz) {
        this.exceptionsClazz = exceptionClazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class MethodBuilder {
        private Visibility visibility;
        private String name;
        private Class<?> returnClazz;
        private List<Parameter> parameters = Lists.newArrayList();
        private String body;
        private List<String> exceptionsClazz = Lists.newArrayList();

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

        public MethodBuilder withExceptionsClazz(List<String> exceptionsClazz) {
            this.exceptionsClazz = exceptionsClazz;
            return this;
        }

        public Method build() {
            Method method = new Method();
            method.setVisibility(visibility);
            method.setName(name);
            method.setReturnClazz(returnClazz);
            method.setParameters(parameters);
            method.setBody(body);
            method.setExceptionsClazz(exceptionsClazz);
            return method;
        }
    }
}