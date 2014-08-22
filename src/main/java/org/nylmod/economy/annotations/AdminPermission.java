package org.nylmod.economy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 05/08/2014.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AdminPermission {
}
