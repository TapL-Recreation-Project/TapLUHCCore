package me.swipez.uhccore.runnables;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TimeRunOut extends BukkitRunnable {

    UHCCore plugin;

    public TimeRunOut(UHCCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (UHCAPI.isStarted){
            if (UHCCore.invincibility == 0){
                UHCCore.invincibility = -1;
                for (Player others : Bukkit.getOnlinePlayers()){
                    others.setInvulnerable(false);
                }
            }
            if (UHCCore.finalheal == 0){
                UHCCore.finalheal = -1;
                for (Player others : Bukkit.getOnlinePlayers()){
                    double maxhealth = others.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    others.setHealth(maxhealth);
                }
            }
            if (UHCCore.pvpenable == 0){
                UHCCore.pvpenable = -1;
                Bukkit.broadcastMessage(ChatColor.RED+"PVP Has been enabled!");
            }
            if (UHCCore.bordershrink == 0){
                UHCCore.bordershrink = -1;
                for (Player others : Bukkit.getOnlinePlayers()){
                    for (World world : Bukkit.getWorlds()) {
                        world.getWorldBorder().setSize(plugin.bordersize*2);
                    }
                    double locx = Math.abs(others.getLocation().getX());
                    double locz = Math.abs(others.getLocation().getZ());
                    if (locx > plugin.bordersize || locz > plugin.bordersize){
                        others.setHealth(0);
                        others.sendMessage(ChatColor.GOLD+"You did not make it inside the border!");
                    }
                }
            }
            if (UHCCore.meetup == 0){
                UHCCore.meetup = -1;
                UHCCore.meetupdone = true;
                for (Player others : Bukkit.getOnlinePlayers()){
                    for (World world : Bukkit.getWorlds()) {
                        world.getWorldBorder().setSize(UHCCore.meetupborder*2);
                    }
                    double locx = Math.abs(others.getLocation().getX());
                    double locz = Math.abs(others.getLocation().getZ());
                    if (locx > UHCCore.meetupborder || locz > UHCCore.meetupborder){
                        Random random = new Random();
                        double randomx = random.nextInt(UHCCore.meetupborder/2);
                        double randomz = random.nextInt(UHCCore.meetupborder/2);
                        int coinflipx = (int) (Math.random()*100);
                        int coinflipz = (int) (Math.random()*100);
                        if (coinflipx < 50){
                            randomx = randomx * -1;
                        }
                        if (coinflipz < 50){
                            randomz = randomz * -1;
                        }
                        Location teleportloc = others.getLocation();
                        teleportloc.setX(randomx);
                        teleportloc.setZ(randomz);
                        double y = others.getWorld().getHighestBlockYAt(teleportloc);
                        teleportloc.setY(y);
                        others.teleport(teleportloc);
                        others.sendMessage(ChatColor.GOLD+"You were outside of the meetup border, you have been teleported inside.");
                    }
                }
            }
        }
    }
}
