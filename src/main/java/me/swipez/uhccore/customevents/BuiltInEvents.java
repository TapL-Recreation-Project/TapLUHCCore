package me.swipez.uhccore.customevents;

import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;


public class BuiltInEvents implements Listener {


    public static ItemStack CUT_CLEAN = ItemBuilder.of(Material.IRON_INGOT, 1).name(ChatColor.GOLD+"Cutclean").build();
    public static ItemStack TIMBER = ItemBuilder.of(Material.DIAMOND_AXE, 1).name(ChatColor.BLUE+"Timber").build();
    public static ItemStack VEINMINER = ItemBuilder.of(Material.GOLDEN_PICKAXE, 1).name(ChatColor.WHITE+"Veinminer").build();
    public static ItemStack COOKED_MEAT = ItemBuilder.of(Material.COOKED_BEEF, 1).name(ChatColor.RED+"Cooked meat").build();
    public static ItemStack ALWAYS_DAY = ItemBuilder.of(Material.CLOCK, 1).name(ChatColor.YELLOW+"Always day").build();
    public static ItemStack HOSTILE_MOBS = ItemBuilder.of(Material.ZOMBIE_HEAD, 1).name(ChatColor.RED+"Hostile mobs").build();
    public static ItemStack LOSE_HUNGER = ItemBuilder.of(Material.POISONOUS_POTATO, 1).name(ChatColor.GREEN+"Lose hunger").build();
    public static ItemStack DO_WEATHER = ItemBuilder.of(Material.WATER_BUCKET, 1).name(ChatColor.DARK_PURPLE+"Always clear weather").build();
    public static ItemStack HASTEY_BOIS = ItemBuilder.of(Material.STONE_PICKAXE, 1).name(ChatColor.GRAY+"Hastey Bois").enchantment(Enchantment.DIG_SPEED, 1).enchantment(Enchantment.DURABILITY, 1).build();
    public static Map<ItemStack, Boolean> customEventsBooleans = new HashMap<>();
    static {
        customEventsBooleans.put(CUT_CLEAN, true);
        customEventsBooleans.put(TIMBER, true);
        customEventsBooleans.put(VEINMINER, false);
        customEventsBooleans.put(COOKED_MEAT, false);
        customEventsBooleans.put(ALWAYS_DAY, true);
        customEventsBooleans.put(HOSTILE_MOBS, false);
        customEventsBooleans.put(LOSE_HUNGER, false);
        customEventsBooleans.put(DO_WEATHER, false);
        customEventsBooleans.put(HASTEY_BOIS, false);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!UHCAPI.isStarted) {
            return;
        }
        if (customEventsBooleans.get(TIMBER) && (e.getBlock().getType().toString().toLowerCase().contains("_log"))) {
            Material heldMaterial = e.getPlayer().getInventory().getItemInMainHand().getType();
            Material brokenMaterial = e.getBlock().getType();
            if (heldMaterial.toString().toLowerCase().contains("_axe")
                    && brokenMaterial.toString().toLowerCase().contains("_log")) {
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType(), false);
            }
        }
        if (customEventsBooleans.get(VEINMINER) && e.getBlock().getType().toString().toLowerCase().contains("_ore") && e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("_pickaxe")) {
            if (e.getBlock().getType().equals(Material.IRON_ORE) || (e.getBlock().getType().equals(Material.GOLD_ORE) && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("stone_"))) {
                e.setDropItems(false);
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType(), customEventsBooleans.get(CUT_CLEAN));
            } else {
                breakAdjacentWood(e.getBlock().getLocation(), e.getBlock().getType(), false);
            }
        }
        if (customEventsBooleans.get(CUT_CLEAN) && e.getBlock().getType().toString().toLowerCase().contains("_ore") && e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("_pickaxe") && !e.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("wooden_")) {
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
        if (e.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && UHCAPI.isStarted && customEventsBooleans.get(COOKED_MEAT) && e.getEntity().getLastDamageCause() != null) {
            for (ItemStack i : e.getDrops()) {
                if (i.getType().equals(Material.BEEF)) i.setType(Material.COOKED_BEEF);
                if (i.getType().equals(Material.RABBIT)) i.setType(Material.COOKED_RABBIT);
                if (i.getType().equals(Material.PORKCHOP)) i.setType(Material.COOKED_PORKCHOP);
                if (i.getType().equals(Material.MUTTON)) i.setType(Material.COOKED_MUTTON);
                if (i.getType().equals(Material.COD)) i.setType(Material.COOKED_COD);
                if (i.getType().equals(Material.SALMON)) i.setType(Material.COOKED_SALMON);
                if (i.getType().equals(Material.CHICKEN)) i.setType(Material.COOKED_CHICKEN);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (UHCAPI.isStarted && !customEventsBooleans.get(LOSE_HUNGER)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (UHCAPI.isStarted && !customEventsBooleans.get(HOSTILE_MOBS) && (e.getEntity() instanceof Monster)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (UHCAPI.isStarted && customEventsBooleans.get(HASTEY_BOIS) &&
                EnchantmentTarget.TOOL.includes(e.getInventory().getResult().getType())) {
            ItemStack itemStack = e.getInventory().getResult();
            ItemMeta meta = itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
            itemStack.setItemMeta(meta);
        }
    }
}
