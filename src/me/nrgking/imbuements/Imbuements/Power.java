package me.nrgking.imbuements.Imbuements;

import me.nrgking.imbuements.ImbuementsMain;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class Power implements Listener {

    NamespacedKey freezekey = ImbuementsMain.freezekey;
    NamespacedKey poisonkey = ImbuementsMain.poisonkey;
    NamespacedKey glowkey = ImbuementsMain.glowkey;
    NamespacedKey witherkey = ImbuementsMain.witherkey;
    NamespacedKey powerkey = ImbuementsMain.powerkey;

    FileConfiguration c = ImbuementsMain.getPlugin().c;

    @EventHandler
    public void PowerUse(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.HAND) return;
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType().equals(Material.BOOKSHELF)) return;
        }

        Player player = (Player) e.getPlayer();
        String playername = player.getName();

        ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer data = meta.getPersistentDataContainer();
        e.getPlayer().sendMessage("2");

        if (!(item.getType().equals(Material.DIAMOND_SWORD) || item.getType().equals(Material.NETHERITE_SWORD))) return;
        if (data.has(powerkey, PersistentDataType.STRING)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 1));
            e.getPlayer().sendMessage("3");
            if (!c.getBoolean("permanentimbuements")) {
                    data.remove(powerkey);
                    item.setItemMeta(meta);
            }
        }

    }


    @EventHandler
    public void PowerInteract(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.HAND) return;
        if (e.getClickedBlock() == null) return;
            if (!(e.getClickedBlock().getType().equals(Material.BOOKSHELF))) return;
            Player player = (Player) e.getPlayer();
            String playername = player.getName();

            ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            ItemStack item2 = player.getEquipment().getItemInOffHand();
            if (!(item == null && item2 == null)) {

                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer data = null;

                ItemMeta meta2 = item2.getItemMeta();
                PersistentDataContainer data2 = null;

                if(item2.getType() != Material.BOOK) return;
                if (meta2 != null && meta != null) {
                    data2 = meta2.getPersistentDataContainer();
                    if (data2.has(powerkey, PersistentDataType.STRING)) {
                        if (item.getType().equals(Material.DIAMOND_SWORD) || item.getType().equals(Material.NETHERITE_SWORD)) {
                            data = meta.getPersistentDataContainer();
                            if(data.has(powerkey, PersistentDataType.STRING) || data.has(freezekey, PersistentDataType.STRING) || data.has(poisonkey, PersistentDataType.STRING) || data.has(glowkey, PersistentDataType.STRING) || data.has(witherkey, PersistentDataType.STRING)) return;

                            if (c.getBoolean("logging")) {
                                Bukkit.getLogger().info(playername + " imbued the Power imbuement");
                            }

                            data.set(powerkey, PersistentDataType.STRING, "power");

                            item.setItemMeta(meta);

                            player.sendMessage(ChatColor.RED + "A red mist swirls around your weapon.");
                            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ(), 40);
                            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

                            player.getInventory().getItemInOffHand().setAmount(item2.getAmount() - 1);
                            e.setCancelled(true);
                        }

                    }
                }


            }
        }





    //end o class
}
