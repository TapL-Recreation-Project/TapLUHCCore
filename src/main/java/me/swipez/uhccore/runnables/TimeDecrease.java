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
        if (UHCCore.meetup > 0) {
            UHCCore.meetup--;
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Meetup " + UHCCore.meetupborder + "x" + UHCCore.meetupborder,
                    UHCCore.meetup);
        }
        if (UHCCore.bordershrink > 0) {
            UHCCore.bordershrink--;
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Border Shrink " + UHCCore.bordersize + "x" + UHCCore.bordersize,
                    UHCCore.bordershrink);
        }
        if (UHCCore.pvpenable > 0) {
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Pvp Enables",
                    UHCCore.pvpenable);
        }
        if (UHCCore.finalheal > 0) {
            UHCCore.finalheal--;
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Final Heal",
                    UHCCore.finalheal);
        }
        if (UHCCore.invincibility > 0) {
            UHCCore.invincibility--;
            SendTitleBarMessage.broadcast(ChatColor.GOLD + "Invincibility",
                    UHCCore.invincibility);
        }
    }
}
