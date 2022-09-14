package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food.NutritionRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class NutritionRecoveryBuff extends TimedAbility implements Mergeable<NutritionRecoveryBuff> {

  public NutritionRecoveryBuff(double amount) {
    Duration duration = Duration.ofSeconds((long) (amount / (NutritionRecoveryEffect.RATE * 20)));

    this.addEffect(new NutritionRecoveryEffect());
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
    return Component.text(TextureModel.RED_X.getChar() + " §aNahrung gegessen");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }

  @Override
  public String getPlainTextName() {
    return "Nahrung gegessen";
  }


  @Override
  public void merge(NutritionRecoveryBuff other) {
    Duration timeLeft = other.getTimeLeft();
    this.setDurationFromNow(this.getTimeLeft().plus(timeLeft));
  }
}
