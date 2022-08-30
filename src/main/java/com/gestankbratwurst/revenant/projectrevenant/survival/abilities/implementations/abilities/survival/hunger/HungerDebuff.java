package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.hunger;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.hunger.HungerHealthEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class HungerDebuff extends Ability {
  public HungerDebuff() {
    this.addEffect(new HungerHealthEffect());
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
    return TextureModel.HUNGER_DEBUFF;
  }

  @Override
  public Component getInfoTitle(Player viewer) {
    return Component.text(TextureModel.HUNGER_DEBUFF_SMALL.getChar() + " §cVerhungern");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du verlierst Lebenspunkte und"),
            Component.text("§7kannst weniger tragen.")
    );
  }
}
