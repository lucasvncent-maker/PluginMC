package fr.loual.myplugin.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Coords implements CommandExecutor {

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

        Location location = player.getLocation();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        player.sendMessage(
                player.getName()
                        + " se trouve aux coordonnées "
                        + x + " " + y + " " + z + " !"
        );

        return true;
    }
}