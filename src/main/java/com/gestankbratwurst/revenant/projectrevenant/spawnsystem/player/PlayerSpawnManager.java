package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.player;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.Flushable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerSpawnManager implements Flushable {

  private final List<Position> spawnPoints = new ArrayList<>();
  private final List<Position> spawnPods = new ArrayList<>();

  public void addSpawnPoint(Position position) {
    spawnPoints.add(position);
  }

  public void removeSpawnPoint(Position position) {
    spawnPoints.remove(position);
  }

  public void addSpawnPod(Position position) {
    spawnPods.add(position);
  }

  public void removeSpawnPod(Position position) {
    spawnPods.remove(position);
  }

  public Position getRandomSpawnPoint() {
    return spawnPoints.isEmpty() ? null : spawnPoints.get(ThreadLocalRandom.current().nextInt(spawnPoints.size()));
  }

  public Position getRandomSpawnPod() {
    return spawnPods.isEmpty() ? null : spawnPods.get(ThreadLocalRandom.current().nextInt(spawnPods.size()));
  }

  @SneakyThrows
  public static PlayerSpawnManager create() {
    File file = new File(JavaPlugin.getPlugin(ProjectRevenant.class).getDataFolder(), "spawn-point-manager.json");
    if (!file.exists()) {
      return new PlayerSpawnManager();
    }
    String json = Files.readString(file.toPath());
    return MMCore.getGsonProvider().fromJson(json, PlayerSpawnManager.class);
  }

  @SneakyThrows
  public void save() {
    String json = MMCore.getGsonProvider().toJsonPretty(this);
    File pluginFolder = JavaPlugin.getPlugin(ProjectRevenant.class).getDataFolder();
    if (!pluginFolder.exists()) {
      pluginFolder.mkdirs();
    }
    File file = new File(pluginFolder, "spawn-point-manager.json");
    if (!file.exists()) {
      file.createNewFile();
    }
    Files.writeString(file.toPath(), json);
  }

  @Override
  public void flush() {
    save();
  }

}
