package fr.loual.myplugin.races;

import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.entity.Player;



public class HorseRace {

    private final Player player;
    private final Horse horse;
    private final Location returnLocation;

    private int prestige = 0;
    private int nextObstacle = 0;

    public HorseRace(
            Player player,
            Horse horse,
            Location returnLocation
    ) {
        this.player = player;
        this.horse = horse;
        this.returnLocation = returnLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public Horse getHorse() {
        return horse;
    }

    public Location getReturnLocation() {
        return returnLocation;
    }

    public int getPrestige() {
        return prestige;
    }
}