package org.kriyss.bukkit.utils.annotations.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value() default "";
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    boolean required() default true;
}
