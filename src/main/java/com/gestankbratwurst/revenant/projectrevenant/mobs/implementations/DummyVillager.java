package com.gestankbratwurst.revenant.projectrevenant.mobs.implementations;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class DummyVillager extends Villager {
  public DummyVillager(EntityType<? extends Villager> entityType, Level world) {
    super(entityType, world);
  }
}
