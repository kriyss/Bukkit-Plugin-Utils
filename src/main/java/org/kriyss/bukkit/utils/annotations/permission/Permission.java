package org.kriyss.bukkit.utils.annotations.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Permission {
    String value() default "";
    String message() default "You are not allowed to execute this command";
}
