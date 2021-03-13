package me.swipez.uhccore.runnables;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.utils.SendTitleBarMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeDecrease extends BukkitRunnable {

    UHCCore plugin;

    public TimeDecrease(UHCCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (UHCAPI.isStarted){
            if (plugin.meetup > 0){
                plugin.meetup--;
                for (Player others : Bukkit.getOnlinePlayers()){
                    SendTitleBarMessage.sendMessage(others, ChatColor.GOLD+"Meetup "+plugin.meetupborder+"x"+plugin.meetupborder, plugin.meetup);
                }
            }
            if (plugin.bordershrink > 0){
                plugin.bordershrink--;
                for (Player others : Bukkit.getOnlinePlayers()){
                    SendTitleBarMessage.sendMessage(others, ChatColor.GOLD+"Border Shrink "+plugin.bordersize+"x"+plugin.bordersize, plugin.bordershrink);
                }
            }
            if (plugin.pvpenable > 0){
                plugin.pvpenable--;
                for (Player others : Bukkit.getOnlinePlayers()){
                    SendTitleBarMessage.sendMessage(others, ChatColor.GOLD+"Pvp Enables", plugin.pvpenable);
                }
            }
            if (plugin.finalheal > 0){
                plugin.finalheal--;
                for (Player others : Bukkit.getOnlinePlayers()){
                    SendTitleBarMessage.sendMessage(others, ChatColor.GOLD+"Final Heal", plugin.finalheal);
                }
            }
            if (plugin.invincibility > 0){
                plugin.invincibility--;
                for (Player others : Bukkit.getOnlinePlayers()){
                    SendTitleBarMessage.sendMessage(others, ChatColor.GOLD+"Invincibility", plugin.invincibility);
                }
            }
        }
    }
}
