package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables.ConsumableHealthTrigger;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ConsumableHealthAbility extends Ability {

  public ConsumableHealthAbility() {
    this(-50.0, Duration.ofSeconds(10));
  }

  public ConsumableHealthAbility(double amount, Duration duration){
    this(new ConsumableHealthBuff(amount, duration));
  }

  public ConsumableHealthAbility(ConsumableHealthBuff buff) {
    this.addEffect(new ConsumableHealthTrigger(buff));
  }


  public ConsumableHealthBuff getBuff() {
    return getEffect(ConsumableHealthTrigger.class).getBuff();
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
    return Component.text("§9Lebensregeneration");
  }

  @Override
  public List<Component> getInfos(Player viewer) {
    List<Component> output = new ArrayList<>();
    ConsumableHealthBuff buff = getBuff();
    double amount = buff.getAmount();
    Duration duration = buff.getDuration();

    if (duration.getSeconds() < 60) {
      output.add(Component.text(String.format("§7Heilt §e%.1f §7Leben in §e%d Sekunden", amount, duration.getSeconds())));
    } else {
      output.add(Component.text(String.format("§7Heilt §e%.1f §7Leben in §e%d Minuten", amount, (duration.getSeconds() / 60))));
    }
    return output;
  }

  @Override
  public String getPlainTextName() {
    return null;
  }

  @Override
  public TextureModel getModel() {
    return null;
  }
}
