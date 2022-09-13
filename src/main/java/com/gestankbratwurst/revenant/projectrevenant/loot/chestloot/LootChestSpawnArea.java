package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

public class LootChestSpawnArea {

  @Getter
  private final UUID areaId;
  @Getter
  @Setter
  private String internalName = "_NO_NAME_";
  private final List<PositionData> emptyPositions;
  private final WeightedCollection<LootType> availableTypes;

  @Getter
  private final int maxActive;

  public LootChestSpawnArea() {
    emptyPositions = new ArrayList<>();
    availableTypes = new WeightedCollection<>();

    this.maxActive = maxActive;
  }

  public void reduceCurrentActive() {
    currentActive -= 1;
  }

  public void addType(LootType type, double weight) {
    availableTypes.add(weight, type);
  }

  public void removeEmptyPosition(Position position) {
    emptyPositions.removeIf(dataPos -> dataPos.position.equals(position));
  }

  public PositionData getRandomEmptyPosition() {
    return emptyPositions.get(ThreadLocalRandom.current().nextInt(emptyPositions.size()));
  }

  public LootType getRandomType() {
    return availableTypes.poll();
  }

  public int getAvailablePositionCount() {
    return maxActive - currentActive;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PositionData {
    private Position position;
    private BlockData blockData;
  }

}
