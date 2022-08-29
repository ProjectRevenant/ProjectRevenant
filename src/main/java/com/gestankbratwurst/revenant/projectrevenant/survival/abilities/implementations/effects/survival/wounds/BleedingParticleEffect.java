package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BleedingParticleEffect extends AbilityEffect<Player> {

  private static final transient BlockData REDSTONE_BLOCK_DATA = Bukkit.createBlockData(Material.REDSTONE_BLOCK);

  public BleedingParticleEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, "bleeding-particles");
  }

  @Override
  public void cast(Player element) {
    if(ThreadLocalRandom.current().nextDouble() <= 0.33) {
      playBleedingEffect(element);
    }
  }

  private void playBleedingEffect(Player player) {
    Location location = player.getLocation().add(0, 0.5, 0);
    player.getWorld().spawnParticle(Particle.BLOCK_DUST, location, 8, 0.5, 0.5, 0.5, REDSTONE_BLOCK_DATA);
  }

}
