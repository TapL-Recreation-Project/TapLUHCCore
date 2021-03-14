package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.GUIListener;
import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class UHCStop {
    public static void stopUHC(Player winner, String message) {
        if (winner != null) {
            Bukkit.broadcastMessage(ChatColor.RED + "UHC has ended!");
            Bukkit.broadcastMessage(ChatColor.GREEN + "The winner of the UHC is " + winner.getDisplayName() + "! Congrats!");
        } else {
            Bukkit.broadcastMessage(message);
        }
        UHCAPI.isStarted = false;
        UHCCore.meetupdone = false;
        UHCCore.init();
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            world.getWorldBorder().setSize(29999984);
            world.setDifficulty(GUIListener.STORED_DIFFICULTY);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, GUIListener.STORED_WEATHER);
            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, false);
        }
        for (Player others : Bukkit.getOnlinePlayers()) {
            others.setInvulnerable(false);
            if (others.getGameMode().equals(GameMode.SPECTATOR)){
                Location location = others.getLocation();
                double y = others.getWorld().getHighestBlockYAt(location);
                location.setY(y);
                others.teleport(location);
                others.setGameMode(GameMode.SURVIVAL);
            }
        }
        UHCAPI.livingPlayers.clear();
        UHCAPI.deadPlayers.clear();
    }
}
