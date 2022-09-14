package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkHeatManager {

  private static final double playerBaseHeating = 2.5;
  private static final double coolingPerMinute = 5.0;
  private static final double maximumHeatDistance = 16;
  private static final double maximumManipulationDistance = 16;
  private static final double globalHeatScalar = 0.01;

  private final Map<Long, Double> loadedChunks = new ConcurrentHashMap<>(4096);
  private final Map<Long, Double> manipulationSpots = new HashMap<>();
  private final NamespacedKey heatKey = NamespaceFactory.provide("chunk-heat-level");
  private final NamespacedKey timestampKey = NamespaceFactory.provide("chunk-last-visit");

  public ChunkHeatManager() {
    init();
  }

  public double getAverageHeatInRegion(Location location, double blockRadius) {
    int chunkRadius = ((int) blockRadius) >> 4;
    int cx = location.getBlockX() >> 4;
    int cz = location.getBlockZ() >> 4;
    int chunkCount = chunkRadius * chunkRadius;
    double heatSum = 0.0;
    for (int x = -chunkRadius; x <= chunkRadius; x++) {
      for (int z = -chunkRadius; z <= chunkRadius; z++) {
        double heat = getChunkHeat(cx + x, cz + z);
        if (!location.getWorld().isChunkLoaded(cx + x, cz + z)) {
          chunkCount--;
        }
        heatSum += heat;
      }
    }
    System.out.printf("Heat for %d (%d) chunks.%n", chunkCount, chunkRadius * chunkRadius);
    return heatSum / chunkCount;
  }

  public double getChunkHeat(int chunkX, int chunkZ) {
    return loadedChunks.getOrDefault(UtilChunk.getChunkKey(chunkX, chunkZ), 0D);
  }

  private void init() {
    Bukkit.getWorlds().forEach(world -> Arrays.stream(world.getLoadedChunks()).forEach(this::addChunk));
  }

  public void addManipulation(long chunkKey, double value) {
    manipulationSpots.put(chunkKey, value);
  }

  public void addHeat(long chunkKey, double heat) {
    loadedChunks.compute(chunkKey, (key, value) -> value == null ? heat : value + heat);
  }

  public void addChunk(Chunk chunk) {

    long chunkKey = chunk.getChunkKey();

    Double heat = chunk.getPersistentDataContainer().get(heatKey, PersistentDataType.DOUBLE);
    loadedChunks.put(chunkKey, Objects.requireNonNullElse(heat, 0.0));

    final Long timestamp = chunk.getPersistentDataContainer().get(timestampKey, PersistentDataType.LONG);

    if (timestamp == null) {
      return;
    }

    loadedChunks.compute(chunkKey, (key, curValue) -> {
      if (curValue == null) {
        return 0D;
      }
      return Math.max(0, curValue - ((timestamp / 1000D / 60D) * coolingPerMinute));
    });
  }

  public void removeChunk(Chunk chunk) {
    loadedChunks.remove(chunk.getChunkKey());
    chunk.getPersistentDataContainer().set(timestampKey, PersistentDataType.LONG, System.currentTimeMillis());
  }

  protected void updateChunkNoise() {
    for (long chunkKey : loadedChunks.keySet()) {
      int[] chunkPos = UtilChunk.getChunkCoords(chunkKey);
      double additionalHeat = 0.0;

      for (Player player : Bukkit.getOnlinePlayers()) {
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
          continue;
        }

        Location playerLoc = player.getLocation();
        int chunkX = playerLoc.getBlockX() >> 4;
        int chunkZ = playerLoc.getBlockZ() >> 4;

        double distanceSquared = (chunkX - chunkPos[0]) * (chunkZ - chunkPos[1]);

        if (distanceSquared <= maximumHeatDistance * maximumHeatDistance) {
          additionalHeat += playerBaseHeating;
          additionalHeat += (RevenantPlayer.of(player).getNoiseLevel() / Math.exp(distanceSquared * 0.03));
        }
      }

      double heatScalar = 1.0;

      for (long spotKey : manipulationSpots.keySet()) {
        int[] spotPos = UtilChunk.getChunkCoords(spotKey);

        double distanceSquared = (chunkPos[0] - spotPos[0]) * (chunkPos[1] - spotPos[1]);

        if (distanceSquared <= maximumManipulationDistance * maximumManipulationDistance) {
          heatScalar *= manipulationSpots.get(spotKey) / Math.exp(distanceSquared * 0.01);
        }
      }

      addHeat(chunkKey, additionalHeat * heatScalar * globalHeatScalar);
    }
  }

}