package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.dry;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.dry.DryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class DryBuff extends TimedAbility {
  public DryBuff() {
    this.addEffect(new DryEffect());
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
    return TextureModel.DRY_EFFECT;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.DRY_EFFECT_SMALL.getChar() + " §aTrocknen");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return Collections.emptyList();
  }

  @Override
  public String getPlainTextName() {
    return "Trocknen";
  }
}
