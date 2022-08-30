package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.ThirstRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class ThirstRecoveryAbility extends TimedAbility implements Mergeable<ThirstRecoveryAbility> {

  public ThirstRecoveryAbility() {
    this(0.0);
  }

  public ThirstRecoveryAbility(double amount) {
    super(RevenantAbility.THIRST_RECOVERY);

    Duration duration = Duration.ofSeconds((long) (amount / (ThirstRecoveryEffect.RATE * 20)));

    this.addEffect(new ThirstRecoveryEffect());
    this.setDurationFromNow(duration);
  }

  @Override
  public boolean shouldDisplayInTab() {
    return false;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return false;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text("");
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
  public void merge(ThirstRecoveryAbility other) {

  }
}