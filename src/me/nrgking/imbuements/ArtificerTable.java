package me.nrgking.imbuements;

import me.nrgking.imbuements.ImbuementsMain;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

import static me.nrgking.imbuements.ImbuementsMain.confettikey;
import static me.nrgking.imbuements.ImbuementsMain.swiftkey;

public class ArtificerTable implements Listener {

    FileConfiguration c = ImbuementsMain.getPlugin().c;

    NamespacedKey freezekey = ImbuementsMain.freezekey;
    NamespacedKey poisonkey = ImbuementsMain.poisonkey;
    NamespacedKey glowkey = ImbuementsMain.glowkey;
    NamespacedKey swiftkey = ImbuementsMain.swiftkey;
    NamespacedKey witherkey = ImbuementsMain.witherkey;
    NamespacedKey powerkey = ImbuementsMain.powerkey;
    NamespacedKey speedkey = ImbuementsMain.speedkey;



    @EventHandler
    public void artificerCreation(BlockPlaceEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        if (!(e.getBlock().getType() == Material.BOOKSHELF || e.getBlock().getType() == Material.END_STONE_BRICK_SLAB || e.getBlock().getType() == Material.PURPUR_SLAB)) return;
        Block block = e.getBlock();
        Material up = block.getRelative(BlockFace.UP).getType();
        Player player = e.getPlayer();
        if (!(up == Material.PURPUR_SLAB || up == Material.END_STONE_BRICK_SLAB) || block.getRelative(BlockFace.DOWN).getType() == Material.BOOKSHELF) return;
        e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "As you place the final block, a wave of magic washes over your hand.");
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
        player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

    }


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
        if (!(block.getRelative(BlockFace.UP).getType() == (Material.PURPUR_SLAB) || (block.getRelative(BlockFace.UP).getType() == (Material.END_STONE_BRICK_SLAB)))) return;

        Material upblock = block.getRelative(BlockFace.UP).getType();
        Integer exp = player.getTotalExperience();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(data.has(powerkey, PersistentDataType.STRING) || data.has(speedkey, PersistentDataType.STRING) || data.has(freezekey, PersistentDataType.STRING) || data.has(poisonkey, PersistentDataType.STRING) || data.has(glowkey, PersistentDataType.STRING) || data.has(witherkey, PersistentDataType.STRING)) return;

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
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

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
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

            // wither spellbook
        } else if (item2.getType().equals(Material.COAL) && item2.getAmount() >= 32 && item.getAmount() == 1 && exp >= 900 && upblock == Material.PURPUR_SLAB && item.getType() == Material.BOOK) {
            ItemStack witheritem = new ItemStack(Material.BLACK_DYE, 1);
            ItemMeta withermeta = witheritem.getItemMeta();
            PersistentDataContainer witherdata = withermeta.getPersistentDataContainer();
            withermeta.setDisplayName(ChatColor.BLACK + "Void Fragment");
            witherdata.set(witherkey, PersistentDataType.STRING, "wither");
            witheritem.setItemMeta(withermeta);

            player.getEquipment().setItemInMainHand(witheritem);
            player.sendMessage(ChatColor.BLACK + "A spectral hand reaches out and grabs your wrist, dissolving the book within your hand.");
            item2.setAmount(item2.getAmount() - 32);
            player.giveExp(-900);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.ENTITY_VEX_CHARGE, 1.0f, 1.0f);

            //swift steak
            //made with a glowstone dust in main hand and sugar in off hand
            // (end stone slab)
        } else if (item2.getType().equals(Material.SUGAR) && item2.getAmount() >= 64 && item.getAmount() == 1 && exp >= 900 && upblock == Material.END_STONE_BRICK_SLAB && item.getType() == Material.GLOWSTONE_DUST) {
            List<String> lore = new ArrayList<>();
            ItemStack swiftredstone = new ItemStack(Material.SUGAR, 1);
            ItemMeta swiftmeta = swiftredstone.getItemMeta();
            PersistentDataContainer swiftdata = swiftmeta.getPersistentDataContainer();

            swiftmeta.setDisplayName(ChatColor.YELLOW + "Speed Enhancing Powder");
            lore.add(ChatColor.DARK_GRAY + "Would fit well on " + ChatColor.LIGHT_PURPLE + "steak.");
            swiftmeta.setLore(lore);

            swiftdata.set(swiftkey, PersistentDataType.STRING, "swift");
            swiftredstone.setItemMeta(swiftmeta);

            player.getEquipment().setItemInMainHand(swiftredstone);
            player.sendMessage(ChatColor.WHITE + "The sugar infuses into the dust, making a speedy-feeling mixture.");
            item2.setAmount(0);
            player.giveExp(-900);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

            //confetti book
        } else if (item2.getType().equals(Material.FIREWORK_STAR) && item2.getAmount() >= 4 && item.getAmount() == 1 && exp >= 600 && upblock == Material.PURPUR_SLAB && item.getType() == Material.BOOK) {
            ItemStack clickitem = new ItemStack(Material.BOOK, 1);
            ItemMeta clickmeta = clickitem.getItemMeta();
            PersistentDataContainer clickdata = clickmeta.getPersistentDataContainer();
            clickmeta.setDisplayName(ChatColor.BLUE + "Book of Joy");
            clickdata.set(confettikey, PersistentDataType.STRING, "confetti");
            clickitem.setItemMeta(clickmeta);

            player.getEquipment().setItemInMainHand(clickitem);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "The fireworks dissolve into paper, which gathers in the book.");
            item2.setAmount(item2.getAmount() - 4);
            player.giveExp(-600);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

            // power spellbook
        } else if (item2.getType().equals(Material.BLAZE_ROD) && item2.getAmount() >= 3 && item.getAmount() == 1 && exp >= 600 && upblock == Material.END_STONE_BRICK_SLAB && item.getType() == Material.BOOK) {
            ItemStack clickitem = new ItemStack(Material.BOOK, 1);
            ItemMeta clickmeta = clickitem.getItemMeta();
            PersistentDataContainer clickdata = clickmeta.getPersistentDataContainer();
            clickmeta.setDisplayName(ChatColor.RED + "Book of Power");
            clickdata.set(powerkey, PersistentDataType.STRING, "power");
            clickitem.setItemMeta(clickmeta);

            player.getEquipment().setItemInMainHand(clickitem);
            player.sendMessage(ChatColor.RED + "The energy in the blaze rods melt into the book.");
            item2.setAmount(item2.getAmount() - 3);
            player.giveExp(-600);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

            // speed spellbook
        } else if (item2.getType().equals(Material.GUNPOWDER) && item2.getAmount() >= 2 && item.getAmount() == 1 && exp >= 600 && upblock == Material.END_STONE_BRICK_SLAB && item.getType() == Material.BOOK) {
            ItemStack clickitem = new ItemStack(Material.BOOK, 1);
            ItemMeta clickmeta = clickitem.getItemMeta();
            PersistentDataContainer clickdata = clickmeta.getPersistentDataContainer();
            clickmeta.setDisplayName(ChatColor.YELLOW + "Book of Speed");
            clickdata.set(speedkey, PersistentDataType.STRING, "speed");
            clickitem.setItemMeta(clickmeta);

            player.getEquipment().setItemInMainHand(clickitem);
            player.sendMessage(ChatColor.YELLOW + "The gunpowder melts into the book, charging it with explosive speed.");
            item2.setAmount(item2.getAmount() - 2);
            player.giveExp(-600);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 20);
            player.playSound(block.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);

        }

    }
}
