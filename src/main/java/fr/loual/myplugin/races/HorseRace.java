package fr.loual.myplugin.races;

import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.System;

import fr.loual.myplugin.races.HorseRaceConfig;


public class HorseRace {

    private final Player player;
    private final Horse horse;
    private final Location returnLocation;
    private final long startTime;
    private final HorseRaceConfig config;

    private int nextObstacle = 0;

    public HorseRace(Player player, Horse horse, Location returnLocation,  HorseRaceConfig config) {
        this.player = player;
        this.horse = horse;
        this.returnLocation = returnLocation;
        this.startTime = System.currentTimeMillis();
        this.config = config;
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

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public HorseRaceConfig getConfig() {
        return config;
    }
}