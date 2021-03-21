package me.swipez.uhccore.api;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UHCAPI {
    public static boolean isStarted = false;
    public static List<UHCPlugin> pluginList = new ArrayList<>();
    public static List<Player> livingPlayers = new ArrayList<>();
    public static List<Player> deadPlayers = new ArrayList<>();
    public static void registerPlugin(UHCPlugin p) {
        pluginList.add(p);
    }

    public static void registerPlugins(UHCPlugin[] p) {
        pluginList.addAll(Arrays.asList(p));
    }

    public static void registerPlugins(List<UHCPlugin> p) {
        pluginList.addAll(p);
    }
}
