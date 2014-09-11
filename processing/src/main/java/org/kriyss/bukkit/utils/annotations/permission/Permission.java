package org.kriyss.bukkit.utils.annotations.permission;

import org.kriyss.bukkit.utils.Const;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Permission {
    String value() default "";
    String message() default Const.DEFAULT_FORBIDEN_MESSAGE;
}
