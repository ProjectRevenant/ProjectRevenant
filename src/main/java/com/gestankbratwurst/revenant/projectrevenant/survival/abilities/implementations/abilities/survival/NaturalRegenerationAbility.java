package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class NaturalRegenerationAbility extends Ability {
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
    return Component.text(TextureModel.GREEN_HEAL_CROSS_SMALL.getChar() + " §aErholung");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return List.of(
            Component.text("§7Solange du genügend Nahrung"),
            Component.text("§7und Wasser hast, erholst du"),
            Component.text("§7dich bis zu maximal der Hälfte"),
            Component.text("§7deiner Leben.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Erholung";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.GREEN_HEAL_CROSS;
  }
}
