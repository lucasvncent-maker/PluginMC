package fr.loual.myplugin.listeners;
import java.util.concurrent.ThreadLocalRandom;

import fr.loual.myplugin.MyPlugin;

import fr.loual.myplugin.items.VigorApple;
import fr.loual.myplugin.items.HorseAnalyzer;

import fr.loual.myplugin.horses.HorseData;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.event.player.PlayerInteractEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.Bukkit;
import org.bukkit.util.RayTraceResult;

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

        if (VigorApple.isVigorApple(plugin, item)) {
            onVigorApple(plugin, horse, event, item);
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (event.getHand() == EquipmentSlot.HAND && HorseAnalyzer.isHorseAnalyzer(plugin, item) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            onHorseAnalyzer(plugin, event, item); 
        }

    }

    public void onVigorApple(MyPlugin plugin, Horse horse, PlayerInteractEntityEvent event, ItemStack item) {
        HorseData data = plugin.getHorseManager().getData(horse);

        item.setAmount(item.getAmount() - 1);
        if (data.getJumpLevel() == data.MAX_JUMP_LEVEL) {
            event.getPlayer().sendMessage("§6Votre cheval a déjà atteint le niveau de saut maximal !");
            return;
        }
        
        data.addJumpLevel(1);
        plugin.getHorseManager().applyStats(horse);

        event.getPlayer().sendMessage("§6Votre cheval a gagné +1 niveau de saut !");
    }


    public void onHorseAnalyzer(MyPlugin plugin, PlayerInteractEvent event, ItemStack item) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!player.isHandRaised()) {
                return;
            }

            RayTraceResult result = player.getWorld().rayTraceEntities(
                    player.getEyeLocation(),
                    player.getEyeLocation().getDirection(),
                    100,
                    entity -> entity instanceof Horse
            );

            if (result == null || !(result.getHitEntity() instanceof Horse horse)) {
                return;
            }
            
            Horse observedHorse = (Horse) result.getHitEntity();
            onHorseFound(plugin, observedHorse, event, item);

        }, 1L);
    }

    public void onHorseFound(MyPlugin plugin, Horse horse, PlayerInteractEvent event, ItemStack item) {
        HorseData data = plugin.getHorseManager().getData(horse);
        Component horseResume = data.getResume();

        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        event.getPlayer().sendMessage(horseResume);

        if (ThreadLocalRandom.current().nextDouble() < 0.03) {
            EquipmentSlot hand = event.getHand();
            ItemStack handItem = event.getPlayer().getInventory().getItem(hand);

            if (handItem.getAmount() <= 1) {
                event.getPlayer().getInventory().setItem(hand, null);
            } else {
                handItem.setAmount(handItem.getAmount() - 1); 
            }

            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            event.getPlayer().sendMessage(Component.text("Pas de chance, votre ").append(handItem.getItemMeta().displayName()).append(Component.text(" s'est cassé...")));
        }  
    }
}