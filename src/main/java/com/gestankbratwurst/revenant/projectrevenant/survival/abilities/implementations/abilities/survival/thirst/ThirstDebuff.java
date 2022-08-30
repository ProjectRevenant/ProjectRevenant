package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.thirst;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.thirst.ThirstHealthEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class ThirstDebuff extends Ability {
  public ThirstDebuff() {
    this.addEffect(new ThirstHealthEffect());
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
    return TextureModel.THIRST_DEBUFF;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.THIRST_DEBUFF_SMALL.getChar() + " §cVerdursten");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du verdurstest und verlierst leben.")
    );
  }
}
