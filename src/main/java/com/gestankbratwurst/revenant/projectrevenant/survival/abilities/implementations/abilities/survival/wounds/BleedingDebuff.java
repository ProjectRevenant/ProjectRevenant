package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wounds;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilMath;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds.BleedingEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds.BleedingInfectionEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds.BleedingParticleEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class BleedingDebuff extends TimedAbility {
  public BleedingDebuff(int intensity) {
    BleedingEffect effect = new BleedingEffect();
    effect.setIntensity(intensity);
    this.addEffect(effect);
    this.addEffect(new BleedingParticleEffect());
    this.addEffect(new BleedingInfectionEffect());
  }

  public BleedingDebuff() {
    this(1);
  }

  public void setIntensity(int intensity) {
    this.getEffect(BleedingEffect.class).setIntensity(intensity);
  }

  private int getIntensity() {
    return this.getEffect(BleedingEffect.class).getIntensity();
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
    return List.of(
            Component.text("§7Du verlierst Lebenspunkte."),
            Component.text("§7Diese Wunde kann sich infizieren.")
    );
  }

  @Override
  public TextureModel getModel() {
    return switch (getIntensity()) {
      case 1 -> TextureModel.BLEEDING_1;
      case 2 -> TextureModel.BLEEDING_2;
      case 3 -> TextureModel.BLEEDING_3;
      case 4 -> TextureModel.BLEEDING_4;
      default -> TextureModel.BLEEDING_5;
    };
  }
}
