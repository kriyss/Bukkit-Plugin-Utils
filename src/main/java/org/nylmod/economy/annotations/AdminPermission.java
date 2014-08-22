package org.nylmod.economy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that only allow OP player to use a {@linkplain org.bukkit.command.Command}.<br>
 * This annotation only works with the {@linkplain org.nylmod.economy.annotations.Command}.<br>
 * <br>
 * She is used by {@link org.nylmod.economy.annotations.proc.GeneratePlugin} annotation proc.<br>
 * You need to declare {@link org.nylmod.economy.annotations.EnableCommandScan} on
 * {@linkplain org.bukkit.plugin.java.JavaPlugin} to activate it.
 *
 * <pre>
 *  \@AdminPermission
 *  \@Command(name = "kill",
 *          description="kill a player",
 *          usage =  "/kill player>",
 *          permissionMessage = "You don\'t have permission"
 *          pattern = "pattern")
 *  public class KillCommand extends AbstractCommandExecutor {
 * </pre>
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface AdminPermission {}
