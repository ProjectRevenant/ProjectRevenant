package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.common.UtilChunk;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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

  private final Map<Long, Double> loadedChunks = new ConcurrentHashMap<>(4096);
  private final Map<Long, Double> manipulationSpots = new HashMap<>();
  private final NamespacedKey heatKey = NamespaceFactory.provide("chunk-heat-level");
  private final NamespacedKey timestampKey = NamespaceFactory.provide("chunk-last-visit");

  private static final double playerBaseHeating = 2.5;
  private static final double coolingPerMinute = 1;
  private static final double maximumHeatDistance = 128;
  private static final double maximumManipulationDistance = 128;

  public ChunkHeatManager() {
    init();
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

    loadedChunks.compute(chunkKey, (key, curValue) -> curValue == null ? 0 : curValue - ((timestamp / 1000 / 60) * coolingPerMinute));
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

      addHeat(chunkKey, additionalHeat * heatScalar);
    }
  }

}