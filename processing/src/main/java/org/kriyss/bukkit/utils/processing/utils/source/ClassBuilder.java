package org.kriyss.bukkit.utils.processing.utils.source;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ClassBuilder {
    private Visibility visibility;
    private String packageName;
    private String className;
    private String extendOf;
    private List<Class<?>> imports = Lists.newArrayList();
    private List<Class<?>> implementsOf = Lists.newArrayList();
    private List<Constant> constants = Lists.newArrayList();
    private List<Method> methods = Lists.newArrayList();

    private ClassBuilder() {
    }

    public static ClassBuilder aClassBuilder() {
        return new ClassBuilder();
    }

    public ClassBuilder withVisibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public ClassBuilder withPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public ClassBuilder withImports(List<Class<?>> imports) {
        this.imports = imports;
        return this;
    }

    public ClassBuilder withClassName(String className) {
        this.className = className;
        return this;
    }

    public ClassBuilder withExtendOf(String extendOf) {
        this.extendOf = extendOf;
        return this;
    }

    public ClassBuilder withImplementsOf(List<Class<?>> implementsOf) {
        this.implementsOf = implementsOf;
        return this;
    }

    public ClassBuilder withConstants(List<Constant> constants) {
        this.constants = constants;
        return this;
    }

    public ClassBuilder withMethods(List<Method> methods) {
        this.methods = methods;
        return this;
    }
    public String build(){
        StringBuilder sb = new StringBuilder();
        // PACKAGE
        sb.append("package ").append(packageName).append(";\n\n");
        // IMPORTS
        for (Class<?> anImport : imports) {
            if(null != anImport) {
                sb.append("import ").append(anImport.getName()).append(";\n");
            }
        }
        // CLASS DESCRIPTION
        sb.append("\n").append(visibility.get()).append(" class ").append(className);
        if (StringUtils.isNotBlank(extendOf)){
            sb.append(" extends ").append(extendOf);
        }
        if (!implementsOf.isEmpty()){
            sb.append(" implements ");
            for (Class<?> impl : implementsOf) {
                if(null != impl){
                    sb.append(impl.getSimpleName()).append(",");
                }
            }
            sb.replace(sb.lastIndexOf(","), sb.length(), "{\n\n");
        }
        for (Constant constant : constants) {
            sb.append("\t")
                    .append(constant.getVisibility().get())
                    .append(" ")
                    .append(constant.getClazz())
                    .append(" ")
                    .append(constant.getName())
                    .append(" = ")
                    .append(constant.getValue())
                    .append(";\n");
        }
        for (Method method : methods) {
            sb.append("\t").append(method.getVisibility().get()).append(" ").append(method.getReturnClazz().getName()).append(" ").append(method.getName()).append("(");
            List<Parameter> parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                sb.append(parameter.getClazz());
                if (parameter.isArray())
                    sb.append("[]");
                sb.append(" ").append(parameter.getName()).append(",");
            }
            removeLastSemicolon(sb).append(")");
            if (!method.getExceptionsClazz().isEmpty()) {
                sb.append(" throws ");
                for (String clazz : method.getExceptionsClazz()) {
                    sb.append(clazz).append(",");
                }
                removeLastSemicolon(sb);
            }
            sb.append("{\n").append(method.getBody()).append("\t}\n\n");
        }
        return sb.append("}\n").toString();
    }

    private StringBuilder removeLastSemicolon(StringBuilder sb) {
        if (sb.lastIndexOf(",") ==  sb.length()-1)
            sb.replace(sb.lastIndexOf(","), sb.length(), "");
        return sb;
    }

}
