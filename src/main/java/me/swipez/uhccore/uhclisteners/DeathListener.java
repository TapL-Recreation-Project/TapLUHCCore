package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.UHCCore;
import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.itembuttons.ItemButtonManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    UHCCore plugin;

    public DeathListener(UHCCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        if (UHCAPI.isStarted){
            Player player = event.getEntity();
            Location location = player.getLocation();
            player.getWorld().strikeLightningEffect(location);
            if (player.getKiller() != null){
                Player killer = player.getKiller();
                player.getWorld().dropItem(location, ItemButtonManager.getGoldenHead().clone());
                Bukkit.broadcastMessage(ChatColor.RED + player.getDisplayName() + " died by the hands of " + killer.getDisplayName());
            }
            UHCAPI.deadPlayers.add(player);
            UHCAPI.livingPlayers.remove(player);
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        if (UHCAPI.isStarted){
            if (getAlivePlayerCount() == 1) {
                Player winner = null;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                        winner = player;
                    }
                }
                UHCStop.stopUHC(winner, "UHC has ended!");
            } else {
                final Player respawning = event.getPlayer();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        respawning.setGameMode(GameMode.SPECTATOR);
                    }
                }.runTaskLater(plugin, 1);
            }
        }
    }

    public Integer getAlivePlayerCount() {
        int alivePlayers = 0;
        for (Player others : Bukkit.getOnlinePlayers()){
            if (others.getGameMode().equals(GameMode.SURVIVAL)){
                alivePlayers++;
            }
        }
        return alivePlayers;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        ItemStack clickeditem = e.getPlayer().getInventory().getItemInMainHand();
        if (clickeditem.getItemMeta() != null && clickeditem.getItemMeta().getDisplayName().equals(ItemButtonManager.getGoldenHead().getItemMeta().getDisplayName()) && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {

            if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && !e.getPlayer().getGameMode().equals(GameMode.SPECTATOR))
                clickeditem.setAmount(clickeditem.getAmount() - 1);
            PotionEffect absorption = new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1);
            PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 200, 2);

            e.getPlayer().addPotionEffect(absorption);
            e.getPlayer().addPotionEffect(regen);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);

            e.setCancelled(true);
        }
    }
}
