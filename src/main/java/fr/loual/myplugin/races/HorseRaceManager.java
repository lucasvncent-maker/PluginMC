package fr.loual.myplugin.races;

import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.loual.myplugin.MyPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HorseRaceManager {

    private final MyPlugin plugin;

    private final Map<UUID, HorseRace> activeRaces = new HashMap<>();

    public HorseRaceManager(MyPlugin plugin) {
        this.plugin = plugin;
    }

    public void startRace(Player player, int raceId) {

        if (activeRaces.containsKey(player.getUniqueId())) {
            player.sendMessage("§cVous êtes déjà dans une course !");
            return;
        }

        if (raceId != 1) {
            player.sendMessage("§cCette course n'existe pas.");
            return;
        }

        World world = Bukkit.getWorld("horse_race_01");

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

        Location start = new Location(world, 0.5, 2, 0.5);

        player.teleport(start);

        HorseRace race = new HorseRace(
                player,
                horse,
                returnLocation
        );

        activeRaces.put(
                player.getUniqueId(),
                race
        );

        player.sendMessage("§6§lCOURSE !");
        player.sendMessage("§eFranchissez un maximum d'obstacles !");
    }

    private Horse findPlayerHorse(Player player) {

        if (player.getVehicle() instanceof Horse horse) {
            return horse;
        }

        return null;
    }
}