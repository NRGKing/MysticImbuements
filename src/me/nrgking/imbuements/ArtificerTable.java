package me.nrgking.imbuements;

import me.nrgking.imbuements.ImbuementsMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ArtificerTable implements Listener {

    FileConfiguration c = ImbuementsMain.getPlugin().c;

    NamespacedKey freezekey = ImbuementsMain.freezekey;
    NamespacedKey poisonkey = ImbuementsMain.poisonkey;
    NamespacedKey glowkey = ImbuementsMain.glowkey;

    @EventHandler
    public void artificerUse(PlayerInteractEvent e) {


        if (e.getHand() == EquipmentSlot.HAND) return;
        if (e.getClickedBlock() == null) return;
        if (e.getPlayer().getEquipment().getItemInMainHand() == null || e.getPlayer().getEquipment().getItemInOffHand() == null) return;


        Player player = e.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        ItemStack item2 = player.getEquipment().getItemInOffHand();
        Block block = e.getClickedBlock();
        if (!(block.getType().equals(Material.BOOKSHELF) && block.getRelative(BlockFace.UP).getType() == (Material.PURPUR_SLAB))) return;
        if (!(item.getType().equals(Material.BOOK))) return;

        Integer exp = player.getTotalExperience();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        //glow spellbook
        if(item2.getType().equals(Material.GLOWSTONE_DUST) && item2.getAmount() >= 8 && item.getAmount() == 1 && exp >= 700) {
            ItemStack glowitem = new ItemStack(Material.BOOK, 1);
            ItemMeta glowmeta = glowitem.getItemMeta();
            PersistentDataContainer glowdata = glowmeta.getPersistentDataContainer();
            glowmeta.setDisplayName("§dGlowing Spellbook");
            glowdata.set(glowkey, PersistentDataType.STRING, "glow");
            glowitem.setItemMeta(glowmeta);

            player.getEquipment().setItemInMainHand(glowitem);
            player.sendMessage(ChatColor.YELLOW + "The book in your main hand glows with magic.");
            item2.setAmount(item2.getAmount() - 8);
            player.giveExp(-700);

        //poison spellbook
        } else if (item2.getType().equals(Material.KELP) && item2.getAmount() >= 32 && item.getAmount() == 1 && exp >= 900){
            ItemStack poisonitem = new ItemStack(Material.GREEN_DYE, 1);
            ItemMeta poisonmeta = poisonitem.getItemMeta();
            PersistentDataContainer poisondata = poisonmeta.getPersistentDataContainer();
            poisonmeta.setDisplayName(ChatColor.GREEN + "Putrid Dust");
            poisondata.set(poisonkey, PersistentDataType.STRING, "poison");
            poisonitem.setItemMeta(poisonmeta);

            player.getEquipment().setItemInMainHand(poisonitem);
            player.sendMessage(ChatColor.GREEN + "The book in your main hand dissolves into repulsive sludge.");
            item2.setAmount(item2.getAmount() - 32);
            player.giveExp(-900);

        }
    }


}
