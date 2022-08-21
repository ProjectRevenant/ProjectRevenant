package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.ToIntFunction;

@AllArgsConstructor
public class ExpLoot implements Loot {

  private final ToIntFunction<Player> experienceFunction;

  @Override
  public void applyTo(Player looter, Location location) {
    int expAmount = experienceFunction.applyAsInt(looter);
    location.getWorld().spawn(location, ExperienceOrb.class, orb -> orb.setExperience(expAmount));
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    int expAmount = experienceFunction.applyAsInt(looter);
    looter.giveExp(expAmount);
  }
}