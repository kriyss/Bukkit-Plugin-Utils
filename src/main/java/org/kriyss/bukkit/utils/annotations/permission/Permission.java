package org.kriyss.bukkit.utils.annotations.permission;

import org.kriyss.bukkit.utils.Const;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Permission {
    String value() default "";
    String message() default Const.DEFAULT_FORBIDEN_MESSAGE;
}
