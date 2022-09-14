package com.gestankbratwurst.revenant.projectrevenant.metaprogression.score;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ScoreType {

  SURVIVED_TIME("Überlebte Zeit"),
  KILLED_MOBS("Getötete Monster"),
  LOOTED_CHESTS("Geplünderte Kisten"),
  CRAFTED_ITEMS("Gecraftete Items");

  @Getter
  private final String name;
}
