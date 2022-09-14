package com.gestankbratwurst.revenant.projectrevenant.metaprogression.levelsystem;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Converts minecraft experience to revenant experience
 */

public class MinecraftExpListener implements Listener {

    private static final double conversionRate = 1.0;

    @EventHandler
    public void onExperiencePickup(PlayerPickupExperienceEvent event) {
        RevenantPlayer player = RevenantPlayer.of(event.getPlayer());
        player.addExperience(event.getExperienceOrb().getExperience() * conversionRate);
        event.getExperienceOrb().remove();
        event.setCancelled(true);
    }

}