package me.nrgking.imbuements;

import me.nrgking.imbuements.Imbuements.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class ImbuementsMain extends JavaPlugin {

    public FileConfiguration c = getConfig();

    private static ImbuementsMain plugin;

    public static Glow glow;
    public static FireResFruit fireResFruit;
    public static Poison poison;
    public static Freeze freeze;
    public static SwiftSteak swiftSteak;
    public static Wither wither;
    public static Confetti confetti;

    public static NamespacedKey glowkey;
    public static NamespacedKey firereskey;
    public static NamespacedKey poisonkey;
    public static NamespacedKey freezekey;
    public static NamespacedKey swiftkey;
    public static NamespacedKey witherkey;
    public static NamespacedKey confettikey;

    @Override
    public void onEnable() {
        plugin = this;
        NamespacedKey glowkey = (new NamespacedKey(this, "glow"));
        NamespacedKey firereskey = (new NamespacedKey(this, "fireres"));
        NamespacedKey poisonkey = (new NamespacedKey(this, "poison"));
        NamespacedKey freezekey = (new NamespacedKey(this, "freeze"));
        NamespacedKey swiftkey = (new NamespacedKey(this, "swift"));
        NamespacedKey witherkey = (new NamespacedKey(this, "wither"));
        NamespacedKey confettikey = (new NamespacedKey(this, "confetti"));

        this.glowkey = glowkey;
        this.poisonkey = poisonkey;
        this.firereskey = firereskey;
        this.freezekey = freezekey;
        this.swiftkey = swiftkey;
        this.witherkey = witherkey;
        this.confettikey = confettikey;


        this.getServer().getPluginManager().registerEvents(new Glow(), this );

        this.getServer().getPluginManager().registerEvents(new FireResFruit(), this);

        this.getServer().getPluginManager().registerEvents(new Poison(), this );

        this.getServer().getPluginManager().registerEvents(new Freeze(), this);

        this.getServer().getPluginManager().registerEvents(new SwiftSteak(), this);

        this.getServer().getPluginManager().registerEvents(new ArtificerTable(), this);

        this.getServer().getPluginManager().registerEvents(new Wither(), this);

        this.getServer().getPluginManager().registerEvents(new Confetti(), this);

        c.addDefault("logging", true);
        c.addDefault("poisonduration", 100);
        c.addDefault("glowingduration", 100);
        c.addDefault("permanentimbuements", false);
        c.addDefault("freezeticks", 140);
        c.addDefault("witherduration", 40);
        c.options().copyDefaults(true);
        saveConfig();
    }

    public static ImbuementsMain getPlugin(){
        return plugin;
    }
}
