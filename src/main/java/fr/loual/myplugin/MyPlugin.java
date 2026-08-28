package fr.loual.myplugin;

import fr.loual.myplugin.commands.Coords;
import fr.loual.myplugin.commands.Elisa;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("coords").setExecutor(new Coords());
        getCommand("elisa").setExecutor(new Elisa());

        getLogger().info("MyPlugin est activé !");

    }
}