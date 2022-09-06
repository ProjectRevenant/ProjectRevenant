package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.data.json.DeserializationPostProcessable;
import com.gestankbratwurst.core.mmcore.util.container.CustomPersistentDataType;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import lombok.SneakyThrows;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.Flushable;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpawnerManager implements DeserializationPostProcessable, Flushable {

  private static final int maxSpawnersPerTick = 20;
  private final transient ArrayDeque<UUID> spawnerTickQueue = new ArrayDeque<>();
  private final Map<UUID, RevenantSpawner> spawnerMap = new HashMap<>();

  public RevenantSpawner getSpawnerOfMob(Entity entity) {
    UUID spawnerId = entity.getPersistentDataContainer().get(RevenantSpawner.SPAWNER_ID_KEY, CustomPersistentDataType.UUIDType);
    return spawnerId == null ? null : getSpawner(spawnerId);
  }

  public RevenantSpawner getSpawner(UUID spawnerId) {
    return spawnerMap.get(spawnerId);
  }

  public void createSpawner(SpawnerType spawnerType, UUID world, String name) {
    addSpawner(spawnerType.create(world, name));
  }

  public void addSpawner(RevenantSpawner revenantSpawner) {
    spawnerMap.put(revenantSpawner.getSpawnerId(), revenantSpawner);
    spawnerTickQueue.add(revenantSpawner.getSpawnerId());
    TaskManager.getInstance().executeTask(this::flush);
  }

  public void removeSpawner(RevenantSpawner revenantSpawner) {
    spawnerMap.remove(revenantSpawner.getSpawnerId());
    spawnerTickQueue.remove(revenantSpawner.getSpawnerId());
    TaskManager.getInstance().executeTask(this::flush);
  }

  public RevenantSpawner getSpawnerByName(String internalName) {
    return spawnerMap.values().stream().filter(spawner -> spawner.getInternalName().equals(internalName)).findAny().orElse(null);
  }

  public List<String> getAllSpawnerNames() {
    return spawnerMap.values().stream().map(RevenantSpawner::getInternalName).toList();
  }

  public void tickSpawners() {
    UUID lastId = spawnerTickQueue.peekLast();
    if(lastId == null) {
      return;
    }
    UUID currentId = null;
    int counter = 0;
    while (currentId != lastId) {
      currentId = spawnerTickQueue.poll();
      getSpawner(currentId).tick();
      assert currentId != null;
      spawnerTickQueue.add(currentId);
      if (++counter == maxSpawnersPerTick) {
        break;
      }
    }
  }

  @SneakyThrows
  public static SpawnerManager create() {
    File file = new File(JavaPlugin.getPlugin(ProjectRevenant.class).getDataFolder(), "spawner-manager.json");
    if (!file.exists()) {
      return new SpawnerManager();
    }
    String json = Files.readString(file.toPath());
    return MMCore.getGsonProvider().fromJson(json, SpawnerManager.class);
  }

  @SneakyThrows
  public void save() {
    String json = MMCore.getGsonProvider().toJsonPretty(this);
    File pluginFolder = JavaPlugin.getPlugin(ProjectRevenant.class).getDataFolder();
    if (!pluginFolder.exists()) {
      pluginFolder.mkdirs();
    }
    File file = new File(pluginFolder, "spawner-manager.json");
    if (!file.exists()) {
      file.createNewFile();
    }
    Files.writeString(file.toPath(), json);
  }

  @Override
  public void flush() {
    save();
  }

  @Override
  public void gsonPostProcess() {
    spawnerTickQueue.addAll(spawnerMap.keySet());
  }
}
