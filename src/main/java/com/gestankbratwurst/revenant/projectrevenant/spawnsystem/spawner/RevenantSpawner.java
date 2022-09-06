package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class RevenantSpawner {

  @Getter
  private final UUID spawnerId;
  @Getter
  private final UUID worldId;
  @Getter
  private final String internalName;
  private final List<SpawnerPosition> spawnerPositionList;
  @Getter
  private final SpawnerType spawnerType;
  @Getter
  @Setter
  private int maxAmountSpawned;
  @Getter
  @Setter
  private int currentAmountSpawned;
  @Getter
  @Setter
  private long spawnCooldown;
  @Getter
  private long lastSpawnTimestamp;

  public RevenantSpawner(UUID spawnerId, UUID worldId, String internalName, SpawnerType spawnerType) {
    this.spawnerId = spawnerId;
    this.internalName = internalName;
    this.worldId = worldId;
    this.spawnerType = spawnerType;
    spawnerPositionList = new ArrayList<>();
  }

  public long getTimeLeft() {
    return lastSpawnTimestamp + spawnCooldown - System.currentTimeMillis();
  }

  public RevenantSpawner() {
    this(UUID.randomUUID(), null, "_NO_NAME_", SpawnerType.DUMMY);
  }

  public boolean hasSpawnerPosition(SpawnerPosition position) {
    return spawnerPositionList.contains(position);
  }

  public void addSpawnerPosition(SpawnerPosition spawnerPosition) {
    if(spawnerPositionList.contains(spawnerPosition)) {
      return;
    }
    spawnerPositionList.add(spawnerPosition);
  }

  public void removeSpawnerPosition(SpawnerPosition spawnerPosition) {
    spawnerPositionList.remove(spawnerPosition);
  }

  public List<SpawnerPosition> getAllPositions() {
    return List.copyOf(spawnerPositionList);
  }

  private SpawnerPosition getRandomSpawnPosition() {
    if(spawnerPositionList.isEmpty()) {
      throw new IllegalStateException("No spawn points available.");
    }
    return spawnerPositionList.get(ThreadLocalRandom.current().nextInt(spawnerPositionList.size()));
  }

  public void incrementCurrentAmountSpawned() {
    this.currentAmountSpawned++;
  }

  public void decrementCurrentAmountSpawned() {
    this.currentAmountSpawned--;
  }

  protected void tick() {
    if(getTimeLeft() > 0) {
      return;
    }
    if(currentAmountSpawned >= maxAmountSpawned) {
      return;
    }

    World world = Bukkit.getWorld(worldId);

    if(world == null) {
      return;
    }

    SpawnerPosition position = getRandomSpawnPosition();

    if(!world.isChunkLoaded(position.getX() >> 4, position.getZ() >> 4)) {
      return;
    }

    lastSpawnTimestamp = System.currentTimeMillis();
    this.spawnMob(position);
    incrementCurrentAmountSpawned();
  }

  protected abstract void spawnMob(SpawnerPosition position);

}
