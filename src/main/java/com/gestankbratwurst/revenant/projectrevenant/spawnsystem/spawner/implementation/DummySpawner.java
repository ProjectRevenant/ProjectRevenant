package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.implementation;

import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobType;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.RevenantSpawner;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerPosition;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class DummySpawner extends RevenantSpawner {

  public DummySpawner(UUID spawnerId, UUID worldId, String internalName) {
    super(spawnerId, worldId, internalName, SpawnerType.DUMMY);
  }

  @Override
  protected Entity spawnMob(SpawnerPosition position) {
    World world = Bukkit.getWorld(this.getWorldId());
    Location location = new Location(world, position.getX(), position.getY(), position.getZ());
    return CustomMobType.REVENANT_ZOMBIE.spawnAsNms(location).getBukkitEntity();
  }

}
