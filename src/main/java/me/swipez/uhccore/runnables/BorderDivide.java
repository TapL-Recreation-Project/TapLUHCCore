package me.swipez.uhccore.runnables;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.utils.SendTitleBarMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderDivide extends BukkitRunnable {

    UHCCore plugin;

    public BorderDivide(UHCCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!UHCAPI.isStarted || !UHCCore.meetupdone) {
            return;
        }
        if (UHCCore.borderdivide > 0) {
            UHCCore.borderdivide--;
            for (Player others : Bukkit.getOnlinePlayers()) {
                SendTitleBarMessage.sendMessage(others, ChatColor.GOLD + "Border Divide " + (others.getWorld().getWorldBorder().getSize() / 2) / 2 + "x" + (others.getWorld().getWorldBorder().getSize() / 2) / 2, plugin.borderdivide);
            }
        } else if (UHCCore.borderdivide == 0) {
            UHCCore.borderdivide = 120;
            for (World world : Bukkit.getWorlds()) {
                world.getWorldBorder().setSize(world.getWorldBorder().getSize() / 2);
            }
        }
    }

}
