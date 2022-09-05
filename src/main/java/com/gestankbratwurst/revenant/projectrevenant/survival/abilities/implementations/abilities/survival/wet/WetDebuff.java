package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wet;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.TemperatureShiftEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wet.WetWeightEffect;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class WetDebuff extends Ability {
  public WetDebuff() {
    this.addEffect(new TemperatureShiftEffect(-0.0001, "wet"));
    this.addEffect(new WetWeightEffect());
  }

  @Getter
  @Setter
  private double litres = 2.0;

  public void dryUp(double amount) {
    litres = Math.max(0, litres - amount);
  }

  public boolean isDry() {
    return litres <= 0;
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
    return TextureModel.WET_EFFECT;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.WET_EFFECT_SMALL.getChar() + " §cDurchnässt");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine Kälteresistenz ist um §c5.0°C"),
            Component.text("§7verringert, deine Hitzeresistenz ist"),
            Component.text("§7um §a5.0°C §7erhöht und du kühlst"),
            Component.text("§7langsam aus.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Durchnässt";
  }
}
