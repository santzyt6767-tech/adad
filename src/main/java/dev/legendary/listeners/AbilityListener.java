package dev.legendary.listeners;

import dev.legendary.LegendaryPlugin;
import dev.legendary.items.LegendaryItems;
import dev.legendary.managers.CooldownManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AbilityListener implements Listener {

    private final LegendaryPlugin plugin;
    private final CooldownManager cd;

    private final Set<UUID> soulRendActive = new HashSet<>();
    private final Set<UUID> stormAuraActive = new HashSet<>();

    public AbilityListener(LegendaryPlugin plugin) {
        this.plugin = plugin;
        this.cd = plugin.getCooldownManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        var item = player.getInventory().getItemInMainHand();
        String id = LegendaryItems.getLegendaryId(item);
        if (id == null) return;

        event.setCancelled(true);

        boolean shift = player.isSneaking();

        switch (id) {
            case "VOIDBANE"     -> { if (shift) darkShroud(player); else
