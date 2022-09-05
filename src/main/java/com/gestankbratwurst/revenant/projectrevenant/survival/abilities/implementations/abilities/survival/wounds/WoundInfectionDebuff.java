package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wounds;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilTime;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds.InstantDeathEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.wounds.WoundConsumptionEffect;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class WoundInfectionDebuff extends Ability {
  public WoundInfectionDebuff() {
    this.addEffect(new WoundConsumptionEffect());
    this.addEffect(new InstantDeathEffect(Instant.now().plus(Duration.ofMinutes(60)).toEpochMilli()));
  }


  private long getDeathTime() {
    return (getEffect(InstantDeathEffect.class)).getDeathTime();
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
    return Component.text(TextureModel.WOUND_INFECTION_SMALL.getChar() + " §cInfizierte Wunde");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    String timeLeft = UtilTime.format(Duration.ofMillis(this.getDeathTime() - System.currentTimeMillis()));
    return List.of(
            Component.text("§7Du schwitzt und verbrauchst §c15%§7 mehr Wasser."),
            Component.text("§7Du verbrauchst §c15%§7 mehr Nahrung."),
            Component.text("§7Wenn du diese Infektion nicht in §6" + timeLeft),
            Component.text("§7heilst, §cstirbst du§7.")
    );
  }

  @Override
  public String getPlainTextName() {
    return "Infizierte Wunde";
  }

  @Override
  public TextureModel getModel() {
    return TextureModel.WOUND_INFECTION;
  }
}
