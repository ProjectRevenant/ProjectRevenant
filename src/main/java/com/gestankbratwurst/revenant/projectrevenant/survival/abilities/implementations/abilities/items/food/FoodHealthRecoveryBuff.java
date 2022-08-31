package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food.FoodHealthRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class FoodHealthRecoveryBuff extends TimedAbility implements Mergeable<FoodHealthRecoveryBuff> {

  public FoodHealthRecoveryBuff(double amount, Duration duration) {
    this.addEffect(new FoodHealthRecoveryEffect());
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
    return TextureModel.HEALTH_ICON;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.HEALTH_ICON.getChar() + " §aGut ernährt!");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }


  @Override
  public void merge(FoodHealthRecoveryBuff other) {
    Duration timeLeft = other.getTimeLeft();
    this.setDurationFromNow(this.getTimeLeft().plus(timeLeft));
  }
}
