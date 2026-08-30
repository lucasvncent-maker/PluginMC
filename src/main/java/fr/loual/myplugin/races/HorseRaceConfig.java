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

    private final boolean accepts_divine_armor;

    private final double min_jump_strength;
    private final double max_jump_strength;
    private final double min_speed;
    private final double max_speed;


    public HorseRaceConfig(String name, double startX, double startY, double startZ, double finishZ, double maxTime, boolean accepts_divine_armor, 
        double min_jump_strength, double max_jump_strength, double min_speed, double max_speed) {
        this.name = name;

        this.finishZ = finishZ;
        this.maxTime = maxTime;

        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;

        this.accepts_divine_armor = accepts_divine_armor;
        this.min_jump_strength = min_jump_strength;
        this.max_jump_strength = max_jump_strength;
        this.min_speed = min_speed;
        this.max_speed = max_speed;
    }

    public boolean getAcceptsDivineArmor() {
        return accepts_divine_armor;
    }

    public double getMinJumpStrength() {
        return min_jump_strength;
    }

    public double getMaxJumpStrength() {
        return max_jump_strength;
    }

    public double getMinSpeed() {
        return min_speed;
    }

    public double getMaxSpeed() {
        return max_speed;
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