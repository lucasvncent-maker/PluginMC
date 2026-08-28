package fr.loual.myplugin.horses;

public class HorseData {

    private double jumpLevel;
    private double speedLevel;
    private double scaleLevel;

    private boolean canFly;

    public HorseData() {
        this.jumpLevel = 0;
        this.speedLevel = 0;
        this.scaleLevel = 0;
        this.canFly = false;
    }

    public double getJumpLevel() {
        return jumpLevel;
    }

    public void addJumpLevel(double amount) {
        jumpLevel += amount;
    }

    public double getSpeedLevel() {
        return speedLevel;
    }

    public void addSpeedLevel(double amount) {
        speedLevel += amount;
    }

    public double getScaleLevel() {
        return scaleLevel;
    }

    public void addScaleLevel(double amount) {
        scaleLevel += amount;
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }
}