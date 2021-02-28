## How to make a plugin compatible with UHCCore

1. Make your plugin.yml have our plugin as a .
2. Make a new folder next to `src` called `lib` and drop our UHCCore jar in it.
3. Add our core to the plugin with maven:
```xml
        <dependency>
            <groupId>UHCCore</groupId>
            <artifactId>me.swipez.UHCCore</artifactId>
            <scope>system</scope>
            <version>1.0</version>
            <systemPath>${basedir}\src\lib\UHCCore.jar</systemPath>
        </dependency>
```
4. Make your plugin main class implement UHCPlugin.
5. In onEnable register your plugin using `UHCAPI.registerPlugin(this);`.
6. The start method will be called when your plugin will be enabled by our core. Events should be registered in plugin onEnable and not the start method. 
7. Read the docs below to find out what everything does and what you can access!

## Methods in interface

void start() is called when the plugin is enabled by our core.
void stop() is called when the plugin is disabled by our core.
String getName() should return the name of your plugin (it can just be the name in your plugin.yml).
String getAuthor() should return your desired display name we will use when displaying your plugin.
String getDescription() should return your plugin's description.
ItemStack getDisplayStack() should return the ItemStack you want displayed as your plugin symbol in our menu. We will append `Plugin name: <pluginnamefrompluginyml>` and set the count of your itemstack to 1.
JavaPlugin getPlugin() should return `this`;

## API Methods

int graceTimeLeft() returns the amount of ms until grace time is over. Returns 0 if grace time is over, or -1 if the plugin isn't enabled.
int graceTimeTotal() returns the total amount of grace time that was started with (ms). Returns 0 if there is no grace time, or -1 if the plugin isn't enabled. 
boolean isGrace() returns true if grace time is still active, returns false if grace time is over or if the plugin isn't enabled.
List<UHCPlugin> getPluginList() returns a list of all *active* UHCPlugins.
boolean isEnabled() returns true if your plugin is enabled.
boolean isEnabled(UHCPlugin plugin) returns true if the specified plugin is enabled.
