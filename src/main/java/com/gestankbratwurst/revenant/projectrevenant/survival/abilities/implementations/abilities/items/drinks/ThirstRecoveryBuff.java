package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.ThirstRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class ThirstRecoveryBuff extends TimedAbility implements Mergeable<ThirstRecoveryBuff> {

  public ThirstRecoveryBuff() {
    this(0.0);
  }

  public ThirstRecoveryBuff(double amount) {
    Duration duration = Duration.ofSeconds((long) (amount / (ThirstRecoveryEffect.RATE * 20)));
    this.addEffect(new ThirstRecoveryEffect());
    this.setDurationFromNow(duration);
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
    return TextureModel.RED_X;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.RED_X.getChar() + " §aWasser getrunken");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }

  @Override
  public String getPlainTextName() {
    return "Wasser getrunken";
  }

  @Override
  public void merge(ThirstRecoveryBuff other) {
    Duration timeLeft = other.getTimeLeft();
    this.setDurationFromNow(this.getTimeLeft().plus(timeLeft));
  }
}