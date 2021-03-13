package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.guis.GUIManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.UUID;

public class ChatEditListener implements Listener {

    UHCCore plugin;

    public ChatEditListener(UHCCore plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerChats(PlayerChatEvent event){
        if (plugin.timeEdited.containsKey(event.getPlayer().getUniqueId())) {
            UUID uuid = event.getPlayer().getUniqueId();
            try {
                switch (plugin.timeEdited.get(uuid)) {


                    case 1:
                        plugin.invincibility = Integer.parseInt(event.getMessage());
                        plugin.timeEdited.remove(uuid);
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Invincibility period is now set to " + event.getMessage() + " seconds");
                        plugin.isInGUI.put(uuid, true);
                        break;
                    case 2:
                        plugin.finalheal = Integer.parseInt(event.getMessage());
                        plugin.timeEdited.remove(uuid);
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Final heal is now set to " + event.getMessage() + " seconds");
                        plugin.isInGUI.put(uuid, true);
                        break;
                    case 3:
                        plugin.pvpenable = Integer.parseInt(event.getMessage()) * 60;
                        plugin.timeEdited.remove(uuid);
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "PVP enable period is now set to " + event.getMessage() + " minutes");
                        plugin.isInGUI.put(uuid, true);
                        break;
                    case 4:
                        plugin.bordershrink = Integer.parseInt(event.getMessage()) * 60;
                        plugin.timeEdited.remove(uuid);
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Border shrink period is now set to " + event.getMessage() + " minutes");
                        plugin.isInGUI.put(uuid, true);
                        break;
                    case 5:
                        plugin.meetup = Integer.parseInt(event.getMessage()) * 60;
                        plugin.timeEdited.remove(uuid);
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Meetup period is now set to " + event.getMessage() + " minutes");
                        plugin.isInGUI.put(uuid, true);
                        break;
                }
                event.setCancelled(true);
            } catch (NumberFormatException exception) {
                event.getPlayer().sendMessage(ChatColor.GOLD+"That is not a number! Try again");
                event.setCancelled(true);
            }
        }
    }
}
