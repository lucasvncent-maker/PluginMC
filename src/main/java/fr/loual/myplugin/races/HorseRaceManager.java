package fr.loual.myplugin.races;

import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.loual.myplugin.MyPlugin;
import fr.loual.myplugin.races.HorseRaceConfig;

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

        plugin.saveResource("races/1.yml", false);

        loadRaceConfigs();
    }

    public void startRace(Player player,  int raceId) {

        if (activeRaces.containsKey(player.getUniqueId())) {
            player.sendMessage("§cVous êtes déjà dans une course !");
            return;
        }

        World world = Bukkit.getWorld("horse_races");

        if (world == null) {
            player.sendMessage("§cLe monde de la course n'est pas chargé.");
            return;
        }

        Location returnLocation = player.getLocation().clone();
        Horse horse = findPlayerHorse(player);

        if (horse == null) {
            player.sendMessage("§cVous devez être à cheval.");
            return;
        }

        HorseRaceConfig config = raceConfigs.get(raceId);

        if (config == null) {
            player.sendMessage("§cCette course n'existe pas.");
            return;
        }

        Location start = config.getStartLocation(world);
        
        horse.removePassenger(player);
        player.teleport(start);
        horse.teleport(start);
        horse.addPassenger(player);

        HorseRace race = new HorseRace(
                player,
                horse,
                returnLocation,
                config
        );

        activeRaces.put(
                player.getUniqueId(),
                race
        );

        player.sendMessage("§6§lCOURSE !");
        player.sendMessage("§eFranchissez un maximum d'obstacles !");
    }

    public void stopRace(Player player) {

        HorseRace race = activeRaces.get(player.getUniqueId());

        if (race == null) {
            player.sendMessage("§cVous n'êtes pas dans une course.");
            return;
        }

        finishRace(race, "§cVous avez abandonné la course.");
    }

    private void finishRace(HorseRace race, String message) {

        Player player = race.getPlayer();
        Horse horse = race.getHorse();

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
                finishRace(race, "Player disconnected, end of the race !");
                continue;
            }

            if (player.getLocation().getY() < 0) {
                finishRace(race,"§cVous êtes tombé du parcours !");
                continue;
            }
            
            long elapsed = race.getElapsedTime();
            double seconds = elapsed / 1000.0;

            if (horse.getLocation().getZ() >= config.getFinishZ()) {
                finishRace(race, "§6§lARRIVÉE ! §eTemps : " + String.format("%.2f", seconds) + " secondes");
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

    // TODO MAJ en fonction du numéro de course
    private void loadRaceConfigs() {

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "races/1.yml"));

        String name = config.getString("name", "Course");
        double finishZ = config.getDouble("finish_z", 250);
        double maxTime = config.getDouble("max_time", 30.0);

        double startX = config.getDouble("start_x");
        double startY = config.getDouble("start_y");
        double startZ = config.getDouble("start_z");

        HorseRaceConfig raceConfig = new HorseRaceConfig(name, startX, startY, startZ, finishZ, maxTime);

        raceConfigs.put(1, raceConfig);
    }
}