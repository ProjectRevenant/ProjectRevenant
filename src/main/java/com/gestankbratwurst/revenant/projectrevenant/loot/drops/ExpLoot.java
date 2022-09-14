package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.ToIntFunction;

@AllArgsConstructor
public class ExpLoot implements Loot {

  private final ToIntFunction<Player> experienceFunction;

  @Override
  public void applyTo(Player looter, Location location) {
    int expAmount = experienceFunction.applyAsInt(looter);
    RevenantPlayer.of(looter).addExperience(expAmount);
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    applyTo(looter, looter.getLocation());
  }
}