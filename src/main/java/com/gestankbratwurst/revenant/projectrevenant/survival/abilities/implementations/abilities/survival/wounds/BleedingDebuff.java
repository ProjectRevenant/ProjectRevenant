package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wounds;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilMath;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BleedingDebuff extends Ability {
  public BleedingDebuff() {
    super(RevenantAbility.BLEEDING_DEBUFF);
  }

  private int getIntensity() {
    return 0;
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
    int intensity = getIntensity();
    TextureModel model = switch (intensity) {
      case 1 -> TextureModel.BLEEDING_1_SMALL;
      case 2 -> TextureModel.BLEEDING_2_SMALL;
      case 3 -> TextureModel.BLEEDING_3_SMALL;
      case 4 -> TextureModel.BLEEDING_4_SMALL;
      default -> TextureModel.BLEEDING_5_SMALL;
    };
    String roman = UtilMath.toRomanNumeral(intensity);
    return Component.text(model.getChar() + " §cBlutung " + roman);
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    return null;
  }

  @Override
  public TextureModel getModel() {
    return null;
  }
}
