package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.ranged;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class RangedDamageEffect extends AbilityEffect<Body> {

    private final double damage;

    public RangedDamageEffect(){
        this(0.0);
    }

    public RangedDamageEffect(double damage){
        super(AbilityTrigger.PASSIVE_ATTRIBUTE, "ranged-damage-effect");
        this.damage = damage;
    }

    @Override
    public void cast(Body element) {

        element.getAttribute(BodyAttribute.RANGED_DAMAGE).addModifier(new BodyAttributeModifier("ranged-damage-mod", BodyAttribute.RANGED_DAMAGE) {
            @Override
            public double applyAsDouble(double operand) {
                return operand + damage;
            }
        });

    }
}
