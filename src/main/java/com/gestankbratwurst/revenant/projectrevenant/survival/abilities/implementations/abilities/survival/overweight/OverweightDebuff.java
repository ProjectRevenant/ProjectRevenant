package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.overweight;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.overweight.OverweightEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class OverweightDebuff extends Ability {

  public OverweightDebuff() {
    this.addEffect(new OverweightEffect());
  }

  private OverweightEffect getOverweightEffect() {
    return this.getEffect(OverweightEffect.class);
  }

  public boolean isCritical() {
    return getOverweightEffect().isCritical();
  }

  @Override
  public boolean shouldDisplayInTab() {
    return getOverweightEffect().isActive();
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return getOverweightEffect().isActive();
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return isCritical() ?
            Component.text(TextureModel.OVERWEIGHT_DEBUFF_II_SMALL.getChar() + " §cÜbergewicht II") :
            Component.text(TextureModel.OVERWEIGHT_DEBUFF_I_SMALL.getChar() + " §cÜbergewicht I");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine Geschwindigkeit ist um"),
            Component.text("§c" + ((isCritical() ? 50 : 85)) + "% §7verringert.")
    );
  }

  @Override
  public String getPlainTextName() {
    return isCritical() ? "Übergewicht II" : "Übergewicht I";
  }

  @Override
  public TextureModel getModel() {
    return isCritical() ? TextureModel.OVERWEIGHT_DEBUFF_II : TextureModel.OVERWEIGHT_DEBUFF_I;
  }
}
