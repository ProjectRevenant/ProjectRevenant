package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.implementations;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.ConsumablePotionBuff;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ConsumableSpeedBuff extends ConsumablePotionBuff {

  public ConsumableSpeedBuff() {
    this(0, 0);
  }

  public ConsumableSpeedBuff(int duration, int amplifier) {
    super(new PotionEffect(PotionEffectType.SPEED, duration, amplifier, false, false, true));
  }

  @Override
  public String getPlainTextName() {
    return "Geschwindigkeit";
  }
}
