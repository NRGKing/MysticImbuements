package me.nrgking.imbuements;

import me.nrgking.imbuements.Imbuements.FireResFruit;
import me.nrgking.imbuements.Imbuements.Glow;
import me.nrgking.imbuements.Imbuements.Poison;
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

    public static NamespacedKey glowkey;
    public static NamespacedKey firereskey;
    public static NamespacedKey poisonkey;

    @Override
    public void onEnable() {
        plugin = this;
        NamespacedKey glowkey = (new NamespacedKey(this, "glow"));
        NamespacedKey firereskey = (new NamespacedKey(this, "fireres"));
        NamespacedKey poisonkey = (new NamespacedKey(this, "poison"));

        this.glowkey = glowkey;
        this.poisonkey = poisonkey;
        this.firereskey = firereskey;


        this.getServer().getPluginManager().registerEvents(new Glow(), this );
        ItemStack glowitem = new ItemStack(Material.BOOK, 1);
        ItemMeta glowmeta = glowitem.getItemMeta();
        PersistentDataContainer glowdata = glowmeta.getPersistentDataContainer();
        glowmeta.setDisplayName("§dGlowing Spellbook");
        glowdata.set(glowkey, PersistentDataType.STRING, "glow");
        glowitem.setItemMeta(glowmeta);
        NamespacedKey glowrecipekey = new NamespacedKey(this, "glowrecipekey");
        ShapedRecipe glowrecipe = new ShapedRecipe(glowrecipekey, glowitem);
        glowrecipe.shape("WEW", "GCG", "WEW");
        glowrecipe.setIngredient('E', Material.GOLD_BLOCK);
        glowrecipe.setIngredient('W', Material.GLOW_BERRIES);
        glowrecipe.setIngredient('G', Material.GOLDEN_CARROT);
        glowrecipe.setIngredient('C', Material.BOOK);
        Bukkit.addRecipe(glowrecipe);

        this.getServer().getPluginManager().registerEvents(new FireResFruit(), this);

        this.getServer().getPluginManager().registerEvents(new Poison(), this );
        ItemStack poisonitem = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta poisonmeta = poisonitem.getItemMeta();
        PersistentDataContainer poisondata = poisonmeta.getPersistentDataContainer();
        poisonmeta.setDisplayName(ChatColor.GREEN + "Putrid Rub");
        poisondata.set(poisonkey, PersistentDataType.STRING, "poison");
        poisonitem.setItemMeta(poisonmeta);
        NamespacedKey poisonrecipekey = new NamespacedKey(this, "poisonrecipekey");
        ShapedRecipe poisonrecipe = new ShapedRecipe(poisonrecipekey, poisonitem);
        poisonrecipe.shape("WEW", "GCG", "WEW");
        poisonrecipe.setIngredient('E', Material.EMERALD_BLOCK);
        poisonrecipe.setIngredient('W', Material.MOSS_BLOCK);
        poisonrecipe.setIngredient('G', Material.KELP);
        poisonrecipe.setIngredient('C', Material.LIGHT_GRAY_DYE);
        Bukkit.addRecipe(poisonrecipe);

        c.addDefault("logging", true);
        c.addDefault("poisonduration", 100);
        c.addDefault("glowingduration", 100);
        c.options().copyDefaults(true);
        saveConfig();
    }

    public static ImbuementsMain getPlugin(){
        return plugin;
    }
}
