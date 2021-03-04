package me.swipez.uhccore.customevents;

import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;

public class BuiltInEvents implements Listener {
    public static boolean CUT_CLEAN = true;
    public static boolean TIMBER = true;
    public static boolean VEINMINER = false;
    public static boolean COOKED_MEAT = true;
    public static boolean ALWAYS_DAY = true;


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (TIMBER && UHCAPI.isStarted && (e.getBlock().getType().toString().toLowerCase().contains("_log"))) {
            Material heldMaterial = e.getPlayer().getInventory().getItemInMainHand().getType();
            Material brokenMaterial = e.getBlock().getType();
            if (heldMaterial.toString().toLowerCase().contains("_axe")
                    && brokenMaterial.toString().toLowerCase().contains("_log")) {
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType());
            }
        }
        if (CUT_CLEAN && UHCAPI.isStarted && e.getBlock().getType().toString().toLowerCase().contains("_ore") && e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("_pickaxe") && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("wooden_")) {
            if (e.getBlock().getType().equals(Material.IRON_ORE)) {
                e.setDropItems(false);
                e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
            } else if (e.getBlock().getType().equals(Material.GOLD_ORE) && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("stone_")) {
                e.setDropItems(false);
                e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
            }
        }
    }

    public void breakAdjacentWood(Location origin, Material woodMaterial) {
        BlockFace[] blockFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST,
                BlockFace.UP, BlockFace.DOWN };
        for (BlockFace face : blockFaces) {
            Block relative = origin.getBlock().getRelative(face);
            origin.getBlock().breakNaturally();
            if (relative.getType() == woodMaterial) {
                breakAdjacentWood(relative.getLocation(), woodMaterial);
            }
        }
    }
}


/*
int minutes = 0;
while (x >= 60) {
    x -= 60;
    minutes++
}

 */