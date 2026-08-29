package fr.loual.myplugin.listeners;

import fr.loual.myplugin.MyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class HorseRaceListener implements Listener {

    private final MyPlugin plugin;

    public HorseRaceListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        plugin.getHorseRaceManager().handlePlayerQuit(
                event.getPlayer()
        );
    }
}