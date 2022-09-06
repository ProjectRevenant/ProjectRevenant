package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.container.CustomPersistentDataType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class RevenantSpawner {

  public static final NamespacedKey SPAWNER_ID_KEY = NamespaceFactory.provide("spawner-linked-id");
  private static final long maxUnloadedTime = Duration.ofDays(14).toMillis();

  private final Set<UUID> activeMobs;
  private final Map<UUID, Long> lastLoadTimestamps;
  private final List<SpawnerPosition> spawnerPositionList;
  @Getter
  private final UUID spawnerId;
  @Getter
  private final UUID worldId;
  @Getter
  private final String internalName;
  @Getter
  private final SpawnerType spawnerType;
  @Getter
  @Setter
  private int maxAmountSpawned;
  @Getter
  @Setter
  private long spawnCooldown;
  @Getter
  @Setter
  private long warmupTime;
  @Getter
  private long lastSpawnTimestamp;
  private long ticksAlive;
  private long warmupStamp;

  public RevenantSpawner(UUID spawnerId, UUID worldId, String internalName, SpawnerType spawnerType) {
    this.spawnerId = spawnerId;
    this.internalName = internalName;
    this.worldId = worldId;
    this.spawnerType = spawnerType;
    spawnerPositionList = new ArrayList<>();
    lastLoadTimestamps = new HashMap<>();
    activeMobs = new HashSet<>();
  }

  public RevenantSpawner() {
    this(UUID.randomUUID(), null, "_NO_NAME_", SpawnerType.DUMMY);
  }

  private void checkForOldMobs() {
    lastLoadTimestamps.entrySet().removeIf(entry -> entry.getValue() < System.currentTimeMillis() - maxUnloadedTime);
  }

  public long getTimeLeft() {
    return lastSpawnTimestamp + spawnCooldown - System.currentTimeMillis();
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

  public int getCurrentAmountFilled() {
    return activeMobs.size() + lastLoadTimestamps.size();
  }

  public boolean isAcceptedForLoad(UUID entityId) {
    return lastLoadTimestamps.containsKey(entityId);
  }

  public void addActiveMob(UUID entityId) {
    lastLoadTimestamps.remove(entityId);
    activeMobs.add(entityId);
  }

  public void removeMob(UUID entityId) {
    lastLoadTimestamps.remove(entityId);
    activeMobs.remove(entityId);
  }

  public void unloadMob(UUID entityId) {
    lastLoadTimestamps.put(entityId, System.currentTimeMillis());
    activeMobs.remove(entityId);
  }

  public Set<Entity> getActiveEntities() {
    return activeMobs.stream().map(Bukkit::getEntity).collect(Collectors.toSet());
  }

  private SpawnerPosition getRandomSpawnPosition() {
    if(spawnerPositionList.isEmpty()) {
      throw new IllegalStateException("No spawn points available.");
    }
    return spawnerPositionList.get(ThreadLocalRandom.current().nextInt(spawnerPositionList.size()));
  }

  protected void tick() {
    if(ticksAlive++ % 2400 == 0) {
      checkForOldMobs();
    }
    if(getCurrentAmountFilled() >= getMaxAmountSpawned()) {
      warmupStamp = System.currentTimeMillis() + warmupTime;
    }
    if(System.currentTimeMillis() < warmupStamp) {
      return;
    }
    if(getTimeLeft() > 0) {
      return;
    }
    if(getCurrentAmountFilled() >= maxAmountSpawned) {
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
    Entity entity = this.spawnMob(position);
    addActiveMob(entity.getUniqueId());
    entity.getPersistentDataContainer().set(SPAWNER_ID_KEY, CustomPersistentDataType.UUIDType, this.spawnerId);
  }

  protected abstract Entity spawnMob(SpawnerPosition position);

}
