package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumableHealthRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class ConsumableHealthBuff extends TimedAbility implements Mergeable<ConsumableHealthBuff> {

  public ConsumableHealthBuff() {
    this(0, Duration.ZERO);
  }

  public ConsumableHealthBuff(double amount, Duration duration) {
    super(false);
    this.addEffect(new ConsumableHealthRecoveryEffect(amount, duration));
  }

  public void startEffect() {
    this.setDurationFromNow(getDuration());
    start();
  }

  public Duration getDuration() {
    return getEffect(ConsumableHealthRecoveryEffect.class).getDuration();
  }

  public double getAmount() {
    return getEffect(ConsumableHealthRecoveryEffect.class).getAmount();
  }

  public void setAmount(double amount, Duration newDuration) {
    getEffect(ConsumableHealthRecoveryEffect.class).setAmount(amount, newDuration);
  }

  @Override
  public boolean shouldDisplayInTab() {
    return true;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return true;
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.HEALTH_ICON;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    Duration timeLeft = getTimeLeft();
    if (timeLeft.getSeconds() < 60) {
      return Component.text(TextureModel.HEALTH_ICON.getChar() + " §aGut ernährt! (" + timeLeft.getSeconds() + "s)");
    } else {
      return Component.text(TextureModel.HEALTH_ICON.getChar() + " §aGut ernährt! (" + (timeLeft.getSeconds() / 60) + "min)");
    }

  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }

  @Override
  public String getPlainTextName() {
    return "Gut ernährt";
  }


  @Override
  public void merge(ConsumableHealthBuff other) {
    Duration otherTimeLeft = other.getTimeLeft();
    double otherHealingLeft = other.getAmount();
    double thisHealingLeft = (getAmount() / getDuration().getSeconds()) * (getTimeLeft().getSeconds());
    this.setDurationFromNow(this.getTimeLeft().plus(otherTimeLeft));
    this.setAmount((thisHealingLeft + otherHealingLeft), (getTimeLeft().plus(otherTimeLeft)));
  }
}