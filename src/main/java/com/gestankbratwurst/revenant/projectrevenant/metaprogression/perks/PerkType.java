package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PerkType {

  SURVIVAL("Überleben"),
  COMBAT("Kampf"),
  UTILITY("Unterstützung");

  @Getter
  private final String displayName;

}
