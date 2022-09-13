package com.gestankbratwurst.revenant.projectrevenant.loot.manager;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootableChest;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement.ChunkDomain;
import com.gestankbratwurst.revenant.projectrevenant.util.worldmanagement.WorldDomain;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.Flushable;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

public class LootChestManager implements Flushable {

  private final Map<UUID, WorldDomain<ChunkDomain<LootableChest>>> spawnableLootChests = new HashMap<>();
  private final PriorityQueue<LootableChest> respawnQueue = new PriorityQueue<>();

  private final Map<UUID, LootChestSpawnArea> spawnAreaMap = new HashMap<>();
  private final Map<Position, UUID> activeChestMap = new HashMap<>();

  public List<LootChestSpawnArea> getAllAreas() {
    return List.copyOf(spawnAreaMap.values());
  }

  public void removeLootChestAt(Position position) {
    LootChestSpawnArea area = getAreaConnectedTo(position);

    for (LootChestSpawnArea possibleArea : spawnAreaMap.values()) {
      possibleArea.removeEmptyPosition(position);
    }

    if (area != null) {
      area.reduceCurrentActive();
    }

    eraseFromPossibleRespawn(position);
  }

  public void removeLootChestArea(LootChestSpawnArea area) {
    purgeArea(area);
    spawnAreaMap.remove(area.getAreaId());

    if (activeChestMap.containsValue(area.getAreaId())) {
      throw new RuntimeException("Active Chests still contained removed lootchest spawnarea");
    }
  }

  public void populateArea(LootChestSpawnArea area) {
    purgeArea(area);

    area.setCurrentActive(0);
    int available = area.getAvailablePositionCount();

    for (int i = 0; i < available; i++) {
      LootType type = area.getRandomType();
      LootChestSpawnArea.PositionData positionData = area.getRandomEmptyPosition();

      LootableChest lootableChest = new LootableChest(type, positionData.getPosition(), positionData.getBlockData(), area.getAreaId());
      enqueChestForSpawn(lootableChest);
      area.removeEmptyPosition(positionData.getPosition());
    }
  }

  private void purgeArea(LootChestSpawnArea area) {
    LootManager lootManager = LootManager.getInstance();
    UUID areaId = area.getAreaId();
    for (Position pos : List.copyOf(activeChestMap.keySet())) {
      if (!activeChestMap.get(pos).equals(areaId)) {
        continue;
      }

      activeChestMap.remove(pos);
      Block block = pos.toLocation().getBlock();

      if (block.getType() == Material.AIR) {
        new IllegalStateException("Active chest of type AIR: " + block.getLocation()).printStackTrace();
        continue;
      }

      if (!(block.getState() instanceof PersistentDataHolder holder)) {
        block.setType(Material.AIR);
        new IllegalStateException("Active chest is not a data holder: " + block.getLocation()).printStackTrace();
        continue;
      }

      if (!lootManager.hasLoot(holder)) {
        block.setType(Material.AIR);
        new IllegalStateException("Active chest is data holder but has no data: " + block.getLocation()).printStackTrace();
        continue;
      }

      area.addEmptyPosition(Position.at(block), block.getBlockData());
      pos.toLocation().getBlock().setType(Material.AIR);
    }

    for (LootableChest lootableChest : List.copyOf(respawnQueue)) {
      if (lootableChest.getOwner().equals(areaId)) {
        eraseFromPossibleRespawn(lootableChest.getPosition());
        area.addEmptyPosition(lootableChest.getPosition(), lootableChest.getBlockData());
      }
    }

    for (WorldDomain<ChunkDomain<LootableChest>> worldDomain : List.copyOf(spawnableLootChests.values())) {
      for (ChunkDomain<LootableChest> chunkDomain : List.copyOf(worldDomain.getValues())) {
        for (LootableChest lootableChest : List.copyOf(chunkDomain.getValues())) {
          if (lootableChest.getOwner().equals(areaId)) {
            eraseFromPossibleRespawn(lootableChest.getPosition());
            area.addEmptyPosition(lootableChest.getPosition(), lootableChest.getBlockData());
          }
        }
      }
    }
  }

  public void forceFullChestPopulation() {
    this.forceFullChestPopulation(null);
  }

  public void forceFullChestPopulation(CommandSender issuer) {
    int counter = 0;
    int full = spawnAreaMap.size();
    for (LootChestSpawnArea area : spawnAreaMap.values()) {
      int finalCounter = counter;
      TaskManager.getInstance().runBukkitSyncDelayed(() -> {
        populateArea(area);
        if (issuer != null) {
          Msg.sendInfo(issuer, "Populated {} [{}/{}]", area.getInternalName(), finalCounter, full);
        }
      }, counter++);
    }
  }

  public List<String> getSpawnAreaNames() {
    return getAllAreas().stream().map(LootChestSpawnArea::getInternalName).toList();
  }

  public LootChestSpawnArea getSpawnArea(String internalName) {
    for (LootChestSpawnArea area : spawnAreaMap.values()) {
      if (area.getInternalName().equals(internalName)) {
        return area;
      }
    }

    throw new RuntimeException("No SpawnArea found with name: " + internalName);
  }

