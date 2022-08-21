package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BoneStatus {

  HEALTHY("§aGesund"),
  HEALING("§eHeilend"),
  BROKEN("§cGebrochen");

  @Getter
  private final String displayName;

}
