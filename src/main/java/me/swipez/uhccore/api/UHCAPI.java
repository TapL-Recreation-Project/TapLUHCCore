package me.swipez.uhccore.api;

import java.util.ArrayList;
import java.util.List;

public class UHCAPI {
    public static List<UHCPlugin> pluginList = new ArrayList<>();
    public static void registerPlugin(UHCPlugin p) {
        pluginList.add(p);
    }
    public static void registerPlugins(UHCPlugin[] p) {
        for (UHCPlugin plugin : p) {
            pluginList.add(plugin);
        }
    }
    public static void registerPlugins(List<UHCPlugin> p) {
        for (UHCPlugin plugin : p) {
            pluginList.add(plugin);
        }
    }
}