  private LootChestSpawnArea getAreaConnectedTo(Position position) {
    return spawnAreaMap.get(activeChestMap.get(position));
  }

  private void eraseFromPossibleRespawn(Position lootChestPosition) {
    respawnQueue.removeIf(chest -> {
      if (chest.getPosition().equals(lootChestPosition)) {
        LootChestSpawnArea area = spawnAreaMap.get(chest.getOwner());
        area.setEnqueudCount(area.getEnqueudCount() - 1);
        return true;
      } else {
        return false;
      }
    });
    dequeChestFromSpawn(lootChestPosition);
  }

  public void reportLootedChest(Position position) {
    UUID areaId = activeChestMap.get(position);
    LootChestSpawnArea area = getAreaConnectedTo(position);

    if (area == null) {
      throw new IllegalStateException("Illegal Lootchest: " + position.toLocation());
    }

    Location location = position.toLocation();
    Block block = location.getBlock();

    BlockData brokenData = block.getBlockData();
    block.setType(Material.AIR);

    block.getWorld().spawnParticle(Particle.BLOCK_DUST, location.add(0.5, 0.5, 0.5), 6, 0.2, 0.2, 0.2, brokenData);

    activeChestMap.remove(position);
    area.reduceCurrentActive();
    area.addEmptyPosition(position, brokenData);

    LootChestSpawnArea.PositionData newPositionData = area.getRandomEmptyPosition();
    LootType newType = area.getRandomType();

    area.removeEmptyPosition(newPositionData.getPosition());

    LootableChest lootableChest = new LootableChest(newType, newPositionData.getPosition(), newPositionData.getBlockData(), areaId);
    lootableChest.setRespawnTimeFromNow();

    respawnQueue.add(lootableChest);

  }

  public void checkRespawnQueue() {

    LootableChest chest = respawnQueue.peek();

    if (chest == null || chest.getRespawnTimestamp() > System.currentTimeMillis()) {
      return;
    }

    LootChestSpawnArea area = spawnAreaMap.get(chest.getOwner());
    area.setEnqueudCount(area.getEnqueudCount() - 1);
    enqueChestForSpawn(chest);
    respawnQueue.poll();

    checkRespawnQueue();
  }

  /**
   * Spawns all chests in @param chunk and deques them from spawnableLootChests
   */

  public void checkSpawnableChestsInChunk(Chunk chunk) {
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
        dequeChestFromSpawn(lootableChest.getPosition());
      }, tickDelay);

      tickDelay += 1;
    }
  }

  public void enqueChestForSpawn(LootableChest lootableChest) {
    UUID worldId = lootableChest.getPosition().getWorldId();
    long chunkKey = lootableChest.getPosition().getChunkId();
    int position = lootableChest.getPosition().getRelLoc();

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

  public void dequeChestFromSpawn(Position position) {
    UUID worldId = position.getWorldId();
    long chunkKey = position.getChunkId();
    int relLoc = position.getRelLoc();

    WorldDomain<ChunkDomain<LootableChest>> worldDomain = spawnableLootChests.get(worldId);

    if (worldDomain == null) {
      return;
    }

    ChunkDomain<LootableChest> chunkDomain = worldDomain.getInChunk(chunkKey);

    if (chunkDomain == null) {
      return;
    }

    LootableChest chest = chunkDomain.removeAtLocation(relLoc);
    if (chest != null) {
      LootChestSpawnArea area = spawnAreaMap.get(chest.getOwner());
      area.setEnqueudCount(area.getEnqueudCount() - 1);
    }


    if (chunkDomain.isEmpty()) {
      worldDomain.removeInChunk(chunkKey);
      if (worldDomain.isEmpty()) {
        spawnableLootChests.remove(worldId);
      }
    }
  }

  public void spawnLootableChest(LootableChest lootableChest) {
    LootManager lootManager = LootManager.getInstance();

    World world = Bukkit.getWorld(lootableChest.getPosition().getWorldId());

    if (world == null) {
      throw new IllegalStateException("Illegal lootable chest spawn in unloaded world.");
    }

    Chunk chunk = world.getChunkAt(lootableChest.getPosition().getChunkId());
    int relLoc = lootableChest.getPosition().getRelLoc();
    Block block = chunk.getBlock(UtilChunk.blockKeyToX(relLoc), UtilChunk.blockKeyToY(relLoc), UtilChunk.blockKeyToZ(relLoc));

    block.setBlockData(lootableChest.getBlockData(), true);

    TileState state = (TileState) block.getState();
    lootManager.applyTypeTo(state, lootableChest.getType());
    state.update(true);

    UUID ownerId = lootableChest.getOwner();
    LootChestSpawnArea area = spawnAreaMap.get(ownerId);

    if (area == null) {
      throw new IllegalStateException("Unowned chest tried to respawn.");
    }

    activeChestMap.put(lootableChest.getPosition(), ownerId);
    area.incrementCurrentActive();
  }


  public void addSpawnArea(LootChestSpawnArea area) {
    spawnAreaMap.put(area.getAreaId(), area);
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
    String json = MMCore.getGsonProvider().toJsonPretty(this);
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