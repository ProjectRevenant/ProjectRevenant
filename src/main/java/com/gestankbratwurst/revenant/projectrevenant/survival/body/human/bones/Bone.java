package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import lombok.Getter;

import java.util.List;

public abstract class Bone {

  @Getter
  private final String boneType;
  @Getter
  private final String boneDisplayName;
  @Getter
  private BoneStatus boneStatus;
  private int statusTimeLeft;

  public Bone(String boneType, String boneDisplayName) {
    this.boneType = boneType;
    this.boneStatus = BoneStatus.HEALTHY;
    this.boneDisplayName = boneDisplayName;
  }

  public Bone() {
    this("NO_TYPE", "NO_TYPE");
  }

  public void breakBone() {
    statusTimeLeft += 300;
    boneStatus = BoneStatus.BROKEN;
  }

  public void bandageBone() {
    if(boneStatus == BoneStatus.BROKEN) {
      boneStatus = BoneStatus.HEALING;
      statusTimeLeft = 150;
    }
  }

  public void healBone() {
    statusTimeLeft = 0;
    boneStatus = BoneStatus.HEALTHY;
  }

  public void tickSecond() {
    if(statusTimeLeft > 0) {
      switch (boneStatus) {
        case HEALTHY -> {
        }
        case HEALING, BROKEN -> {
          statusTimeLeft--;
          if(statusTimeLeft == 0) {
            boneStatus = BoneStatus.HEALTHY;
          }
        }
      }
    }
  }

  public abstract List<Ability> getEffects();

  public abstract TextureModel getCurrentGuiIcon();

}
