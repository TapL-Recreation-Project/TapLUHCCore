package me.swipez.uhccore;

import me.swipez.uhccore.api.UHCAPI;
import me.swipez.uhccore.bstats.Metrics;
import me.swipez.uhccore.commands.UHCGUICommand;
import me.swipez.uhccore.customevents.BuiltInEvents;
import me.swipez.uhccore.itembuttons.ItemButtonManager;
import me.swipez.uhccore.runnables.BorderDivide;
import me.swipez.uhccore.runnables.TimeDecrease;
import me.swipez.uhccore.runnables.TimeRunOut;
import me.swipez.uhccore.uhclisteners.ChatEditListener;
import me.swipez.uhccore.uhclisteners.DeathListener;
import me.swipez.uhccore.uhclisteners.JoinListener;
import me.swipez.uhccore.uhclisteners.PVPStop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class UHCCore extends JavaPlugin {

    public static boolean meetupdone = false;
    public static Metrics metrics;
    public static UHCCore plugin;

    // GUI Hashmaps
    public HashMap<UUID, Boolean> isInGUI = new HashMap<>();
    public HashMap<UUID, Boolean> isInEventGUI = new HashMap<>();
    public HashMap<UUID, Boolean> isInCustomEventGUI = new HashMap<>();
    public HashMap<UUID, Integer> EventGUIPages = new HashMap<>();

    //Time hashmap
    public HashMap<UUID, Integer> timeEdited = new HashMap<>();

    // Stage times
    public static int invincibility = 30;
    public static int finalheal = 60;
    public static int pvpenable = 1200;
    public static int bordershrink = 1800;
    public static int meetup = 2100;
    public static int borderdivide = 120;


    //Border sizes
    public static int initialborder = 2000;
    public static int bordersize = 1000;
    public static int meetupborder = 500;







    @Override
    public void onEnable() {
        plugin = this;



        //Command
        getCommand("uhcmenu").setExecutor(new UHCGUICommand(this));

        //Listeners
        getServer().getPluginManager().registerEvents(new BuiltInEvents(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        getServer().getPluginManager().registerEvents(new PVPStop(this), this);
        getServer().getPluginManager().registerEvents(new ChatEditListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        //Runnables
        BukkitTask TimeDecrease = new TimeDecrease(this).runTaskTimer(this, 20, 20);
        BukkitTask TimeRunOut = new TimeRunOut(this).runTaskTimer(this, 20, 20);
        BukkitTask BorderDivide = new BorderDivide(this).runTaskTimer(this, 20, 20);
        //Recipe
        registerGoldenHeadRecipe(this, "golden_head_recipe", new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), new ItemStack(Material.APPLE, 1));

    }

    @Override
    public void onDisable() {
        UHCAPI.pluginList.clear();
    }

    public static void init() {

        invincibility = 30;
        finalheal = 60;
        pvpenable = 1200;
        bordershrink = 1800;
        meetup = 2100;
        borderdivide = 120;

        initialborder = 2000;
        bordersize = 1000;
        meetupborder = 500;
    }

    @SuppressWarnings("deprecation")
    private static void registerGoldenHeadRecipe(UHCCore plugin, String id, ItemStack result, ItemStack ingredient) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, id), result)
                .shape("AAA", "AHA", "AAA")
                .setIngredient('A', new RecipeChoice.ExactChoice(ingredient))
                .setIngredient('H', new RecipeChoice.ExactChoice(ItemButtonManager.getGoldenHead()));
        Bukkit.addRecipe(recipe);
    }
}
