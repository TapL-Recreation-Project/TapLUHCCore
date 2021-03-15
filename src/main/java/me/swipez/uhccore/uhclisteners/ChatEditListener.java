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
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Invincibility period is now set to " + event.getMessage() + " seconds");
                        break;
                    case 2:
                        plugin.finalheal = Integer.parseInt(event.getMessage());
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Final heal is now set to " + event.getMessage() + " seconds");
                        break;
                    case 3:
                        plugin.pvpenable = Integer.parseInt(event.getMessage()) * 60;
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "PVP enable period is now set to " + event.getMessage() + " minutes");
                        break;
                    case 4:
                        plugin.bordershrink = Integer.parseInt(event.getMessage()) * 60;
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Border shrink period is now set to " + event.getMessage() + " minutes");
                        break;
                    case 5:
                        plugin.meetup = Integer.parseInt(event.getMessage())*60;
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Meetup period is now set to " + event.getMessage() + " minutes");
                        break;
                    case 6:
                        plugin.initialborder = Integer.parseInt(event.getMessage());
                        event.getPlayer().openInventory(GUIManager.makeBorderSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Initial border size is now set to " + event.getMessage());
                        break;
                    case 7:
                        plugin.bordersize = Integer.parseInt(event.getMessage());
                        event.getPlayer().openInventory(GUIManager.makeBorderSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Border shrink size is now set to " + event.getMessage());
                        break;
                    case 8:
                        plugin.meetupborder = Integer.parseInt(event.getMessage());
                        event.getPlayer().openInventory(GUIManager.makeBorderSettingsGUI(event.getPlayer()));
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Meetup border size is now set to " + event.getMessage());
                        break;
                }

                plugin.timeEdited.remove(uuid);
                plugin.isInGUI.put(uuid, true);
            }
            catch (NumberFormatException exception) {
                if (event.getMessage().equals("x"))  {
                    if (plugin.timeEdited.get(uuid) < 6){
                        event.getPlayer().openInventory(GUIManager.makeTimingSettingsGUI(event.getPlayer()));
                    }
                    else {
                        event.getPlayer().openInventory(GUIManager.makeBorderSettingsGUI(event.getPlayer()));
                    }
                    plugin.timeEdited.remove(uuid);
                    event.setCancelled(true);
                    plugin.isInGUI.put(uuid, true);
                }
                else {
                    event.getPlayer().sendMessage(ChatColor.GOLD+"That is not a number! Try again");
                }
            }
            event.setCancelled(true);
        }
    }
}
