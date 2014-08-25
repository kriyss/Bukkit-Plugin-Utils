package org.kriyss.bukkit.utils.annotations.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Command {
    String name() default "";
    /** description of the command. Mandatory.*/
    String description();
}
