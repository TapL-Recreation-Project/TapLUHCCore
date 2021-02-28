package me.swipez.uhccore;

import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.api.UHCPlugin;
import me.swipez.uhccore.commands.UHCCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class UHCCore extends JavaPlugin {
    boolean gamestarted = false;
    public HashMap<UUID, Boolean> isInGUI = new HashMap<>();
    public HashMap<UUID, Boolean> isInEventGUI = new HashMap<>();
    public HashMap<UUID, Integer> EventGUIPages = new HashMap<>();
    public List<UHCPlugin> dupelist = new ArrayList<>();

    @Override
    public void onEnable() {
        dupelist = UHCAPI.pluginList;
        getCommand("uhcmenu").setExecutor(new UHCCommand(this));
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
    }

    @Override
    public void onDisable() {
        UHCAPI.pluginList.clear();
    }
}
