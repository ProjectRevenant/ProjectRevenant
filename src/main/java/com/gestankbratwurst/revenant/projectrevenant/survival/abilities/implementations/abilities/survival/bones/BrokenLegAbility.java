package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.BrokenBoneDamageEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.leg.BrokenLegEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenLegAbility extends Ability {
  public BrokenLegAbility() {
    this.addEffect(new BrokenLegEffect());
    this.addEffect(new BrokenBoneDamageEffect("broken-leg-damage-effect"));
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
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.BROKEN_BONE_SMALL.getChar() + " §cBeinbruch");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Deine Laufgeschwindigkeit ist um"),
            Component.text("§c40% §7verringert. Außerdem verlierst"),
            Component.text("§7du langsam Leben.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Beinbruch";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.BROKEN_BONE;
  }
}
