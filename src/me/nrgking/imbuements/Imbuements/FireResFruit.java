package me.nrgking.imbuements.Imbuements;

import me.nrgking.imbuements.ImbuementsMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class FireResFruit implements Listener {

    NamespacedKey firereskey = ImbuementsMain.firereskey;

    FileConfiguration c = ImbuementsMain.getPlugin().c;


    @EventHandler
    public void FresFruitEat(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        if (player.getEquipment().getItemInMainHand() != null) {
            ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();

            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = Objects.requireNonNull(meta).getPersistentDataContainer();


            if (data.has(firereskey, PersistentDataType.STRING)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 8000, 1));

                        data.remove(firereskey);
                        item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void FresCreation(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.HAND) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = (Player) e.getPlayer();
            String playername = player.getName();

            ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            ItemStack item2 = player.getEquipment().getItemInOffHand();
            if (item.getType().equals(Material.APPLE) && item2.getType().equals(Material.MAGMA_CREAM) && item.getAmount() == 1 && player.getLocation().getBlock().getBiome() == Biome.NETHER_WASTES) {
                {
                    ItemStack fresitem = new ItemStack(Material.APPLE, 1);
                    ItemMeta fresmeta = fresitem.getItemMeta();
                    PersistentDataContainer fresdata = fresmeta.getPersistentDataContainer();
                    fresmeta.setDisplayName(ChatColor.DARK_RED + "Scorched Apple");
                    fresdata.set(firereskey, PersistentDataType.STRING, "fireres");
                    fresitem.setItemMeta(fresmeta);

                    item2.setAmount(item2.getAmount() - 1);
                    player.getEquipment().setItemInMainHand(fresitem);
                    if (c.getBoolean("logging")) {
                        Bukkit.getLogger().info(playername + " created a Scorched Apple");
                    }
                }
            }
        }
    }


}
