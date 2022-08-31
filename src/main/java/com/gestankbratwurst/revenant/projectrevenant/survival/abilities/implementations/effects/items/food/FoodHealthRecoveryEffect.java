package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodHealthRecoveryEffect extends AbilityEffect<Body> {


  private final double rate;

  public FoodHealthRecoveryEffect() {
    this(0.0);
  }

  public FoodHealthRecoveryEffect(double rate){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.rate = rate;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.HEALTH).addModifier(new BodyAttributeModifier("food-health-recovery-mod", BodyAttribute.HEALTH) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + rate;
      }
    });
  }

}