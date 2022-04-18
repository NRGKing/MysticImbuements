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

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

import static me.nrgking.imbuements.ImbuementsMain.swiftkey;

public class ArtificerTable implements Listener {

    FileConfiguration c = ImbuementsMain.getPlugin().c;

    NamespacedKey freezekey = ImbuementsMain.freezekey;
    NamespacedKey poisonkey = ImbuementsMain.poisonkey;
    NamespacedKey glowkey = ImbuementsMain.glowkey;

    NamespacedKey swiftkey = ImbuementsMain.swiftkey;

    @EventHandler
    public void artificerUse(PlayerInteractEvent e) {


        if (e.getHand() == EquipmentSlot.HAND) return;
        if (e.getClickedBlock() == null) return;
        if (e.getPlayer().getEquipment().getItemInMainHand() == null || e.getPlayer().getEquipment().getItemInOffHand() == null)
            return;


        Player player = e.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        ItemStack item2 = player.getEquipment().getItemInOffHand();
        Block block = e.getClickedBlock();
        if (!(block.getType().equals(Material.BOOKSHELF))) return;
        if (!(item.getType().equals(Material.BOOK) || item.getType().equals(Material.GLOWSTONE_DUST))) return;
        if (!(block.getRelative(BlockFace.UP).getType() == (Material.PURPUR_SLAB) || (block.getRelative(BlockFace.UP).getType() == (Material.END_STONE_BRICK_SLAB))))
            ;

        Material upblock = block.getRelative(BlockFace.UP).getType();
        Integer exp = player.getTotalExperience();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        //glow spellbook
        if (item2.getType().equals(Material.GLOWSTONE_DUST) && item2.getAmount() >= 8 && item.getAmount() == 1 && exp >= 700 && upblock == Material.PURPUR_SLAB && item.getType() == Material.BOOK) {
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
        } else if (item2.getType().equals(Material.KELP) && item2.getAmount() >= 32 && item.getAmount() == 1 && exp >= 900 && upblock == Material.PURPUR_SLAB && item.getType() == Material.BOOK) {
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

        } else if (item2.getType().equals(Material.SUGAR) && item2.getAmount() >= 64 && item.getAmount() == 1 && exp >= 900 && upblock == Material.END_STONE_BRICK_SLAB && item.getType() == Material.GLOWSTONE_DUST) {
            List<String> lore = new ArrayList<>();
            ItemStack swiftredstone = new ItemStack(Material.SUGAR, 1);
            ItemMeta swiftmeta = swiftredstone.getItemMeta();
            PersistentDataContainer swiftdata = swiftmeta.getPersistentDataContainer();

            swiftmeta.setDisplayName(ChatColor.YELLOW + "Speed Enhancing Spice");
            lore.add(ChatColor.DARK_GRAY + "Would fit well on " + ChatColor.LIGHT_PURPLE + "steak.");
            swiftmeta.setLore(lore);

            swiftdata.set(swiftkey, PersistentDataType.STRING, "swift");
            swiftredstone.setItemMeta(swiftmeta);

            player.getEquipment().setItemInMainHand(swiftredstone);
            player.sendMessage(ChatColor.WHITE + "The sugar infuses into the dust, making a speedy-feeling mixture.");
            item2.setAmount(0);
            player.giveExp(-900);
        }


    }
}
