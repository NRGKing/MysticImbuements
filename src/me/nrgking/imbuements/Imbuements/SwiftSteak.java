package me.nrgking.imbuements.Imbuements;

import me.nrgking.imbuements.ImbuementsMain;
import org.bukkit.*;
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

public class SwiftSteak implements Listener {

    NamespacedKey swiftkey = ImbuementsMain.swiftkey;

    FileConfiguration c = ImbuementsMain.getPlugin().c;


    @EventHandler
    public void SwiftSteakEat(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        if (player.getEquipment().getItemInMainHand() == null) return;
        ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = Objects.requireNonNull(meta).getPersistentDataContainer();
        if (data.has(swiftkey, PersistentDataType.STRING)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 3));

            data.remove(swiftkey);
            item.setItemMeta(meta);
        }
    }

    @EventHandler
    public void SwiftSteakCreation(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.HAND) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = (Player) e.getPlayer();
            String playername = player.getName();

            ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            ItemStack item2 = player.getEquipment().getItemInOffHand();
            if(item2.getItemMeta() == null) return;
            if(item2.getType() != Material.SUGAR) return;
            ItemMeta meta2 = item2.getItemMeta();
            if (item.getType().equals(Material.COOKED_BEEF) && meta2.getPersistentDataContainer().has(swiftkey, PersistentDataType.STRING) && item.getAmount() == 1) {
                {

                    ItemStack swiftitem = new ItemStack(Material.COOKED_BEEF, 1);
                    ItemMeta swiftmeta = swiftitem.getItemMeta();
                    PersistentDataContainer swiftdata = swiftmeta.getPersistentDataContainer();
                    swiftmeta.setDisplayName(ChatColor.GRAY + "Steak of Swiftness");
                    swiftdata.set(swiftkey, PersistentDataType.STRING, "swift");
                    swiftitem.setItemMeta(swiftmeta);

                    item2.setAmount(item2.getAmount() - 1);
                    player.getEquipment().setItemInMainHand(swiftitem);
                    player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ(), 40);
                    player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

                    if (c.getBoolean("logging")) {
                        Bukkit.getLogger().info(playername + " created a Steak of Swiftness");
                    }
                }
            }
        }
    }


}
