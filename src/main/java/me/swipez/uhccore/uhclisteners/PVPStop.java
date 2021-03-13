package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPStop implements Listener {

    UHCCore plugin;

    public PVPStop(UHCCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHitsPlayer(EntityDamageByEntityEvent event){
        if (UHCAPI.isStarted){
            if (plugin.pvpenable != -1){
                if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
                    event.getDamager().sendMessage(ChatColor.RED+"PVP is currently not enabled!");
                    event.setCancelled(true);
                }
            }
        }
    }
}
