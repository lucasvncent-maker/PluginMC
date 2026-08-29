package fr.loual.myplugin.races;

import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.loual.myplugin.MyPlugin;
import fr.loual.myplugin.races.HorseRaceConfig;
import fr.loual.myplugin.items.RacePass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class HorseRaceManager {

    private final MyPlugin plugin;
    private final Map<UUID, HorseRace> activeRaces = new HashMap<>();
    private final Map<Integer, HorseRaceConfig> raceConfigs = new HashMap<>();

    public HorseRaceManager(MyPlugin plugin) {
        this.plugin = plugin;

        saveRaceResources();
        loadRaceConfigs();
    }

    public boolean startRace(Player player,  int raceId) {

        if (activeRaces.containsKey(player.getUniqueId())) {
            player.sendMessage("§cVous êtes déjà dans une course !");
            return false;
        }

        World world = Bukkit.getWorld("horse_races");

        if (world == null) {
            player.sendMessage("§cLe monde de la course n'est pas chargé.");
            return false;
        }

        Location returnLocation = player.getLocation().clone();
        Horse horse = findPlayerHorse(player);

        if (horse == null) {
            player.sendMessage("§cVous devez être à cheval.");
            return false;
        }

        HorseRaceConfig config = raceConfigs.get(raceId);

        if (config == null) {
            player.sendMessage("§cCette course n'existe pas.");
            return false;
        }

        Location start = config.getStartLocation(world);
        
        horse.removePassenger(player);
        player.teleport(start);
        horse.teleport(start);
        horse.addPassenger(player);

        HorseRace race = new HorseRace(player, horse, returnLocation, config,  raceId);

        activeRaces.put(player.getUniqueId(), race);

        player.sendMessage("§eAtteignez la ligne d'arrivée le plus rapidement possible !");
        return true;
    }

    public void stopRace(Player player) {
        HorseRace race = activeRaces.get(player.getUniqueId());

        if (race == null) {
            player.sendMessage("§cVous n'êtes pas dans une course.");
            return;
        }

        finishRace(race, "§cVous avez abandonné la course.", 0);
    }

    private void finishRace(HorseRace race, String message, double raceTime) {
        Player player = race.getPlayer();
        Horse horse = race.getHorse();
        
        HorseRaceConfig config = race.getConfig();
        int raceId = race.getRaceId();

        if (raceTime != 0 && raceTime <= config.getMaxTime()) {
            HorseRaceConfig nextConfig = raceConfigs.get(raceId + 1);
            if (nextConfig != null) {
                ItemStack nextPass = RacePass.create(plugin, raceId + 1, nextConfig.getName());
                nextPass.setAmount(5);
                player.getInventory().addItem(nextPass);
            }
        }

        activeRaces.remove(player.getUniqueId());

        horse.removePassenger(player);
        horse.teleport(race.getReturnLocation());
        player.teleport(race.getReturnLocation());
        horse.addPassenger(player);

        if (message != null) {
            player.sendMessage(message);
        }
    }

    private Horse findPlayerHorse(Player player) {

        if (player.getVehicle() instanceof Horse horse) {
            return horse;
        }

        return null;
    }

    public void tick() {

        for (HorseRace race : new ArrayList<>(activeRaces.values())) {

            Player player = race.getPlayer();
            Horse horse = race.getHorse();

            HorseRaceConfig config = race.getConfig();

            if (!player.isOnline()) {
                finishRace(race, "Player disconnected, end of the race !", 0);
                continue;
            }

            if (player.getLocation().getY() < 0) {
                finishRace(race,"§cVous êtes tombé du parcours !", 0);
                continue;
            }
            
            long elapsed = race.getElapsedTime();
            double seconds = elapsed / 1000.0;

            if (horse.getLocation().getZ() >= config.getFinishZ()) {
                finishRace(race, "§6§lARRIVÉE ! §eTemps : " + String.format("%.2f", seconds) + " secondes", seconds);
                continue;
            }
        }
    }

    public void handlePlayerQuit(Player player) {

        HorseRace race = activeRaces.get(player.getUniqueId());

        if (race == null) {
            return;
        }
        Horse horse = race.getHorse();
        horse.removePassenger(player);
        horse.teleport(race.getReturnLocation());
        player.teleport(race.getReturnLocation());
        horse.addPassenger(player);

        activeRaces.remove(player.getUniqueId());
    }

    private void loadRaceConfigs() {

        File racesFolder = new File(plugin.getDataFolder(), "races");

        if (!racesFolder.exists()) {
            racesFolder.mkdirs();
        }

        File[] files = racesFolder.listFiles((dir, name) -> name.endsWith(".yml"));

        if (files == null) {
            return;
        }

        for (File file : files) {

            try {
                String fileName = file.getName();

                int raceId = Integer.parseInt(fileName.substring(0, fileName.length() - 4));

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                String name = config.getString("name", "Course");

                double startX = config.getDouble("start_x");
                double startY = config.getDouble("start_y");
                double startZ = config.getDouble("start_z");

                double finishZ = config.getDouble("finish_z");
                double maxTime = config.getDouble("max_time", 30.0);

                HorseRaceConfig raceConfig = new HorseRaceConfig(name, startX, startY, startZ, finishZ, maxTime);

                raceConfigs.put(raceId, raceConfig);

                plugin.getLogger().info("[MyPlugin] Course " + raceId + " chargée : " + name);

            } catch (NumberFormatException e) {
                plugin.getLogger().warning("[MyPlugin] Fichier de course ignoré : " + file.getName()  + " (le nom doit être un nombre)");
            }
        }
    }

    private void saveRaceResources() {

        for (int i = 1; ; i++) {

            String resourcePath = "races/" + i + ".yml";

            if (plugin.getResource(resourcePath) == null) {
                break;
            }
            
            // TODO Set to false when all well configured
            plugin.saveResource(resourcePath, true);

            plugin.getLogger().info("[MyPlugin] Course copiée : " + resourcePath);
        }
    }
}