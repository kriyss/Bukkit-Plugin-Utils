package org.nylmod.economy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that create the command executor class and add it to
 * the plugin class and the plugin.yml file.<br>
 * <br>
 * It's possible to placed this annotation on class extends {@linkplain org.bukkit.command.CommandExecutor}.<br>
 * <br>
 * {@linkplain #name} field is used to set the command name.<br>
 * {@linkplain #description} field is used to describe command function.<br>
 * {@linkplain #usage} field is used to show user how to use the command.<br>
 * {@linkplain #permission} field allow server to show user how to use the command.<br>
 * {@linkplain #permissionMessage} field will be
 *      display to user if he hadn't right to execute the command.<br>
 * {@linkplain #pattern} is used to create Regexp pattern for control user input.<br>
 * <br>
 * Exemple : <br>
 *
 * TODO write the example
 * <pre>
 *  \@Command(name = "money",
 *          description="display player's money and rubis.",
 *          usage =  "/money>",
 *          permission = "neomod.economy.money",
 *          permissionMessage = "You don\'t have permission"
 *          pattern = "pattern")
 *  public class MoneyCommand extends AbstractCommandExecutor {
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Command {
    /** name of the command. Mandatory.*/
    String name();
    /** description of the command. Mandatory.*/
    String description();
    /** how to use the command. Mandatory*/
    String usage();
    /** permission needed to execute the command. */
    String permission() default "";
    /** this message will be show if user havn't right to execute the command. */
    String permissionMessage() default "You don't have permission";
    /** Regexp pattern to generate automatic control of user input. */
    String pattern() default "";
}
