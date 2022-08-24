package com.gestankbratwurst.revenant.projectrevenant.loot.manager;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement.ChunkDomain;
import com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement.WorldDomain;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.Flushable;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LootChestManager implements Flushable {

  private final transient Map<UUID, WorldDomain<ChunkDomain<LootableChest>>> spawnableLootChests = new HashMap<>();
  private final Set<LootableChest> allChests = new HashSet<>();

  public void addLootChest(LootableChest chest) {
    allChests.add(chest);
    enqueChestForRespawn(chest);
  }

  public void removeLootChest(LootableChest chest) {
    allChests.remove(chest);
    dequeChestFromRespawn(chest);
  }

  public void initialize() {
    allChests.forEach(this::enqueChestForRespawn);
  }

  /**
   * Spawns all chests in @param chunk and deques them from spawnableLootChests
   */

  public void checkRespawnableChestsInChunk(Chunk chunk) {
    UUID worldId = chunk.getWorld().getUID();
    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.get(worldId);

    if (worldDomain == null) {
      return;
    }

    ChunkDomain<LootableChest> chunkDomain = worldDomain.getInChunk(chunk.getChunkKey());

    if (chunkDomain == null) {
      return;
    }

    TaskManager taskManager = TaskManager.getInstance();

    int tickDelay = 1;
    for (LootableChest lootableChest : chunkDomain.getValues()) {
      taskManager.runBukkitSyncDelayed(() -> {
        spawnLootableChest(lootableChest);
        dequeChestFromRespawn(lootableChest);
      }, tickDelay);

      tickDelay += 1;
    }

  }

  public void enqueChestForRespawn(LootableChest lootableChest) {
    UUID worldId = lootableChest.getWorldUUID();
    long chunkKey = lootableChest.getChunkID();
    int position = lootableChest.getLocationInChunk();

    World world = Bukkit.getWorld(worldId);

    if (world != null) {
      int[] chunkCoords = UtilChunk.getChunkCoords(chunkKey);
      boolean isChunkLoaded = world.isChunkLoaded(chunkCoords[0], chunkCoords[1]);

      if (isChunkLoaded) {
        spawnLootableChest(lootableChest);
        return;
      }
    }

    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.computeIfAbsent(worldId, (key) -> new WorldDomain<>());
    ChunkDomain<LootableChest> chunkDomain = worldDomain.getOrCreate(chunkKey, (key) -> new ChunkDomain<>());

    chunkDomain.addAtLocation(position, lootableChest);
    worldDomain.addInChunk(chunkKey, chunkDomain);
  }

  public void dequeChestFromRespawn(LootableChest lootableChest) {
    UUID worldId = lootableChest.getWorldUUID();
    long chunkKey = lootableChest.getChunkID();
    int position = lootableChest.getLocationInChunk();

    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.get(worldId);

    if (worldDomain == null) {
      return;
    }

    ChunkDomain<LootableChest> chunkDomain = worldDomain.getInChunk(chunkKey);

    if (chunkDomain == null) {
      return;
    }

    chunkDomain.removeAtLocation(position);

    if (chunkDomain.isEmpty()) {
      worldDomain.removeInChunk(chunkKey);
      if (worldDomain.isEmpty()) {
        spawnableLootChests.remove(worldId);
      }
    }
  }

  public void spawnLootableChest(LootableChest lootableChest) {
    LootManager lootManager = LootManager.getInstance();

    World world = Bukkit.getWorld(lootableChest.getWorldUUID());

    if (world == null) {
      throw new IllegalStateException("Illegal lootable chest spawn in unloaded world.");
    }

    Chunk chunk = world.getChunkAt(lootableChest.getChunkID());
    int position = lootableChest.getLocationInChunk();

    Block block = chunk.getBlock(UtilChunk.blockKeyToX(position), UtilChunk.blockKeyToY(position), UtilChunk.blockKeyToZ(position));

    block.setBlockData(lootableChest.getBlockData(), true);

    TileState state = (TileState) block.getState();
    lootManager.applyTypeTo(state, lootableChest.getType());
    state.update(true);
  }

  @SneakyThrows
  public static LootChestManager create() {
    File file = new File(JavaPlugin.getPlugin(ProjectRevenant.class).getDataFolder(), "loot-chest-manager.json");
    if (!file.exists()) {
      return new LootChestManager();
    }
    String json = Files.readString(file.toPath());
    return MMCore.getGsonProvider().fromJson(json, LootChestManager.class);
  }

  @SneakyThrows
  public void save() {
    String json = MMCore.getGsonProvider().toJson(this);
    File pluginFolder = JavaPlugin.getPlugin(ProjectRevenant.class).getDataFolder();
    if (!pluginFolder.exists()) {
      pluginFolder.mkdirs();
    }
    File file = new File(pluginFolder, "loot-chest-manager.json");
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