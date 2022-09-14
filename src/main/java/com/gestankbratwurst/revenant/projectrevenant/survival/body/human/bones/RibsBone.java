package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.BrokenRibsAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.bones.HealingRibsAbility;

import java.util.Collections;
import java.util.List;

public class RibsBone extends Bone {

  public static final String TYPE = "RIBS";

  public RibsBone() {
    super(RibsBone.TYPE, "Brustkorb");
  }

  @Override
  public List<Ability> getEffects() {
    return switch (this.getBoneStatus()) {
      case HEALTHY -> Collections.emptyList();
      case HEALING -> Collections.singletonList(new HealingRibsAbility());
      case BROKEN -> Collections.singletonList(new BrokenRibsAbility());
    };
  }

  @Override
  public TextureModel getCurrentGuiIcon() {
    return switch (this.getBoneStatus()) {
      case HEALTHY -> TextureModel.TORSO_BONE;
      case HEALING -> TextureModel.HEALING_BONE_SMALL;
      case BROKEN -> TextureModel.TORSO_BROKEN_BONE_SMALL;
    };
  }
}
