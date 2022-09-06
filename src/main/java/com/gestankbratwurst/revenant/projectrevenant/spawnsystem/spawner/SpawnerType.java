package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.implementation.DummySpawner;
import lombok.AllArgsConstructor;

import java.util.UUID;
import java.util.function.BiFunction;

@AllArgsConstructor
public enum SpawnerType {

  DUMMY((worldId, name) -> new DummySpawner(UUID.randomUUID(), worldId, name));

  private final BiFunction<UUID, String, RevenantSpawner> creator;

  public RevenantSpawner create(UUID worldId, String name) {
    return creator.apply(worldId, name);
  }

}
