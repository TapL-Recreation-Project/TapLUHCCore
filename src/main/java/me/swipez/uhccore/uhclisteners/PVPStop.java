package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPStop implements Listener {

    UHCCore plugin;

    public PVPStop(UHCCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHitsPlayer(EntityDamageByEntityEvent event) {
        if (UHCAPI.isStarted) {
            if (UHCCore.pvpenable != -1) {
                if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                    event.getDamager().sendMessage(ChatColor.RED + "PVP is currently not enabled!");
                    event.setCancelled(true);
                } else if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
                    Projectile projectile = (Projectile) event.getDamager();
                    if (projectile.getShooter() instanceof Player) {
                        ((Player) projectile.getShooter()).sendMessage(ChatColor.RED + "PVP is currently not enabled!");
                        event.setCancelled(true);
                    }
                }
            } else {
                if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
                    Projectile projectile = (Projectile) event.getDamager();
                    if (projectile.getShooter() instanceof Player) {
                        double health = (((Player) event.getEntity()).getHealth() - event.getDamage());
                        if (health < 0){
                            health = 0;
                        }
                        ((Player) projectile.getShooter()).sendMessage(ChatColor.GREEN + "Opponents health: " + ChatColor.RED + health + " ❤");
                    }
                }
            }
        }
    }
}
