package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.weapons.melee;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;

public class WeaponKnockbackEffect extends AbilityEffect<Body> {

    private final double knockback;
    public WeaponKnockbackEffect(){
        this(0.0);
    }

    public WeaponKnockbackEffect(double knockback){
        super(AbilityTrigger.PASSIVE_ATTRIBUTE);
        this.knockback = knockback;
    }

    @Override
    public void cast(Body element) {
        element.getAttribute(BodyAttribute.MELEE_KNOCKBACK).addModifier(new BodyAttributeModifier("melee-knockback-mod", BodyAttribute.MELEE_KNOCKBACK) {
            @Override
            public double applyAsDouble(double operand) {
                return operand + knockback;
            }
        });
    }
}
