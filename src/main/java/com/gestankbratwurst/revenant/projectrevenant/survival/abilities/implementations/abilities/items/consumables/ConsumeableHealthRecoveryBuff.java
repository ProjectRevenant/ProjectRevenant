package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumableHealthRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

public class ConsumeableHealthRecoveryBuff extends TimedAbility implements Mergeable<ConsumeableHealthRecoveryBuff> {

  public ConsumeableHealthRecoveryBuff(double amount, Duration duration) {
    this.addEffect(new ConsumableHealthRecoveryEffect());
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
  public String getPlainTextName() {
    return "Gut ernährt";
  }


  @Override
  public void merge(ConsumeableHealthRecoveryBuff other) {
    Duration timeLeft = other.getTimeLeft();
    this.setDurationFromNow(this.getTimeLeft().plus(timeLeft));
  }
}
