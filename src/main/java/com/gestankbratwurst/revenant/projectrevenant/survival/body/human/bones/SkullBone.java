package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.BrokenSkullAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.HealingSkullAbility;

import java.util.Collections;
import java.util.List;

public class SkullBone extends Bone {

  public static final String TYPE = "SKULL";

  public SkullBone() {
    super(SkullBone.TYPE);
  }

  @Override
  public List<Ability> getEffects() {
    return switch (this.getBoneStatus()) {
      case HEALTHY -> Collections.emptyList();
      case HEALING -> Collections.singletonList(new HealingSkullAbility());
      case BROKEN -> Collections.singletonList(new BrokenSkullAbility());
    };
  }
}
