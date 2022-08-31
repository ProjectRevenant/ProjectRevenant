package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.armor;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;

public class ColdInsulationEffect extends AbilityEffect<Body> {

  public ColdInsulationEffect(){
    super(AbilityTrigger.PASSIVE_ATTRIBUTE);
  }

  @Override
  public void cast(Body element) {

  }
}
