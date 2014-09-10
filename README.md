Bukkit-Plugin-Utils
===================

This is a framework for help to create [Bukkit](http://bukkit.org/) plugins. All work is done in compilation time.
The framework create automatically the plugin.yml, permissions.yml etc...

He is also inspired by ToHPluginUtils - ZerothAngel's Bukkit plugin library. 

## TODO :
* archetype maven
* better permission management
* unit test
* Auto config annotation
* documentation

## How to use it
### Get the source
```
    cd [PATH-TO-YOUR-FOLDER]
    git clone https://github.com/kriyss/Bukkit-Plugin-Utils.git
    cd Bukkit-Plugin-Utils
    mvn clean install
```
### Create your plugin project
#### From scratch
Create a new maven project and inherit from this parent : 
```xml
    <parent>
        <groupId>org.kriyss.bukkit</groupId>
        <artifactId>plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
```
#### From Archetype
// TODO
After this, create the Plugin class as below : 

## USE IT 
The easiest way to generate a plugin is just putting the annotation `@Plugin` with the version of this one. I would try to automate the version.<br>
The class where you put `@Plugin` must extends `JavaPlugin`. You could put what do you want on it, the Framework will take it into account.
```java
    
    @Plugin(version ="0.1") // The plugin name will be 'economy'
    public class Economy extends JavaPlugin {
        ...
    }
```
You can also specified the plugin name like that : 

```java
    @Plugin(name = "my_plugin", version ="0.1") // The plugin name will be 'my_plugin'
    public class Economy extends JavaPlugin {
        ...
    }
```
### Command Group :

A `@CommandGroup` is a class regrouping some commands, so you can manage permission of many commands with annotations :
* `@Admin` : OP's player can use this group of command.
* `@Console` : console can use this group of command.
* `@Permission` : normal Player can use this command if they have good permission.

```java
   @Admin
   @Console
   @Permission
   @CommandGroup
   public class RubisCommand {
      ... //some commands, you will see it later.
   }
```
### Command :
A `@Command` is a method that you can manage permission of many commands with annotations like `@CommandGroup`:
* `@Admin` : OP's player can use this group of command.
* `@Console` : console can use this group of command.
* `@Permission` : normal Player can use this command if they have good permission.

Thanks to the `@Command`, the application will generate plugin.xml, with usage, name, description etc...
All test like player is present, no null/empty passed, min, max etc, will be managed by the Framework.
Like the Framework makes all at the compile time, you will can see generated code to be sure that the work is good.
The `CommandSender` will be automatically injected.
```java
   @Command(description = "give money to another player")
   public boolean give(CommandSender sender, @Param String player, @Param int amount){
       // your stuff
       return true;
   }
```
For example this command will generate this : 
```yml
    give:
        description: give money to another player
        usage: /give [player] [amount] 
```

More example and explanation on the [Wiki](https://github.com/kriyss/Bukkit-Plugin-Utils/wiki)
