package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones.skull.HealingSkullEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class HealingSkullAbility extends Ability {
  public HealingSkullAbility() {
    super(RevenantAbility.HEALING_HEAD);
    this.addEffect(new HealingSkullEffect());
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
    return Component.text(TextureModel.HEALING_HEAD_BONE_SMALL.getChar() + " §6Kopfschiene");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Dein Fernkampf ist um §c15% §7verringert.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.HEALING_HEAD_BONE;
  }
}
