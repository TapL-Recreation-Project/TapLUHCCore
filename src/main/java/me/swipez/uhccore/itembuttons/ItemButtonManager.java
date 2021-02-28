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
            .name(ChatColor.GRAY+"Custom Events")
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
}
