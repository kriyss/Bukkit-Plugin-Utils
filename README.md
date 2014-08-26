Bukkit-Plugin-Utils
===================

This is a framework for help to create [Bukkit](http://bukkit.org/) plugins. All work is done in compilation time.
The framework create automaticaly the plugin.yml, permissions.yml etc...

He is also inspired by ToHPluginUtils - ZerothAngel's Bukkit plugin library. 

#Plugin example
```java
    
    @Plugin(version ="0.1") // The plugin name will be 'economy'
    public class Economy extends JavaPlugin {
        @Override
        public void onEnable() {
            super.onEnable();
            // personal stuff
        }
        ...
    }
```

You can also specified the plugin name like that : 

```java
    @Plugin(name = "my_plugin", version ="0.1") // The plugin name will be 'my_plugin'
    public class Economy extends JavaPlugin {
        @Override
        public void onEnable() {
            super.onEnable();
            // personal stuff
        }
        ...
    }
```
#Command example

```java
   @Console
   @CommandGroup
   public class RubisCommand {
   
       @Permission
       @Command(description = "give money to another player")
       public boolean give(CommandSender sender, @Param String player, @Param(min = 0) int amount){
           ...
           return true;
       }
       
       @Admin
       @Command(description = "take money to another player")
       public boolean take(CommandSender sender, @Param String player, @Param(min = 0) int amount){
           ...
           return true;
       }
   }
```
