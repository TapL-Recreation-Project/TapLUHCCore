package me.swipez.uhccore;

import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.api.UHCPlugin;
import me.swipez.uhccore.guis.GUIManager;
import me.swipez.uhccore.itembuttons.ItemButtonManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class GUIListener implements Listener {
    UHCCore plugin;

    public GUIListener(UHCCore plugin) {
        this.plugin = plugin;
    }
    List<UHCPlugin> api = new ArrayList<>();

    @EventHandler
    public void onPlayerOpenGUI(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (plugin.isInGUI.get(player.getUniqueId())){
            int pluginstoload = UHCAPI.pluginList.size();
            int vanillalistsize = 0;
            int maxpagespossible = 0;
            for (int i=0;i<100;i++){
                if (pluginstoload > 9){
                    pluginstoload = pluginstoload-9;
                    maxpagespossible++;
                }
            }
            ItemStack clickeditem = e.getCurrentItem();
            Inventory inventory = e.getClickedInventory();
            int slotofitem = 0;
            if (clickeditem != null){
                for (int i=0; i<inventory.getSize()-1; i++){
                    if (clickeditem.isSimilar(inventory.getItem(i))){
                        slotofitem = i;
                    }
                }
                if (clickeditem.isSimilar(ItemButtonManager.EVENT_MODIFIERS)){
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    int pages = 0;
                    plugin.EventGUIPages.put(player.getUniqueId(), 0);
                    player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                    plugin.isInGUI.put(player.getUniqueId(), true);
                    plugin.isInEventGUI.put(player.getUniqueId(), true);
                }
                else {
                    e.setCancelled(true);
                }
                for (UHCPlugin plugin : UHCAPI.pluginList){
                    if (clickeditem.getItemMeta().getDisplayName().equals(plugin.getDisplayStack().getItemMeta().getDisplayName()) && clickeditem.getType().equals(plugin.getDisplayStack().getType())){
                        ItemStack comparableitem = addTitleLore(plugin.getDisplayStack());
                        if (clickeditem.getItemMeta().getLore().contains(comparableitem.getItemMeta().getLore().get(0))){
                            if (api.contains(plugin)){
                                api.remove(plugin);
                                inventory.setItem(slotofitem+9, ItemButtonManager.DISABLED_EVENT);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                            }
                            else {
                                api.add(plugin);
                                inventory.setItem(slotofitem+9, ItemButtonManager.ENABLED_EVENT);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            }
                        }
                    }
                }
                if (clickeditem.isSimilar(ItemButtonManager.RETURN)){
                    player.openInventory(GUIManager.makeStartSettingsGUI(player, UHCAPI.isStarted));
                    plugin.isInGUI.put(player.getUniqueId(), true);
                }
                else if (clickeditem.isSimilar(ItemButtonManager.NEXT_PAGE)){
                    //if (maxpagespossible > 0){
                        int pagesstored = plugin.EventGUIPages.get(player.getUniqueId());
                        int pages = pagesstored + 1;
                        if (plugin.isInCustomEventGUI.get(player.getUniqueId())){
                            if (pages > maxpagespossible){
                                pages = maxpagespossible;
                            }
                        }
                        else if (plugin.isInEventGUI.get(player.getUniqueId())){
                            if (pages > vanillalistsize){
                                pages = vanillalistsize;
                            }
                        }
                        if (plugin.isInCustomEventGUI.get(player.getUniqueId())){
                            player.openInventory(GUIManager.makeCustomEventsGUI(player, pages, api));
                            plugin.isInCustomEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }
                        else if (plugin.isInEventGUI.get(player.getUniqueId())){
                            player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                            plugin.isInEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    //}
                }
                else if (clickeditem.isSimilar(ItemButtonManager.PREVIOUS_PAGE)){
                    //if (maxpagespossible > 0){
                        int pagesstored = plugin.EventGUIPages.get(player.getUniqueId());
                        int pages = pagesstored - 1;
                        if (pages <= 0){
                            pages = 0;
                        }
                        if (plugin.isInCustomEventGUI.get(player.getUniqueId())){
                            player.openInventory(GUIManager.makeCustomEventsGUI(player, pages, api));
                            plugin.isInCustomEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                        }
                        else if (plugin.isInEventGUI.get(player.getUniqueId())){
                            player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                            plugin.isInEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                        }
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    //}
                }
                else if (clickeditem.isSimilar(ItemButtonManager.INSTALLED_EVENTS)){
                    int pages = 0;
                    plugin.EventGUIPages.put(player.getUniqueId(), 0);
                    player.openInventory(GUIManager.makeCustomEventsGUI(player, pages, api));
                    plugin.isInGUI.put(player.getUniqueId(), true);
                    plugin.isInEventGUI.put(player.getUniqueId(), false);
                    plugin.isInCustomEventGUI.put(player.getUniqueId(), true);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                }
                else if (clickeditem.isSimilar(ItemButtonManager.UHC_SETTINGS)){
                    player.openInventory(GUIManager.makeUHCSettingsGUI(player));
                    plugin.isInGUI.put(player.getUniqueId(), true);
                }
                else if (clickeditem.isSimilar(ItemButtonManager.TIMING_SETTINGS)){
                    player.openInventory(GUIManager.makeTimingSettingsGUI(player));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1F);
                    plugin.isInGUI.put(player.getUniqueId(), true);
                }
                if (clickeditem.isSimilar(ItemButtonManager.START_UHC)){
                    if (api.size() > 0){
                        for (UHCPlugin uhcPlugin : api) {
                            uhcPlugin.start();
                        }
                        player.closeInventory();
                        player.sendMessage(ChatColor.GREEN+"All Selected events have started!");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        UHCAPI.isStarted = true;
                    }
                    else {
                        player.openInventory(GUIManager.makeStartSettingsGUI(player, UHCAPI.isStarted));
                        player.sendMessage(ChatColor.RED+"No events to enable");
                        plugin.isInGUI.put(player.getUniqueId(), true);
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        UHCAPI.isStarted = true;
                    }
                    Bukkit.broadcastMessage(ChatColor.RED+"UHC Has Begun!");
                }
                else if (clickeditem.isSimilar(ItemButtonManager.END_UHC)){
                    player.closeInventory();
                    Bukkit.broadcastMessage(ChatColor.RED+"UHC has ended!");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                    UHCAPI.isStarted = false;
                }
            }
        }
    }
    @EventHandler
    public void onPlayerCloseGUI(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        plugin.isInGUI.put(p.getUniqueId(), false);
        plugin.isInEventGUI.put(p.getUniqueId(), false);
        plugin.isInCustomEventGUI.put(p.getUniqueId(), false);
    }
    public static ItemStack addTitleLore(ItemStack item){
        for (int i=0;i<UHCAPI.pluginList.size();i++){
            if (UHCAPI.pluginList.get(i).getDisplayStack().equals(item)){
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GOLD+"Event from: "+UHCAPI.pluginList.get(i).getName());
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

}
