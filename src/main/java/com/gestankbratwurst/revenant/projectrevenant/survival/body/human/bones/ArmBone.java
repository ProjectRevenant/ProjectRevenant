package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.BrokenArmAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.HealingArmAbility;

import java.util.Collections;
import java.util.List;

public class ArmBone extends Bone {

  public static final String LEFT = "LEFT_ARM";
  public static final String RIGHT = "RIGHT_ARM";

  public ArmBone(String boneType) {
    super(boneType);
  }

  @Override
  public List<Ability> getEffects() {
    return switch (this.getBoneStatus()) {
      case HEALTHY -> Collections.emptyList();
      case HEALING -> Collections.singletonList(new HealingArmAbility());
      case BROKEN -> Collections.singletonList(new BrokenArmAbility());
    };
  }
}
