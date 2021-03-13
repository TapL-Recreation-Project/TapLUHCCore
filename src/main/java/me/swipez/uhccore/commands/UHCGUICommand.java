package me.swipez.uhccore.commands;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.guis.GUIManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UHCGUICommand implements CommandExecutor {
    UHCCore plugin;

    public UHCGUICommand(UHCCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(GUIManager.makeStartSettingsGUI(player, UHCAPI.isStarted));
            plugin.isInGUI.put(player.getUniqueId(), true);
            plugin.EventGUIPages.put(player.getUniqueId(), 0);
        } else {
            sender.sendMessage(ChatColor.WHITE + "[TaplUHC] " + ChatColor.RED + "Only players can run this command!");
        }
        return true;
    }
}
