package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BrokenSkullAbility extends Ability {
  public BrokenSkullAbility() {
    super(RevenantAbility.BROKEN_HEAD);
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
            Component.text("§7Dein Schaden ist um §c30% §7verringert."),
            Component.text("§7Du bist ständig verwirrt."),
            Component.text("§7Außerdem verlierst du langsam Leben.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.HEAD_BROKEN_BONE;
  }
}
