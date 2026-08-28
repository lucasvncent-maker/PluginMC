package fr.loual.myplugin.commands;

import fr.loual.myplugin.MyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartRace implements CommandExecutor {

    private final MyPlugin plugin;

    public StartRace(MyPlugin plugin) {
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

        if (args.length != 1) {
            player.sendMessage("§cUsage : /start_race <id>");
            return true;
        }

        int raceId;

        try {
            raceId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cL'identifiant doit être un nombre.");
            return true;
        }

        plugin.getHorseRaceManager().startRace(player, raceId);

        return true;
    }
}