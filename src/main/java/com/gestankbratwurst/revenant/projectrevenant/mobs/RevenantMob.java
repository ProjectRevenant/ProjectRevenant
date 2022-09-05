package com.gestankbratwurst.revenant.projectrevenant.mobs;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RevenantMob<T extends Entity> {

  Body createDefaultBody();

  default Body getActiveBody() {
    return Optional.ofNullable(ProjectRevenant.getBodyManager().getBody(getMobId())).orElseGet(this::createDefaultBody);
  }

  List<Ability> getActiveAbilities();

  UUID getMobId();

  T getSelf();

  void postSpawnSetup();

}
