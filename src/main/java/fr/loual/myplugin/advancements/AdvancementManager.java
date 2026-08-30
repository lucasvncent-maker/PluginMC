package fr.loual.myplugin.advancements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class AdvancementManager {

    private final JavaPlugin plugin;

    public AdvancementManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void installDatapack() {
        File datapack = new File(
                Bukkit.getWorldContainer(),
                "world/datapacks/myplugin"
        );

        copy("datapack", datapack);
    }

    public void award(Player player, String advancementId) {

        NamespacedKey key = new NamespacedKey(plugin, advancementId);
        Advancement advancement = Bukkit.getAdvancement(key);

        if (advancement == null) {
            plugin.getLogger().warning("Advancement introuvable : " + key);
            return;
        }

        AdvancementProgress progress = player.getAdvancementProgress(advancement);

        for (String criterion : progress.getRemainingCriteria()) {
            progress.awardCriteria(criterion);
        }
    }

    public void discoverRecipe(Player player, String recipeId) {
        NamespacedKey key = new NamespacedKey(plugin, recipeId);
        player.discoverRecipe(key);
    }

    private void copy(String resourcePath, File destination) {
        try {
            destination.mkdirs();

            var resources = plugin.getClass()
                    .getClassLoader()
                    .getResources(resourcePath);

            while (resources.hasMoreElements()) {
                var url = resources.nextElement();

                if (url.getProtocol().equals("jar")) {
                    try (var jar = ((java.net.JarURLConnection) url.openConnection()).getJarFile()) {

                        jar.stream()
                                .filter(e -> e.getName().startsWith(resourcePath + "/"))
                                .forEach(e -> {
                                    try {
                                        String relative = e.getName()
                                                .substring(resourcePath.length() + 1);

                                        if (relative.isEmpty()) return;

                                        File target = new File(destination, relative);

                                        if (e.isDirectory()) {
                                            target.mkdirs();
                                        } else {
                                            target.getParentFile().mkdirs();

                                            try (InputStream in = jar.getInputStream(e)) {
                                                Files.copy(
                                                        in,
                                                        target.toPath(),
                                                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                                                );
                                            }
                                        }
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                });
                    }
                }
            }

        } catch (Exception e) {
            plugin.getLogger().severe(
                    "Impossible d'installer le datapack : " + e.getMessage()
            );
        }
    }
}

