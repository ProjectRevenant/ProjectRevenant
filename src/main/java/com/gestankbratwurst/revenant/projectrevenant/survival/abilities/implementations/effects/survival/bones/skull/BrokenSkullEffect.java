package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.skull;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BrokenSkullEffect extends AbilityEffect<Player> {
  public BrokenSkullEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, "broken-skull-effect");
  }

  @Override
  public void cast(Player element) {
    element.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35, 0));
  }
}
