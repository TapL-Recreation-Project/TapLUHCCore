package me.swipez.uhccore.uhclisteners;

import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onPlayerShearLeaves(BlockBreakEvent event){
        if (UHCAPI.isStarted){
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null){
                if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SHEARS) && event.getBlock().getType().toString().toLowerCase().contains("leaves")){
                    int random = (int) (Math.random()*100);
                    int percentage = 10;
                    if (random < percentage){
                        ItemStack apple = new ItemStack(Material.APPLE);
                        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), apple);
                    }
                }
            }
        }
    }
}
