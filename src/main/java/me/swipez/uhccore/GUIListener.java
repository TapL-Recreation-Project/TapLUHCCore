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
                    if (clickeditem.getItemMeta().getDisplayName().equals(plugin.getDisplayStack().getItemMeta().getDisplayName()) && clickeditem.getItemMeta().getLore().equals(addTitleLore(plugin.getDisplayStack()))){
                        if (api.contains(plugin)){
                            api.remove(plugin);
                            inventory.setItem(slotofitem+9, ItemButtonManager.DISABLED_EVENT);
                        }
                        else {
                            api.add(plugin);
                            inventory.setItem(slotofitem+9, ItemButtonManager.ENABLED_EVENT);
                        }
                    }
                }
                if (clickeditem.isSimilar(ItemButtonManager.RETURN)){
                    player.openInventory(GUIManager.makeStartSettingsGUI(player));
                    plugin.isInGUI.put(player.getUniqueId(), true);
                }
                else if (clickeditem.isSimilar(ItemButtonManager.NEXT_PAGE)){
                    //if (maxpagespossible > 0){
                        int pagesstored = plugin.EventGUIPages.get(player.getUniqueId());
                        int pages = pagesstored + 1;
                        if (pages > maxpagespossible){
                            pages = maxpagespossible;
                        }
                        player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
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
                        player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    //}
                }
                if (clickeditem.isSimilar(ItemButtonManager.START_UHC)){
                    if (api.size() > 0){
                        for (UHCPlugin uhcPlugin : api) {
                            uhcPlugin.start();
                        }
                        player.closeInventory();
                        player.sendMessage(ChatColor.GREEN+"All Selected events have started!");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    }
                    else {
                        player.openInventory(GUIManager.makeStartSettingsGUI(player));
                        player.sendMessage(ChatColor.RED+"No events to enable");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, (float) 0.2);
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    }
                    Bukkit.broadcastMessage(ChatColor.RED+"UHC Has Begun!");
                }
            }
        }
    }
    @EventHandler
    public void onPlayerCloseGUI(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        plugin.isInGUI.put(p.getUniqueId(), false);
        plugin.isInEventGUI.put(p.getUniqueId(), false);
    }
    public static ItemStack addTitleLore(ItemStack item){
        ItemStack comparableitem = null;
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
