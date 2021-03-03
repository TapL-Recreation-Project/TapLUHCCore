package me.swipez.uhccore.customevents;

import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;

public class BuiltInEvents implements Listener {
    public static boolean CUT_CLEAN = true;
    public static boolean TIMBER = true;
    public static boolean VEINMINER = false;
    public static boolean COOKED_MEAT = true;
    public static boolean ALWAYS_DAY = true;


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Bukkit.broadcastMessage("block broked");
        if (TIMBER && UHCAPI.isStarted && (e.getBlock().getType().toString().toLowerCase().contains("log"))) {
            Bukkit.broadcastMessage("log broked");


            Block checkblock = e.getBlock();
            for (int i=e.getBlock().getY();i<256-e.getBlock().getY();i++){
                Bukkit.broadcastMessage("in for loop " + i);

                if (checkblock.getType().toString().toLowerCase().contains("log")){
                    Bukkit.broadcastMessage("in if " + i);

                    checkblock.breakNaturally();
                    checkblock = checkblock.getRelative(BlockFace.UP);
                } else {
                    return;
                }
            }

        }
    }
}


/*
int minutes = 0;
while (x >= 60) {
    x -= 60;
    minutes++
}

 */