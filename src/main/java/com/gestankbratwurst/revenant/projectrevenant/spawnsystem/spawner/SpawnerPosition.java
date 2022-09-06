package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class SpawnerPosition {

  public static SpawnerPosition of(Location location) {
    return new SpawnerPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }

  public SpawnerPosition() {
    this(0, 0, 0);
  }

  private final int x;
  private final int y;
  private final int z;

}
