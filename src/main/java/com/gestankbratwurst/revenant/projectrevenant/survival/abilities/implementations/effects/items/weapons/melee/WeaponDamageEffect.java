package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class WeaponDamageEffect extends AbilityEffect<Body> {

  private final double damage;

  public WeaponDamageEffect() {
    this(1.0);
  }

  public WeaponDamageEffect(double damage) {
    super(AbilityTrigger.PASSIVE_ATTRIBUTE, "weapon-damage-effect");
    this.damage = damage;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.MELEE_DAMAGE).addModifier(new BodyAttributeModifier("meele-base-dmg-mod", BodyAttribute.MELEE_DAMAGE) {
      @Override
      public double applyAsDouble(double current) {
        return current + damage;
      }
    });
  }
}
