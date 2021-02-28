package me.swipez.uhccore.api;

import org.bukkit.inventory.ItemStack;

public interface UHCPlugin {
    public void stop();
    public void start();
    public String getName();
    public String getAuthor();
    public ItemStack getDisplayStack();
}
