package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumablePotionEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.List;

public abstract class ConsumablePotionBuff extends TimedAbility implements Mergeable<ConsumablePotionBuff> {

  public ConsumablePotionBuff() {
    this(new PotionEffect(PotionEffectType.SPEED, 0, 1, false, false, false));
  }

  public ConsumablePotionBuff(PotionEffect effect) {
    this.addEffect(new ConsumablePotionEffect(effect));
    this.setDurationFromNow(Duration.ofDays(36500));
  }

  public void startEffect() {
    Duration duration = Duration.ofSeconds(getEffect().getDuration() / 20);
    this.setDurationFromNow(duration);
  }

  public PotionEffect getEffect() {
    return getEffect(ConsumablePotionEffect.class).getPotionEffect();
  }

  @Override
  public boolean shouldDisplayInTab() {
    return true;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return false;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    Duration timeLeft = this.getTimeLeft();
    if (timeLeft.getSeconds() <= 60) {
      return Component.text("§9Potion-Effekt (" + timeLeft.getSeconds() + "s)");
    }

    return Component.text("§9Potion-Effekt (" + this.getTimeLeft().toMinutes() + "m)");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }


  @Override
  public void merge(ConsumablePotionBuff other) {
    Duration timeLeft = other.getTimeLeft();
    this.setDurationFromNow(this.getTimeLeft().plus(timeLeft));
  }


}
