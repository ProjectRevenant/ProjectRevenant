package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class AttackSpeedEffect extends AbilityEffect<Body> {
  private final double attackSpeed;

  public AttackSpeedEffect() {
    this(1.0);
  }

  public AttackSpeedEffect(double attackSpeed) {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.attackSpeed = attackSpeed;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.ATTACK_SPEED).addModifier(new BodyAttributeModifier("meele-base-attack-speed-mod", BodyAttribute.ATTACK_SPEED) {
      @Override
      public double applyAsDouble(double current) {
        return current * (1.0 + attackSpeed);
      }
    });
  }
}
