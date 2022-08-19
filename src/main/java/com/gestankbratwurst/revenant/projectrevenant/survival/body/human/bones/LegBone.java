package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.BrokenLegAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.HealingLegAbility;

import java.util.Collections;
import java.util.List;

public class LegBone extends Bone {

  public static final String LEFT = "LEFT_LEG";
  public static final String RIGHT = "RIGHT_LEG";

  public LegBone(String boneType) {
    super(boneType);
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
