package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.BrokenLegAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.HealingLegAbility;

import java.util.Collections;
import java.util.List;

public class RibsBone extends Bone {

  public static final String TYPE = "RIBS";

  public RibsBone() {
    super(RibsBone.TYPE);
  }

  @Override
  public List<Ability> getEffects() {
    return switch (this.getBoneStatus()) {
      case HEALTHY -> Collections.emptyList();
      case HEALING -> Collections.singletonList(new HealingLegAbility());
      case BROKEN -> Collections.singletonList(new BrokenLegAbility());
    };
  }
}
