package fr.loual.myplugin;

import fr.loual.myplugin.commands.Coords;
import fr.loual.myplugin.commands.Elisa;
import fr.loual.myplugin.recipes.HorseAppleRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("coords").setExecutor(new Coords());
        getCommand("elisa").setExecutor(new Elisa());

        HorseAppleRecipe.register(this);

        getLogger().info("MyPlugin est activé !");

    }
}