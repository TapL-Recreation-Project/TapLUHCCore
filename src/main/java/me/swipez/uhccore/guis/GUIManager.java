package me.swipez.uhccore.guis;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.api.UHCPlugin;
import me.swipez.uhccore.customevents.BuiltInEvents;
import me.swipez.uhccore.itembuttons.ItemButtonManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GUIManager {


    public static Inventory makeStartSettingsGUI(Player p, boolean started) {
        Inventory inventory = Bukkit.createInventory(p, 27, "UHC Main Menu");
        inventory.setItem(11, ItemButtonManager.UHC_SETTINGS);
        if (started){
            inventory.setItem(13, ItemButtonManager.END_UHC);
        }
        else {
            inventory.setItem(13, ItemButtonManager.START_UHC);
        }
        inventory.setItem(15, ItemButtonManager.EVENT_MODIFIERS);
        InventoryFill(inventory, Material.RED_STAINED_GLASS_PANE, ChatColor.RED + " ");
        return inventory;
    }
    public static Inventory makeTimingSettingsGUI(Player p) {
        Inventory inventory = Bukkit.createInventory(p, 27, "UHC Timing Settings Menu");
        inventory.setItem(11, ItemButtonManager.GRACE_PERIOD);
        inventory.setItem(12, ItemButtonManager.FINAL_HEAL);
        inventory.setItem(13, ItemButtonManager.PVP);
        inventory.setItem(14, ItemButtonManager.BORDER);
        inventory.setItem(15, ItemButtonManager.MEETUP);
        InventoryFill(inventory, Material.GREEN_STAINED_GLASS_PANE, ChatColor.RED + " ");
        return inventory;
    }

    public static Inventory makeUHCSettingsGUI(Player p) {
        Inventory inventory = Bukkit.createInventory(p, 27, "UHC Settings Menu");
        inventory.setItem(11, ItemButtonManager.TIMING_SETTINGS);
        inventory.setItem(15, ItemButtonManager.BORDER_SETTINGS);
        InventoryFill(inventory, Material.GREEN_STAINED_GLASS_PANE, ChatColor.RED + " ");
        return inventory;
    }

    public static Inventory makeEventsGUI(Player p, Integer page, List<UHCPlugin> list) {
        Inventory inventory = Bukkit.createInventory(p, 27, "UHC Events Menu");
        DisplayVanillaEvents(inventory, BuiltInEvents.customEventsBooleans);
        inventory.setItem(26, ItemButtonManager.RETURN);
        inventory.setItem(25, ItemButtonManager.NEXT_PAGE);
        inventory.setItem(24, ItemButtonManager.PREVIOUS_PAGE);
        inventory.setItem(23, ItemButtonManager.INSTALLED_EVENTS);
        InventoryFill(inventory, Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + " ");
        return inventory;
    }

    public static Inventory makeCustomEventsGUI(Player p, Integer page, List<UHCPlugin> list) {
        Inventory inventory = Bukkit.createInventory(p, 27, "UHC Custom Events Menu");
        if (UHCAPI.pluginList.size() > 0) {
            getPluginSelectorInventory(inventory, page);
        }
        DisplayProperly(inventory, list);
        inventory.setItem(26, ItemButtonManager.RETURN);
        inventory.setItem(25, ItemButtonManager.NEXT_PAGE);
        inventory.setItem(24, ItemButtonManager.PREVIOUS_PAGE);
        inventory.setItem(23, ItemButtonManager.EVENT_MODIFIERS);
        InventoryFill(inventory, Material.GRAY_STAINED_GLASS_PANE, ChatColor.RED + " ");
        return inventory;
    }

    public static Inventory InventoryFill(Inventory inventory, Material material, String name) {
        ItemStack desiredmaterial = new ItemStack(material, 1);
        ItemMeta meta = desiredmaterial.getItemMeta();
        meta.setDisplayName(name);
        desiredmaterial.setItemMeta(meta);
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, desiredmaterial);
            }
        }
        return inventory;
    }


    public static Inventory getPluginSelectorInventory(Inventory inventory, Integer page) {
        int pagestarter = page * 9;
        int displayedplugins = 0;
        int pluginstodisplay = UHCAPI.pluginList.size() - pagestarter;
        for (int i = 0; i < inventory.getSize() - 1; i++) {
            for (int k = pagestarter; k < UHCAPI.pluginList.size(); k++) {
                if (displayedplugins < 9 && pluginstodisplay != 0) {
                    ItemStack item = UHCAPI.pluginList.get(k).getDisplayStack();
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GOLD + "Event from: " + UHCAPI.pluginList.get(k).getName());
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    if (!inventory.contains(item)) {
                        inventory.addItem(item);
                        pluginstodisplay--;
                        if (pluginstodisplay > 0) {
                            displayedplugins++;
                        }
                    }
                }
            }
        }

        return inventory;
    }

    public static Inventory DisplayProperly(Inventory inventory, List<UHCPlugin> list) {
        for (int i = 0; i < inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) != null) {
                for (UHCPlugin plugin : UHCAPI.pluginList)
                    if (inventory.getItem(i).getItemMeta().getDisplayName().equals(plugin.getDisplayStack().getItemMeta().getDisplayName())) {
                        if (list.contains(plugin)) {
                            inventory.setItem(i + 9, ItemButtonManager.ENABLED_EVENT);
                        } else {
                            inventory.setItem(i + 9, ItemButtonManager.DISABLED_EVENT);
                        }
                    }
            }
        }
        return inventory;
    }
    public static Inventory DisplayVanillaEvents(Inventory inventory, Map<ItemStack, Boolean> items){
        int slot = 0;
        for (Map.Entry<ItemStack, Boolean> entry : items.entrySet()) {
            inventory.addItem(entry.getKey());
            if (entry.getValue()){
                inventory.setItem(slot+9, ItemButtonManager.ENABLED_EVENT);
            }
            else {
                inventory.setItem(slot+9, ItemButtonManager.DISABLED_EVENT);
            }
            slot++;
        }
        return inventory;
    }
}