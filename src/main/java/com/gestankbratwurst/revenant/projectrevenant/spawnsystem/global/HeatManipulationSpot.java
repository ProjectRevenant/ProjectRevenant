package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

import lombok.Getter;
import lombok.Setter;

public class HeatManipulationSpot {

  @Getter
  private final long chunkKey;
  @Getter
  @Setter
  private double scalar;
  @Getter
  private final String name;

  public HeatManipulationSpot(long chunkKey, double scalar, String name) {
    this.chunkKey = chunkKey;
    this.scalar = scalar;
    this.name = name;
  }


}
