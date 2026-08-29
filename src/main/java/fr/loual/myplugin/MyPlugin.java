package fr.loual.myplugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.loual.myplugin.commands.Coords;
import fr.loual.myplugin.commands.Elisa;
import fr.loual.myplugin.commands.StartRace;
import fr.loual.myplugin.commands.StopRace;

import fr.loual.myplugin.recipes.VigorAppleRecipe;
import fr.loual.myplugin.recipes.HorseAnalyzerRecipe;
import fr.loual.myplugin.recipes.HasteBambooRecipe;
import fr.loual.myplugin.recipes.HealthyGrassRecipe;
import fr.loual.myplugin.recipes.DivineArmorRecipe;
import org.bukkit.Bukkit;

import fr.loual.myplugin.horses.HorseManager;
import fr.loual.myplugin.races.HorseRaceManager;

import fr.loual.myplugin.listeners.HorseItemListener;
import fr.loual.myplugin.listeners.HorseRaceListener;

import org.bukkit.World;
import org.bukkit.entity.Horse;
import org.bukkit.WorldCreator;

public class MyPlugin extends JavaPlugin {
    private final HorseManager horseManager = new HorseManager();
    private HorseRaceManager horseRaceManager = new HorseRaceManager(this);

    @Override
    public void onEnable() {

        getCommand("coords").setExecutor(new Coords());
        getCommand("elisa").setExecutor(new Elisa());
        getCommand("start_race").setExecutor(new StartRace(this));
        getCommand("stop_race").setExecutor(new StopRace(this));

        VigorAppleRecipe.register(this);
        HorseAnalyzerRecipe.register(this);
        HasteBambooRecipe.register(this);
        HealthyGrassRecipe.register(this);
        DivineArmorRecipe.register(this);

        getServer().getPluginManager().registerEvents(new HorseItemListener(this), this);
        getServer().getPluginManager().registerEvents(new HorseRaceListener(this), this);


        getLogger().info("MyPlugin est activé !");

        Bukkit.getScheduler().runTaskTimer(
            this,
            () -> {
                for (World world : getServer().getWorlds()) {
                    for (Horse horse : world.getEntitiesByClass(Horse.class)) {
                        horseManager.tickFlyingHorse(this, horse);
                    }
                }
            },
            0L,
            1L
        );

        Bukkit.getScheduler().runTaskTimer(
                this,
                () -> horseRaceManager.tick(),
                0L,
                1L
        );

        WorldCreator creator = new WorldCreator("horse_races");
        World raceWorld = creator.createWorld();

        if (raceWorld == null) {
            getLogger().severe("Impossible de charger horse_races !");
        } else {
            getLogger().info("Monde horse_races chargé !");
        }

    }

    public HorseManager getHorseManager() {
        return horseManager;
    }

    public HorseRaceManager getHorseRaceManager() { return horseRaceManager; }

}