package fr.loual.myplugin.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Elisa implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande doit être exécutée par un joueur.");
            return true;
        }

        player.sendMessage(
                player.getName() + " dit qu'Elisa est la plus belle des petites mimi !"
        );

        return true;
    }
}