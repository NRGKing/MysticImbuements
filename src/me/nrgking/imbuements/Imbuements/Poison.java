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

public class Poison implements Listener {

    NamespacedKey freezekey = ImbuementsMain.freezekey;
    NamespacedKey poisonkey = ImbuementsMain.poisonkey;
    NamespacedKey glowkey = ImbuementsMain.glowkey;

    FileConfiguration c = ImbuementsMain.getPlugin().c;


    @EventHandler
    public void PoisonHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                if (e.getEntity() instanceof Player); {
                    if (player.getEquipment().getItemInMainHand() != null) {
                        LivingEntity victim = (LivingEntity) e.getEntity();


                        ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();

                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                            PersistentDataContainer data = Objects.requireNonNull(meta).getPersistentDataContainer();
                            Integer duration = c.getInt("poisonduration");
                            if (data.has(poisonkey, PersistentDataType.STRING)) {
                                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, 0));

                                if (!c.getBoolean("permanentimbuements")) { data.remove(poisonkey); }
                                item.setItemMeta(meta);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void PoisonInteract(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.HAND) return;
        if (e.getAction() == e.getAction().RIGHT_CLICK_AIR) {
            Player player = (Player) e.getPlayer();
            String playername = player.getName();

            ItemStack item = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            ItemStack item2 = player.getEquipment().getItemInOffHand();
            if (!(item == null && item2 == null)) {

                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer data = null;
                ItemMeta meta2 = item2.getItemMeta();

                PersistentDataContainer data2 = null;
                if(item2.getType() != Material.GREEN_DYE) return;
                if (meta2 != null && meta != null) {
                    data2 = meta2.getPersistentDataContainer();
                    if (data2.has(poisonkey, PersistentDataType.STRING)) {
                        if (item.getType().equals(Material.DIAMOND_SWORD) || item.getType().equals(Material.NETHERITE_SWORD)) {
                            data = meta.getPersistentDataContainer();
                            if(data.has(freezekey, PersistentDataType.STRING) || data.has(poisonkey, PersistentDataType.STRING) || data.has(glowkey, PersistentDataType.STRING)) return;

                            if (c.getBoolean("logging")) {
                                Bukkit.getLogger().info(playername + " imbued the Poison imbuement");
                            }

                            data.set(poisonkey, PersistentDataType.STRING, "poison");

                            item.setItemMeta(meta);

                            player.sendMessage(ChatColor.GREEN + "Your weapon drips with poisonous liquid.");
                            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ(), 40);
                            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

                            player.getInventory().getItemInOffHand().setAmount(item2.getAmount() - 1);
                            e.setCancelled(true);
                        }

                    }
                }


            }
        }
    }





    //end o class
}


