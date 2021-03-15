package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.itembuttons.ItemButtonManager;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (UHCAPI.isStarted) {
            if (!UHCAPI.deadPlayers.contains(e.getPlayer()) && !UHCAPI.livingPlayers.contains(e.getPlayer())) {
                if (!UHCCore.meetupdone) {
                    e.getPlayer().setHealth(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                    e.getPlayer().setFoodLevel(20);

                    Random random = new Random();
                    double randomx = random.nextInt(UHCCore.plugin.initialborder / 2);
                    double randomz = random.nextInt(UHCCore.plugin.initialborder / 2);
                    int coinflipx = (int) (Math.random() * 100);
                    int coinflipz = (int) (Math.random() * 100);
                    if (coinflipx < 50) {
                        randomx = randomx * -1;
                    }
                    if (coinflipz < 50) {
                        randomz = randomz * -1;
                    }
                    Location teleportloc = e.getPlayer().getLocation();
                    teleportloc.setX(randomx);
                    teleportloc.setZ(randomz);
                    double y = e.getPlayer().getWorld().getHighestBlockYAt((int) randomx, (int) randomz, HeightMap.WORLD_SURFACE);
                    teleportloc.setY(y);
                    e.getPlayer().teleport(teleportloc);
                    e.getPlayer().sendMessage(ChatColor.GOLD + "You joined the UHC late! Good luck!");
                    e.getPlayer().getInventory().clear();
                    e.getPlayer().setGameMode(GameMode.SURVIVAL);
                    UHCAPI.livingPlayers.add(e.getPlayer());
                    PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 50, 100);
                    e.getPlayer().addPotionEffect(resistance);
                    e.setJoinMessage(ChatColor.GREEN + e.getPlayer().getName() + " joined in the middle of the game!");
                } else {
                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                    e.getPlayer().sendMessage(ChatColor.RED + "Sorry! You joined the UHC late :(");
                }
            } else if (UHCAPI.deadPlayers.contains(e.getPlayer()) || !UHCAPI.livingPlayers.contains(e.getPlayer())) {
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
                e.getPlayer().sendMessage(ChatColor.RED + "You joined the game in the imddle of a UHC but you were already dead!");
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (UHCAPI.isStarted) {
            if (!UHCAPI.deadPlayers.contains(e.getPlayer())) {
                UHCAPI.deadPlayers.add(e.getPlayer());
                e.getPlayer().setHealth(0);
            }
            if (UHCAPI.livingPlayers.contains(e.getPlayer())) {
                UHCAPI.livingPlayers.remove(e.getPlayer());
                e.getPlayer().setHealth(0);
            }

            if (e.getPlayer().getLastDamageCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                if (e.getPlayer().getLastDamageCause().getEntityType().equals(EntityType.PLAYER)) {
                    e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), ItemButtonManager.getGoldenHead().clone());
                }
            }
            e.setQuitMessage(ChatColor.RED + e.getPlayer().getName() + " decided to quit in the middle of a game!");
        }
    }
}
