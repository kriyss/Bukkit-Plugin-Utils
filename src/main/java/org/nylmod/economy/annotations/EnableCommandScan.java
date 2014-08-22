package org.nylmod.economy.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Activate the dectection and generation of {@link org.nylmod.economy.annotations.Command}.
 * This will create CommandExecutor classes and add them to the LauncherClass.<br>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EnableCommandScan {}
