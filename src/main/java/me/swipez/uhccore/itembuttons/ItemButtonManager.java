package me.swipez.uhccore.itembuttons;

import me.swipez.uhccore.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemButtonManager {

    public static final ItemStack START_UHC = ItemBuilder.of(Material.GOLD_BLOCK)
            .name(ChatColor.YELLOW+"Start UHC")
            .build();
    public static final ItemStack END_UHC = ItemBuilder.of(Material.REDSTONE_BLOCK)
            .name(ChatColor.RED+"End UHC")
            .build();
    public static final ItemStack EVENT_MODIFIERS = ItemBuilder.of(Material.FURNACE)
            .name(ChatColor.GRAY+"BUILT-IN EVENTS")
            .build();
    public static final ItemStack PLACE_HOLDER_ITEM = ItemBuilder.of(Material.STONE)
            .name(ChatColor.GREEN+"Place Holder Item")
            .build();
    public static final ItemStack RETURN = ItemBuilder.of(Material.BARRIER)
            .name(ChatColor.YELLOW+"Return to previous page")
            .build();
    public static final ItemStack DISABLED_EVENT = ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
            .name(ChatColor.RED+"DISABLED")
            .build();
    public static final ItemStack ENABLED_EVENT = ItemBuilder.of(Material.GREEN_STAINED_GLASS_PANE)
            .name(ChatColor.GREEN+"ENABLED")
            .build();
    public static final ItemStack NEXT_PAGE = ItemBuilder.of(Material.ARROW)
            .name(ChatColor.RED+"NEXT PAGE")
            .build();
    public static final ItemStack PREVIOUS_PAGE = ItemBuilder.of(Material.ARROW)
            .name(ChatColor.RED+"PREVIOUS PAGE")
            .build();
    public static final ItemStack INSTALLED_EVENTS = ItemBuilder.of(Material.EMERALD_BLOCK)
            .name(ChatColor.GREEN+"INSTALLED EVENTS")
            .build();
    public static final ItemStack UHC_SETTINGS = ItemBuilder.of(Material.IRON_BLOCK)
            .name(ChatColor.GOLD+"UHC SETTINGS")
            .build();
    public static final ItemStack BORDER_SETTINGS = ItemBuilder.of(Material.BARRIER)
            .name(ChatColor.YELLOW+"BORDER SETTINGS")
            .build();
    public static final ItemStack TIMING_SETTINGS = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.BLUE+"TIMING SETTINGS")
            .build();
    public static final ItemStack GRACE_PERIOD = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.YELLOW+"GRACE PERIOD")
            .build();
    public static final ItemStack FINAL_HEAL = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.YELLOW+"FINAL HEAL")
            .build();
    public static final ItemStack PVP = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.YELLOW+"PVP")
            .build();
    public static final ItemStack BORDER = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.YELLOW+"BORDER")
            .build();
    public static final ItemStack MEETUP = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.YELLOW+"MEETUP")
            .build();
    public static final ItemStack TIME_DISPLAY = ItemBuilder.of(Material.CLOCK)
            .name(ChatColor.BLUE+"[]")
            .build();
}
