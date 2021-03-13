package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    UHCCore plugin;

    public DeathListener(UHCCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        if (UHCAPI.isStarted){
            Player player = event.getEntity();
            Location location = player.getLocation();
            player.getWorld().strikeLightning(location);
            int AlivePlayers = getAlivePlayerCount();
            if (AlivePlayers == 1){
                for (Player others : Bukkit.getOnlinePlayers()){
                    if (others.getGameMode().equals(GameMode.SURVIVAL)){
                        Bukkit.broadcastMessage(ChatColor.GREEN+"The winner of the UHC is "+others.getDisplayName());
                        UHCAPI.isStarted = false;
                        plugin.meetupdone = false;
                        for (World world : Bukkit.getWorlds()) {
                            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                            world.getWorldBorder().setSize(29999984);
                            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
                            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, false);
                        }
                        others.setInvulnerable(false);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        if (UHCAPI.isStarted){
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

    public Integer getAlivePlayerCount(){
        int AlivePlayers = 0;
        for (Player others : Bukkit.getOnlinePlayers()){
            if (others.getGameMode().equals(GameMode.SURVIVAL)){
                AlivePlayers++;
            }
        }
        return AlivePlayers;
    }
}
