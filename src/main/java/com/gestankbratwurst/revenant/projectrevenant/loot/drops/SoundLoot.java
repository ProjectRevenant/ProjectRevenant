package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class SoundLoot implements Loot {

  private final Sound sound;
  private final float volume;
  private final float pitch;

  @Override
  public void applyTo(Player looter, Location location) {
    looter.playSound(location, sound, volume, pitch);
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    applyTo(looter, looter.getLocation());
  }
}
