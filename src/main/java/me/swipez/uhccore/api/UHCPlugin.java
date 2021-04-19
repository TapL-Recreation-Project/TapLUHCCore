package me.swipez.uhccore.api;

import org.bukkit.inventory.ItemStack;

public interface UHCPlugin {
    void stop();
    void start();
    String getName();
    String getAuthor();
    ItemStack getDisplayStack();
}
