package com.gestankbratwurst.revenant.projectrevenant.mobs;

import lombok.AllArgsConstructor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Location;

@AllArgsConstructor
public enum CustomMobType {

  REVENANT_ZOMBIE(CustomEntityType.REVENANT_ZOMBIE);

  private final EntityType<?> nmsType;

  public Entity spawnAsNms(Location location) {
    return CustomMobManager.spawnNmsEntity(nmsType, location);
  }

}
