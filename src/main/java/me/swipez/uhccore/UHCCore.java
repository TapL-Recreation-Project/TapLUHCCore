package me.swipez.uhccore;

import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.commands.UHCGUICommand;
import me.swipez.uhccore.customevents.BuiltInEvents;
import me.swipez.uhccore.runnables.BorderDivide;
import me.swipez.uhccore.runnables.TimeDecrease;
import me.swipez.uhccore.runnables.TimeRunOut;
import me.swipez.uhccore.uhclisteners.ChatEditListener;
import me.swipez.uhccore.uhclisteners.DeathListener;
import me.swipez.uhccore.uhclisteners.PVPStop;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class UHCCore extends JavaPlugin {

    public boolean meetupdone = false;

    // GUI Hashmaps
    public HashMap<UUID, Boolean> isInGUI = new HashMap<>();
    public HashMap<UUID, Boolean> isInEventGUI = new HashMap<>();
    public HashMap<UUID, Boolean> isInCustomEventGUI = new HashMap<>();
    public HashMap<UUID, Integer> EventGUIPages = new HashMap<>();

    //Time hashmap
    public HashMap<UUID, Integer> timeEdited = new HashMap<>();

    // Stage times
    public int invincibility;
    public int finalheal;
    public int pvpenable;
    public int bordershrink;
    public int meetup;
    public int borderdivide;

    //Border sizes
    public int bordersize;
    public int meetupborder;



    @Override
    public void onEnable() {
        Init();

        //Command
        getCommand("uhcmenu").setExecutor(new UHCGUICommand(this));

        //Listeners
        getServer().getPluginManager().registerEvents(new BuiltInEvents(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        getServer().getPluginManager().registerEvents(new PVPStop(this), this);
        getServer().getPluginManager().registerEvents(new ChatEditListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);

        //Runnables
        BukkitTask TimeDecrease = new TimeDecrease(this).runTaskTimer(this, 20, 20);
        BukkitTask TimeRunOut = new TimeRunOut(this).runTaskTimer(this, 20, 20);
        BukkitTask BorderDivide = new BorderDivide(this).runTaskTimer(this, 20, 20);
    }

    @Override
    public void onDisable() {
        UHCAPI.pluginList.clear();
    }

    public void Init(){
        invincibility = 30;
        finalheal = 60;
        pvpenable = 1200;
        bordershrink = 1800;
        meetup = 2100;
        borderdivide = 180;

        bordersize = 1000;
        meetupborder = 500;
    }
}
