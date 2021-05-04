package me.swipez.uhccore.runnables;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.utils.SendTitleBarMessage;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeDecrease extends BukkitRunnable {

    UHCCore plugin;

    public TimeDecrease(UHCCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!UHCAPI.isStarted) {
            return;
        }
        // Game phases, from last to first
        if (UHCCore.meetup > 0) UHCCore.meetup --;
        if (UHCCore.bordershrink > 0) UHCCore.bordershrink --;
        if (UHCCore.pvpenable > 0) UHCCore.pvpenable --;
        if (UHCCore.finalheal > 0) UHCCore.finalheal --;
        if (UHCCore.invincibility > 0) UHCCore.invincibility --;

        // Only show message for current phase
        if (UHCCore.meetup > 0) {
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Meetup " + UHCCore.meetupborder + "x" +
                    UHCCore.meetupborder, UHCCore.meetup);
        } else if (UHCCore.bordershrink > 0) {
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Border Shrink " + UHCCore.bordersize + "x" +
                    UHCCore.bordersize, UHCCore.bordershrink);
        } else if (UHCCore.pvpenable > 0) {
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Pvp Enables", UHCCore.pvpenable);
        } else if (UHCCore.finalheal > 0) {
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Final Heal", UHCCore.finalheal);
        } else if (UHCCore.invincibility > 0) {
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Invincibility", UHCCore.invincibility);
        }
    }
}
