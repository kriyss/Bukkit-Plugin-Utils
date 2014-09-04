package org.kriyss.bukkit.utils.processing.utils.source;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created on 04/09/2014.
 */
public class ClassBuilder {
    public Visibility visibility;
    public String packageName;
    public List<String> imports = Lists.newArrayList();;
    public String className;
    public String extendOf;
    public List<String> implementsOf = Lists.newArrayList();
    public List<Constant> constants = Lists.newArrayList();;
    public List<Method> methods = Lists.newArrayList();;

    public String build(){
        StringBuilder sb = new StringBuilder();
        // PACKAGE
        sb.append("package ").append(packageName);
        // IMPORTS
        for (String anImport : imports) {
            sb.append("import ").append(anImport).append(";");
        }
        // CLASS DESCRIPTION
        sb.append(visibility.get()).append("class ").append(className);
        if (StringUtils.isNotBlank(extendOf)){
            sb.append(" extends ").append(extendOf);
        }
        if (!implementsOf.isEmpty()){
            sb.append(" implements ");
            im
        }


        return sb.toString();
    }

}
