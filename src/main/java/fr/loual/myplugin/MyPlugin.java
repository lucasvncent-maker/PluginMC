package fr.loual.myplugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.loual.myplugin.commands.Coords;
import fr.loual.myplugin.commands.Elisa;

import fr.loual.myplugin.recipes.VigorAppleRecipe;
import fr.loual.myplugin.recipes.HorseAnalyzerRecipe;
import fr.loual.myplugin.recipes.HasteBambooRecipe;

import fr.loual.myplugin.horses.HorseManager;

import fr.loual.myplugin.listeners.HorseItemListener;

public class MyPlugin extends JavaPlugin {
    private HorseManager horseManager = new HorseManager();

    @Override
    public void onEnable() {
        
        getCommand("coords").setExecutor(new Coords());
        getCommand("elisa").setExecutor(new Elisa());

        VigorAppleRecipe.register(this);
        HorseAnalyzerRecipe.register(this);
        HasteBambooRecipe.register(this);

        getServer().getPluginManager().registerEvents(
                new HorseItemListener(this),
                this
        );


        getLogger().info("MyPlugin est activé !");

    }

    public HorseManager getHorseManager() {
        return horseManager;
    }
}