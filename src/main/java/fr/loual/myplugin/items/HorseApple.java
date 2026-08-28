package fr.loual.myplugin.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class HorseApple {

    private static final String ITEM_ID = "horse_apple";

    public static ItemStack create(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.APPLE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
            Component.text("Pomme de croissance", NamedTextColor.GOLD)
        );

        NamespacedKey key = new NamespacedKey(plugin, ITEM_ID);
        meta.getPersistentDataContainer().set(
                key,
                PersistentDataType.BYTE,
                (byte) 1
        );

        item.setItemMeta(meta);

        return item;
    }

    public static boolean isHorseApple(JavaPlugin plugin, ItemStack item) {
        if (item == null || item.getType() != Material.APPLE) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        NamespacedKey key = new NamespacedKey(plugin, ITEM_ID);

        return meta.getPersistentDataContainer().has(
                key,
                PersistentDataType.BYTE
        );
    }
}