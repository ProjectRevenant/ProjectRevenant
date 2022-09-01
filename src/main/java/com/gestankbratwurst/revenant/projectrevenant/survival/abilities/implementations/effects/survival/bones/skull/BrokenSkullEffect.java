package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.skull;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class BrokenSkullEffect extends AbilityEffect<Player> {
  public BrokenSkullEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND);
  }

  @Override
  public void cast(Player element) {
    element.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0));
  }
}
