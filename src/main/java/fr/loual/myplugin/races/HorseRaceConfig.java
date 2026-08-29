package fr.loual.myplugin.races;

import org.bukkit.Location;
import org.bukkit.World;

public class HorseRaceConfig {

    private final String name;

    private final double startX;
    private final double startY;
    private final double startZ;

    private final double finishZ;
    private final double maxTime;

    public HorseRaceConfig(
            String name,
            double startX,
            double startY,
            double startZ,
            double finishZ,
            double maxTime
    ) {
        this.name = name;

        this.finishZ = finishZ;
        this.maxTime = maxTime;

        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public String getName() {
        return name;
    }

    public double getFinishZ() {
        return finishZ;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public Location getStartLocation(World world) {
        return new Location(world, startX, startY, startZ, 0, 0);
    }

     public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getStartZ() {
        return startZ;
    }
}