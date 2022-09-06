package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobType;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

public class GlobalSpawnManager {

  private static final int maxMonstersPerPlayer = 25;
  private static final int spawnLocationIterations = 5;
  private static final int minDistance = 20;
  private static final int maxDistance = 50;
  private static final int maxYDif = 10;
  private static final double highestVsOffsetChance = 0.4;
  static final double discardHighestBlock = 0.25;

  private final Map<Player, Integer> playerMonsterCount = new HashMap<>();
  private final Map<UUID, Player> monsterPlayerAssignment = new ConcurrentHashMap<>(2048);

  /**
   * @param player target player
   * @param amount positive amount for positive change, negative amount for negative change
   */
  public void changePlayerMonsterCount(Player player, int amount) {
    playerMonsterCount.compute(player, (player1, currentAmount) -> currentAmount == null ? amount : currentAmount + amount);
  }

  public void assignMonsterToPlayer(UUID uuid, Player player) {
    monsterPlayerAssignment.put(uuid, player);
  }

  public void removeMonsterAssignment(UUID uuid) {
    Player player = monsterPlayerAssignment.get(uuid);

    if (player == null) {
      return;
    }

    playerMonsterCount.compute(player, (player1, amount) -> amount == null ? 0 : amount - 1);

    monsterPlayerAssignment.remove(uuid);
  }

  public void spawnForNext(){
    for(Player player : Bukkit.getOnlinePlayers()){
      spawnMonsters(player);
    }
  }

  public void spawnMonsters(Player target) {

    final int currentMobCount;

    int current = playerMonsterCount.computeIfAbsent(target, (key) -> 0);

    if (current >= maxMonstersPerPlayer) {
      System.out.println("[DEBUG] Reached maximum Monsters for Player " + target.getDisplayName());
      return;
    }

    Location currentLocation = target.getLocation();

    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    for (int i = 0; i < spawnLocationIterations; i++) {

      int xOffset = threadLocalRandom.nextInt(minDistance, maxDistance);
      int zOffset = threadLocalRandom.nextInt(minDistance, maxDistance);

      Location offsetLocation = currentLocation.add(xOffset, 0, zOffset);

      if (!offsetLocation.getWorld().isChunkLoaded(offsetLocation.getChunk())) {
        continue;
      }

      Block highestBlock = offsetLocation.getWorld().getHighestBlockAt(offsetLocation);

      Location spawnableLocation = null;

      if (spawnPossible(highestBlock) && threadLocalRandom.nextDouble() <= discardHighestBlock) {
        //spawnableLocation = the highest Block
        spawnableLocation = highestBlock.getLocation();
      } else {
        Block randomBlock = offsetLocation.getBlock();

        if (spawnPossible(randomBlock)) {
          //spawnableLocation = block at random offset
          spawnableLocation = offsetLocation;
        } else {

          if (threadLocalRandom.nextDouble() <= highestVsOffsetChance) {

            for (int j = 0; j < maxYDif; j++) {
              Block offsetBlock = highestBlock.getRelative(0, -j, 0);
              if (spawnPossible(offsetBlock)) {
                //spawnableLocation = first spawnable block beneath the highest block
                spawnableLocation = offsetBlock.getLocation();
              }
            }

          } else {

            boolean up = threadLocalRandom.nextDouble() >= 0.5;

            for (int j = 0; j < maxYDif; j++) {

              Block offsetBlock;

              if(up){
                offsetBlock = highestBlock.getRelative(0, j, 0);
              } else {
                offsetBlock = highestBlock.getRelative(0, -j, 0);
              }

              if (spawnPossible(offsetBlock)) {
                //spawnableLocation = first spawnable block going up or down from the random block
                spawnableLocation = offsetBlock.getLocation();
              }
            }

          }

        }


      }

      if(spawnableLocation == null){
        continue;
      }

      CustomMobType type = computeMonsterForLocation(spawnableLocation);

      if(type == null){
        continue;
      }

      Location finalSpawnableLocation = spawnableLocation.add(0.5, 1, 0.5);

      Entity spawned = computeMonsterForLocation(finalSpawnableLocation).spawnAsNms(finalSpawnableLocation);

      monsterPlayerAssignment.put(spawned.getUUID(), target);

      System.out.println("[DEBUG] Spawned at " + spawnableLocation.getBlockX() + "/" + spawnableLocation.getBlockY() + "/" + spawnableLocation.getBlockZ());

      playerMonsterCount.compute(target, (player, currentValue) -> currentValue == null ? 0 : currentValue + 1);

    }


  }

  public CustomMobType computeMonsterForLocation(Location spawnableLocation) {
    return CustomMobType.REVENANT_ZOMBIE;
  }

  public boolean spawnPossible(Block block) {
    if (!block.isSolid() || !block.getType().isOccluding()) {
      return false;
    }

    if (!block.getRelative(0, 1, 0).getType().equals(Material.AIR) || !block.getRelative(0, 1, 0).getType().equals(Material.AIR)) {
      return false;
    }

    return true;

  }
}
