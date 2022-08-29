package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.thirstrecovery;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.thirstrecovery.ThirstRecoveryEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class ThirstRecoveryAbility extends TimedAbility {
  public ThirstRecoveryAbility() {
    super(RevenantAbility.THIRST_RECOVERY);
    this.addEffect(new ThirstRecoveryEffect());
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
    return Component.text("");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of();
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}
