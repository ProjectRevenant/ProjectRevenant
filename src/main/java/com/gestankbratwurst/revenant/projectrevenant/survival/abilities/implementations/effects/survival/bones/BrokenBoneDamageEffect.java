package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.bones;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BrokenBoneDamageEffect extends AbilityEffect<Player> {

  private static final double TRIGGER_CHANCE = 0.05;

  public BrokenBoneDamageEffect(String identifier) {
    super(AbilityTrigger.PLAYER_EVERY_SECOND);
  }

  public BrokenBoneDamageEffect() {
    this("broken-bone");
  }

  @Override
  public void cast(Player element) {
    if(ThreadLocalRandom.current().nextDouble() > TRIGGER_CHANCE) {
      return;
    }
    RevenantPlayer.of(element).getBody().getAttribute(BodyAttribute.HEALTH).addModifier(new BodyAttributeModifier("broken-bone-health-mod", BodyAttribute.HEALTH) {
      @Override
      public double applyAsDouble(double operand) {
        return operand - 0.5;
      }
    });
    element.damage(0);
  }
}
