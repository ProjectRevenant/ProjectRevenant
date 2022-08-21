package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.hunger;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.sprinting.SprintingEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class SprintingDebuff extends Ability {

  public SprintingDebuff() {
    super(RevenantAbility.SPRINTING_DEBUFF);

    this.addEffect(new SprintingEffect());
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
    return Component.text("§cSprint Erschöpfung");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Du verbrauchst mehr"),
            Component.text("§7Nahrung und Wasser.")
    );
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.RED_X;
  }
}
