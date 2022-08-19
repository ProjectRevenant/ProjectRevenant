package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.emptybottle;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.emptybottle.EmptyBottleGatherEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class EmptyBottleAbility extends Ability {
  public EmptyBottleAbility() {
    super(RevenantAbility.EMPTY_BOTTLE_FILL);
    this.addEffect(new EmptyBottleGatherEffect());
  }

  @Override
  public boolean shouldDisplayInTab() {
    return false;
  }

  @Override
  public boolean shouldDisplayInActionbar() {
    return false;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text("§fAuffüllbar");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du kannst Flüssigkeiten"),
            Component.text("§7hiermit aufsammeln.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}
