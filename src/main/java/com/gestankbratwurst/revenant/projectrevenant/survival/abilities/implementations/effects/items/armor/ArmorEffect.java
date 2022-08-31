package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class ArmorEffect extends AbilityEffect<Body> {

  private final double armor;
  private final String sufix;

  public ArmorEffect() {
    this(0.0, "");
  }

  public ArmorEffect(double armor, String sufix){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
    this.armor = armor;
    this.sufix = sufix;
  }

  @Override
  public void cast(Body element) {
    element.getAttribute(BodyAttribute.PHYSICAL_ARMOR).addModifier(new BodyAttributeModifier("armor-mod-" + sufix, BodyAttribute.PHYSICAL_ARMOR) {
      @Override
      public double applyAsDouble(double operand) {
        return operand + armor;
      }
    });
  }

}
