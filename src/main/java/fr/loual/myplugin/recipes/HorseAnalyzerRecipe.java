package fr.loual.myplugin.recipes;

import fr.loual.myplugin.items.HorseAnalyzer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class HorseAnalyzerRecipe {

    public static void register(JavaPlugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "horse_analyzer");

        ItemStack result = HorseAnalyzer.create(plugin);

        ShapedRecipe recipe = new ShapedRecipe(key, result);

        recipe.shape(
                " H ",
                "ASA",
                " H "
        );

        recipe.setIngredient('H', Material.HAY_BLOCK);
        recipe.setIngredient('S', Material.SPYGLASS);
        recipe.setIngredient('A', Material.APPLE);

        Bukkit.addRecipe(recipe);
    }
}