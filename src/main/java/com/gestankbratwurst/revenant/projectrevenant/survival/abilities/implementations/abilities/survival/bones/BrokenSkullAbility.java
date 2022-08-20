package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.BrokenBoneDamageEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.skull.BrokenSkullEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenSkullAbility extends Ability {
  public BrokenSkullAbility() {
    super(RevenantAbility.BROKEN_HEAD);
    this.addEffect(new BrokenSkullEffect());
    this.addEffect(new BrokenBoneDamageEffect("broken-skull-damage-effect"));
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
    return Component.text(TextureModel.HEAD_BROKEN_BONE_SMALL.getChar() + " §cSchädelbruch");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du bist ständig vernebelt."),
            Component.text("§7Außerdem verlierst du langsam Leben.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.HEAD_BROKEN_BONE;
  }
}
