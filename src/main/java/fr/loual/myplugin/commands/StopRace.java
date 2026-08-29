package fr.loual.myplugin.commands;

import fr.loual.myplugin.MyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopRace implements CommandExecutor {

    private final MyPlugin plugin;

    public StopRace(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        plugin.getHorseRaceManager().stopRace(player);

        return true;
    }
}