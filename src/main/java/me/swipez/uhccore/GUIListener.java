package me.swipez.uhccore;

import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.api.UHCPlugin;
import me.swipez.uhccore.bstats.Metrics;
import me.swipez.uhccore.customevents.BuiltInEvents;
import me.swipez.uhccore.guis.GUIManager;
import me.swipez.uhccore.itembuttons.ItemButtonManager;
import me.swipez.uhccore.uhclisteners.UHCStop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GUIListener implements Listener {
    UHCCore plugin;

    public GUIListener(UHCCore plugin) {
        this.plugin = plugin;
    }
    List<UHCPlugin> api = new ArrayList<>();

    public static Difficulty STORED_DIFFICULTY;
    public static boolean STORED_WEATHER;

    @EventHandler
    public void onPlayerOpenGUI(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (plugin.isInGUI.containsKey(player.getUniqueId())){
            if (plugin.isInGUI.get(player.getUniqueId())) {
                int pluginstoload = UHCAPI.pluginList.size();
                int vanillalistsize = 0;
                int maxpagespossible = 0;
                for (int i = 0; i < 100; i++) {
                    if (pluginstoload > 9) {
                        pluginstoload = pluginstoload - 9;
                        maxpagespossible++;
                    }
                }
                ItemStack clickeditem = e.getCurrentItem();
                Inventory inventory = e.getClickedInventory();
                int slotofitem = 0;
                if (clickeditem != null) {
                    for (int i = 0; i < inventory.getSize() - 1; i++) {
                        if (clickeditem.isSimilar(inventory.getItem(i))) {
                            slotofitem = i;
                        }
                    }
                    if (clickeditem.isSimilar(ItemButtonManager.EVENT_MODIFIERS)) {
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        int pages = 0;
                        plugin.EventGUIPages.put(player.getUniqueId(), 0);
                        player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                        plugin.isInGUI.put(player.getUniqueId(), true);
                        plugin.isInEventGUI.put(player.getUniqueId(), true);
                    } else {
                        e.setCancelled(true);
                    }
                    for (UHCPlugin plugin : UHCAPI.pluginList) {
                        if (clickeditem.getItemMeta().getDisplayName().equals(plugin.getDisplayStack().getItemMeta().getDisplayName()) && clickeditem.getType().equals(plugin.getDisplayStack().getType())) {
                            ItemStack comparableitem = addTitleLore(plugin.getDisplayStack());
                            if (clickeditem.getItemMeta().getLore().contains(comparableitem.getItemMeta().getLore().get(0))) {
                                if (api.contains(plugin)) {
                                    api.remove(plugin);
                                    inventory.setItem(slotofitem + 9, ItemButtonManager.DISABLED_EVENT);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                                } else {
                                    api.add(plugin);
                                    inventory.setItem(slotofitem + 9, ItemButtonManager.ENABLED_EVENT);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                                }
                            }
                        }
                    }
                    for (Map.Entry<ItemStack, Boolean> entry : BuiltInEvents.customEventsBooleans.entrySet()) {
                        if (clickeditem.isSimilar(entry.getKey())) {
                            if (entry.getValue()) {
                                entry.setValue(false);
                                inventory.setItem(slotofitem + 9, ItemButtonManager.DISABLED_EVENT);
                            } else {
                                entry.setValue(true);
                                inventory.setItem(slotofitem + 9, ItemButtonManager.ENABLED_EVENT);
                            }
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }
                    }
                    if (clickeditem.isSimilar(ItemButtonManager.RETURN)) {
                        player.openInventory(GUIManager.makeStartSettingsGUI(player, UHCAPI.isStarted));
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    } else if (clickeditem.isSimilar(ItemButtonManager.NEXT_PAGE)) {
                        //if (maxpagespossible > 0){
                        int pagesstored = plugin.EventGUIPages.get(player.getUniqueId());
                        int pages = pagesstored + 1;
                        if (plugin.isInCustomEventGUI.get(player.getUniqueId())) {
                            if (pages > maxpagespossible) {
                                pages = maxpagespossible;
                            }
                        } else if (plugin.isInEventGUI.get(player.getUniqueId())) {
                            if (pages > vanillalistsize) {
                                pages = vanillalistsize;
                            }
                        }
                        if (plugin.isInCustomEventGUI.get(player.getUniqueId())) {
                            player.openInventory(GUIManager.makeCustomEventsGUI(player, pages, api));
                            plugin.isInCustomEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        } else if (plugin.isInEventGUI.get(player.getUniqueId())) {
                            player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                            plugin.isInEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }
                        plugin.isInGUI.put(player.getUniqueId(), true);
                        //}
                    } else if (clickeditem.isSimilar(ItemButtonManager.PREVIOUS_PAGE)) {
                        //if (maxpagespossible > 0){
                        int pagesstored = plugin.EventGUIPages.get(player.getUniqueId());
                        int pages = pagesstored - 1;
                        if (pages <= 0) {
                            pages = 0;
                        }
                        if (plugin.isInCustomEventGUI.get(player.getUniqueId())) {
                            player.openInventory(GUIManager.makeCustomEventsGUI(player, pages, api));
                            plugin.isInCustomEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                        } else if (plugin.isInEventGUI.get(player.getUniqueId())) {
                            player.openInventory(GUIManager.makeEventsGUI(player, pages, api));
                            plugin.isInEventGUI.put(player.getUniqueId(), true);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                        }
                        plugin.isInGUI.put(player.getUniqueId(), true);
                        //}
                    } else if (clickeditem.isSimilar(ItemButtonManager.INSTALLED_EVENTS)) {
                        int pages = 0;
                        plugin.EventGUIPages.put(player.getUniqueId(), 0);
                        player.openInventory(GUIManager.makeCustomEventsGUI(player, pages, api));
                        plugin.isInGUI.put(player.getUniqueId(), true);
                        plugin.isInEventGUI.put(player.getUniqueId(), false);
                        plugin.isInCustomEventGUI.put(player.getUniqueId(), true);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    } else if (clickeditem.isSimilar(ItemButtonManager.UHC_SETTINGS)) {
                        player.openInventory(GUIManager.makeUHCSettingsGUI(player));
                        plugin.isInGUI.put(player.getUniqueId(), true);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1F);
                    } else if (clickeditem.isSimilar(ItemButtonManager.TIMING_SETTINGS)) {
                        player.openInventory(GUIManager.makeTimingSettingsGUI(player));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1F);
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    } else if (clickeditem.isSimilar(ItemButtonManager.BORDER_SETTINGS)) {
                        player.openInventory(GUIManager.makeBorderSettingsGUI(player));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1F);
                        plugin.isInGUI.put(player.getUniqueId(), true);
                    } else if (clickeditem.isSimilar(ItemButtonManager.START_UHC)) {
                        if (api.size() > 0) {
                            for (UHCPlugin uhcPlugin : api) {
                                uhcPlugin.start();
                            }
                        } else {
                            player.openInventory(GUIManager.makeStartSettingsGUI(player, UHCAPI.isStarted));
                            plugin.isInGUI.put(player.getUniqueId(), true);
                        }
                        Metrics metrics = new Metrics(plugin, 10659);
                        metrics.addCustomChart(new Metrics.SimplePie("noup", () -> "" + api.size()));
                        for (World world : Bukkit.getWorlds()) {
                            world.getWorldBorder().setSize(plugin.initialborder * 2);
                            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
                            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                        }

                        Random random = new Random();

                        int playerIndex = 0;
                        for (Player others : Bukkit.getOnlinePlayers()) {
                            others.setInvulnerable(true);
                            others.setHealth(others.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                            others.setFoodLevel(20);

                            BukkitRunnable timedTeleporter = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    double randomX = random.nextInt(plugin.initialborder / 2);
                                    double randomZ = random.nextInt(plugin.initialborder / 2);
                                    boolean flipX = random.nextBoolean();
                                    boolean flipZ = random.nextBoolean();
                                    randomX *= flipX ? -1 : 1;
                                    randomZ *= flipZ ? -1 : 1;
                                    Location teleportloc = others.getLocation();
                                    teleportloc.setX(randomX);
                                    teleportloc.setZ(randomZ);
                                    double y = others.getWorld().getHighestBlockYAt(teleportloc);
                                    teleportloc.setY(y);
                                    others.teleport(teleportloc);
                                    others.sendMessage(ChatColor.RED + "UHC has started!");
                                    others.getInventory().clear();
                                    others.setGameMode(GameMode.SURVIVAL);
                                    UHCAPI.livingPlayers.add(others);
                                }
                            };
                            timedTeleporter.runTaskLater(plugin, playerIndex);
                            playerIndex++;
                        }
                        UHCAPI.isStarted = true;
                        if (BuiltInEvents.customEventsBooleans.get(BuiltInEvents.ALWAYS_DAY)) {
                            for (World world : Bukkit.getWorlds()) {
                                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                                world.setTime(1000l);
                            }
                        }
                        // TODO: NPE if default world isn't called ''world''
                        STORED_DIFFICULTY = Bukkit.getWorld("world").getDifficulty();
                        if (!BuiltInEvents.customEventsBooleans.get(BuiltInEvents.LOSE_HUNGER) && !BuiltInEvents.customEventsBooleans.get(BuiltInEvents.HOSTILE_MOBS)) {
                            for (World world : Bukkit.getWorlds()) {
                                world.setDifficulty(Difficulty.PEACEFUL);
                            }
                        }
                        STORED_WEATHER = Bukkit.getWorld("world").getGameRuleValue(GameRule.DO_WEATHER_CYCLE);
                        for (World world : Bukkit.getWorlds()) {
                            world.setGameRule(GameRule.DO_WEATHER_CYCLE, BuiltInEvents.customEventsBooleans.get(BuiltInEvents.DO_WEATHER));
                            world.setClearWeatherDuration(100000000);
                        }
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    } else if (clickeditem.isSimilar(ItemButtonManager.GRACE_PERIOD)) {
                        plugin.timeEdited.put(player.getUniqueId(), 1);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "How long to do you want the invincibility period to last for? (In Seconds), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.invincibility);
                    } else if (clickeditem.isSimilar(ItemButtonManager.FINAL_HEAL)) {
                        plugin.timeEdited.put(player.getUniqueId(), 2);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "How far into the game should the final heal be? (In Seconds), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.finalheal);
                    } else if (clickeditem.isSimilar(ItemButtonManager.PVP)) {
                        plugin.timeEdited.put(player.getUniqueId(), 3);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "How far into the game should the PVP be? (In Minutes), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.pvpenable / 60);
                    } else if (clickeditem.isSimilar(ItemButtonManager.BORDER)) {
                        plugin.timeEdited.put(player.getUniqueId(), 4);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "How far into the game should the border shrink? (In Minutes), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.bordershrink / 60);
                    } else if (clickeditem.isSimilar(ItemButtonManager.MEETUP)) {
                        plugin.timeEdited.put(player.getUniqueId(), 5);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "How far into the game should the meetup be? (In Minutes), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.meetup / 60);
                    } else if (clickeditem.isSimilar(ItemButtonManager.INITIAL_BORDER)) {
                        plugin.timeEdited.put(player.getUniqueId(), 6);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "What should the radius of the initial border (first shrink) be? (In blocks), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.initialborder);
                    } else if (clickeditem.isSimilar(ItemButtonManager.BORDER_SHRINK)) {
                        plugin.timeEdited.put(player.getUniqueId(), 7);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "What should the radius of the border shrink border (second shrink) be? (In blocks), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.bordersize);
                    } else if (clickeditem.isSimilar(ItemButtonManager.MEETUP_BORDER)) {
                        plugin.timeEdited.put(player.getUniqueId(), 8);
                        player.closeInventory();
                        player.sendMessage(ChatColor.GOLD + "What should the radius of the meetup border (last/third shrink) be? (In blocks), type x to go back");
                        player.sendMessage(ChatColor.GOLD + "Currently is " + plugin.meetupborder);
                    } else if (clickeditem.isSimilar(ItemButtonManager.END_UHC)) {
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                        UHCStop.stopUHC(null, ChatColor.RED + "The host decided to stop the UHC! UHC has ended.");
                    }
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
