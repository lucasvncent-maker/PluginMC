package fr.loual.myplugin.listeners;

import fr.loual.myplugin.MyPlugin;
import fr.loual.myplugin.items.HorseApple;
import fr.loual.myplugin.horses.HorseData;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class HorseItemListener implements Listener {

    private final MyPlugin plugin;

    public HorseItemListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof Horse horse)) {
            return;
        }

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if (!HorseApple.isHorseApple(plugin, item)) {
            return;
        }

        HorseData data = plugin.getHorseManager().getData(horse);

        data.addJumpLevel(1);

        event.getPlayer().sendMessage(
                "§6Votre cheval a gagné +1 niveau de saut !"
        );

        item.setAmount(item.getAmount() - 1);
    }
}