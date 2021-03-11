package me.swipez.uhccore.customevents;

import me.swipez.uhccore.api.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Locale;

public class BuiltInEvents implements Listener {
    public static boolean CUT_CLEAN = true;
    public static boolean TIMBER = true;
    public static boolean VEINMINER = false;
    public static boolean COOKED_MEAT = true;
    public static boolean ALWAYS_DAY = true;
    public static boolean ALWAYS_NIGHT = false;


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (TIMBER && UHCAPI.isStarted && (e.getBlock().getType().toString().toLowerCase().contains("_log"))) {
            Material heldMaterial = e.getPlayer().getInventory().getItemInMainHand().getType();
            Material brokenMaterial = e.getBlock().getType();
            if (heldMaterial.toString().toLowerCase().contains("_axe")
                    && brokenMaterial.toString().toLowerCase().contains("_log")) {
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType(), false);
            }
        }
        if (VEINMINER && UHCAPI.isStarted && e.getBlock().getType().toString().toLowerCase().contains("_ore") && e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("_pickaxe")) {
            if (e.getBlock().getType().equals(Material.IRON_ORE) || (e.getBlock().getType().equals(Material.GOLD_ORE) && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("stone_"))) {
                e.setDropItems(false);
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType(), CUT_CLEAN);
            } else {
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType(), false);
            }
        }
        if (CUT_CLEAN && UHCAPI.isStarted && e.getBlock().getType().toString().toLowerCase().contains("_ore") && e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("_pickaxe") && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("wooden_")) {
            if (e.getBlock().getType().equals(Material.IRON_ORE)) {
                e.setDropItems(false);
                dropCutClean(e.getBlock());
            } else if (e.getBlock().getType().equals(Material.GOLD_ORE) && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("stone_")) {
                e.setDropItems(false);
                dropCutClean(e.getBlock());
            }
        }

    }

    public void breakAdjacentWood(Location origin, Material woodMaterial, boolean cutClean) {
        BlockFace[] blockFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST,
                BlockFace.UP, BlockFace.DOWN };
        for (BlockFace face : blockFaces) {
            Block relative = origin.getBlock().getRelative(face);
            if (cutClean) {
                dropCutClean(origin.getBlock());
            } else {
                origin.getBlock().breakNaturally();
            }
            if (relative.getType() == woodMaterial) {
                breakAdjacentWood(relative.getLocation(), woodMaterial, cutClean);
            }
        }
    }

    public void dropCutClean(Block b) {
        if (b.getType().equals(Material.IRON_ORE)) {
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
        } else if (b.getType().equals(Material.GOLD_ORE)) {
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
        } else {
            b.breakNaturally();
        }
    }



    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && UHCAPI.isStarted && COOKED_MEAT) {
            for (ItemStack i : e.getDrops()) {
                if (i.getType().equals(Material.BEEF)) i.setType(Material.COOKED_BEEF);
                if (i.getType().equals(Material.RABBIT)) i.setType(Material.COOKED_RABBIT);
                if (i.getType().equals(Material.PORKCHOP)) i.setType(Material.COOKED_PORKCHOP);
                if (i.getType().equals(Material.MUTTON)) i.setType(Material.COOKED_MUTTON);
                if (i.getType().equals(Material.COD)) i.setType(Material.COOKED_COD);
                if (i.getType().equals(Material.SALMON)) i.setType(Material.COOKED_SALMON);
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