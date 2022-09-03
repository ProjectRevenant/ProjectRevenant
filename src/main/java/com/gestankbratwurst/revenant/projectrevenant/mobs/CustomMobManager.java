package com.gestankbratwurst.revenant.projectrevenant.mobs;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;

public class CustomMobManager {

  public static <T extends Entity> T spawnNmsEntity(EntityType<T> type, Location location) {
    ServerLevel world = ((CraftWorld) location.getWorld()).getHandle();
    T entity = type.create(world);
    entity.setPos(location.getX(), location.getY(), location.getZ());
    entity.setRot(location.getYaw(), location.getPitch());
    world.addFreshEntity(entity);
    if(entity instanceof RevenantMob<?> revenantMob) {
      revenantMob.postSpawnSetup();
    }
    return entity;
  }

  public static void setupLiveEntities() {
    for(World world : Bukkit.getWorlds()) {
      for(org.bukkit.entity.Entity entity : world.getEntities()) {
        if(((CraftEntity) entity).getHandle() instanceof RevenantMob<?> revenantMob) {
          revenantMob.postSpawnSetup();
        }
      }
    }
  }

}
