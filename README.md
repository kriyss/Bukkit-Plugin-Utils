Bukkit-Plugin-Utils
===================

This is a framework for help to create [Bukkit](http://bukkit.org/) plugins. All work is done in compilation time.
The framework create automaticaly the plugin.yml, permissions.yml etc...

He is also inspired by ToHPluginUtils - ZerothAngel's Bukkit plugin library. 

#Plugin example
```java
    
    @Plugin(version ="0.1")
    public class Economy extends JavaPlugin {
        @Override
        public void onEnable() {
            super.onEnable();
            // personal stuff
        }
        ...
    }
```

#Command group example

```java

   @Admin
   @Console
   @Permission("neomod.rubis")
   @CommandGroup("rubis")
   public class RubisCommand {
   
       private static final IMoneyCore moneyCore = MoneyCore.getInstance();
   
       @Permission("neomod.perm.other")
       @Command(description = "give money to another player")
       public boolean give(CommandSender sender, @Arg String player, @Arg(min = 0) int amount){
           ...
           return true;
       }
       @Permission
       @Command(description = "take money to another player")
       public boolean take(CommandSender sender, @Arg String player, @Arg(min = 0) int amount){
           ...
           return true;
       }
   }
```

This exemple will generate : 
*   commands `rubisgive` with permission `neomod.perm.other` for Admin and Console too.
*   plugin.yml with automaticaly usage/name/desciption/permission field.
