package fr.loual.myplugin.horses;

import org.bukkit.entity.Horse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HorseManager {

    private final Map<UUID, HorseData> horses = new HashMap<>();

    public HorseData getData(Horse horse) {
        return horses.computeIfAbsent(
                horse.getUniqueId(),
                uuid -> new HorseData()
        );
    }

    public void remove(Horse horse) {
        horses.remove(horse.getUniqueId());
    }
}