package fr.loual.myplugin.horses;
import org.bukkit.entity.Horse;
import org.bukkit.attribute.Attribute;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class HorseData {

    private static final double MAX_BASE_SPEED = 0.3375;
    private static final double MAX_BASE_JUMP = 1.0;
    private static final double MAX_BASE_HEALTH = 30.0;

    private double jumpLevel;
    private double speedLevel;
    private double healthLevel;
    private double scaleLevel;
    private boolean canFly;
    private double baseJumpStrength;
    private double baseSpeed;
    private double baseHealth;
    private Horse horse;

    public static final double MAX_JUMP_LEVEL = 9;
    public static final double MAX_SPEED_LEVEL = 9;
    public static final double MAX_HEALTH_LEVEL = 9;
    public static final double MAX_SCALE_LEVEL = 9;

    public HorseData(Horse horse) {
        this.healthLevel = 0;
        this.jumpLevel = 0;
        this.scaleLevel = 0;
        this.speedLevel = 0;
        this.baseJumpStrength = horse.getAttribute(Attribute.JUMP_STRENGTH).getBaseValue();
        this.baseSpeed = horse.getAttribute(Attribute.MOVEMENT_SPEED).getBaseValue();
        this.baseHealth = horse.getAttribute(Attribute.MAX_HEALTH).getBaseValue();
        this.canFly = false;
        this.horse = horse;
    }

    public double getHealthLevel() {
        return healthLevel;
    }

    public double getJumpLevel() {
        return jumpLevel;
    }

    public double getScaleLevel() {
        return scaleLevel;
    }

    public double getSpeedLevel() {
        return speedLevel;
    }

    public void addHealthLevel(double amount) {
        jumpLevel = Math.min(MAX_HEALTH_LEVEL, healthLevel + amount);
    }

    public void addJumpLevel(double amount) {
        jumpLevel = Math.min(MAX_JUMP_LEVEL, jumpLevel + amount);
    }

    public void addSpeedLevel(double amount) {
        speedLevel = Math.min(MAX_SPEED_LEVEL, speedLevel + amount);
    }

    public void addScaleLevel(double amount) {
        scaleLevel = Math.min(MAX_SCALE_LEVEL, scaleLevel + amount);
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public double getBaseJumpStrength() {
        return baseJumpStrength;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public double getBaseHealth() {
        return baseHealth;
    }

    public String getHorseName() {
        Component customName = this.horse.customName();
        if (customName == null) {
            return "Cheval";
        }
        return PlainTextComponentSerializer.plainText().serialize(customName);
    }

    public Component getResume() {
        return Component.text()
                .append(Component.text(
                        "._.-. " + getHorseName() + " .-._.\n",
                        NamedTextColor.GOLD
                ))
                .append(Component.text("- Vitesse de base: ", NamedTextColor.GRAY))
                .append(Component.text(
                        "%.4f/%.4f\n".formatted(getBaseSpeed(), MAX_BASE_SPEED),
                        NamedTextColor.WHITE
                ))
                .append(Component.text("- Saut de base: ", NamedTextColor.GRAY))
                .append(Component.text(
                        "%.4f/%.4f\n".formatted(getBaseJumpStrength(), MAX_BASE_JUMP),
                        NamedTextColor.WHITE
                ))
                .append(Component.text("- Points de vie de base: ", NamedTextColor.GRAY))
                .append(Component.text(
                        "%.1f/%.1f\n\n".formatted(getBaseHealth(), MAX_BASE_HEALTH),
                        NamedTextColor.WHITE
                ))
                .append(Component.text("Bonus:\n", NamedTextColor.GREEN))
                .append(Component.text("- Vitesse: ", NamedTextColor.GRAY))
                .append(Component.text(
                        "%.1f/9\n".formatted(getSpeedLevel()),
                        NamedTextColor.AQUA
                ))
                .append(Component.text("- Saut: ", NamedTextColor.GRAY))
                .append(Component.text(
                        "%.1f/9\n".formatted(getJumpLevel()),
                        NamedTextColor.AQUA
                ))
                .append(Component.text("- PV: ", NamedTextColor.GRAY))
                .append(Component.text(
                        "%.1f/9".formatted(getHealthLevel()),
                        NamedTextColor.AQUA
                ))
                .build();
    }
}