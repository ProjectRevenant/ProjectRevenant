package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.core.mmcore.util.functional.WeightedCollection;
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
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LootChestSpawnArea {

  @Getter
  private final UUID areaId;
  @Getter
  @Setter
  private String internalName = "_NO_NAME_";
  private final List<PositionData> emptyPositions;
  private final WeightedCollection<LootType> availableTypes;

  @Getter
  @Setter
  private int maxActive;
  @Getter
  @Setter
  private int currentActive;
  @Getter
  @Setter
  private int enqueudCount;

  public LootChestSpawnArea() {
    emptyPositions = new ArrayList<>();
    availableTypes = new WeightedCollection<>();

    this.maxActive = 0;
    currentActive = 0;

    this.areaId = UUID.randomUUID();
  }

  public void reduceCurrentActive() {
    currentActive -= 1;
  }

  public void incrementCurrentActive() {
    currentActive += 1;
  }

  public void addType(LootType type, double weight) {
    availableTypes.add(weight, type);
  }

  public void addEmptyPosition(Position pos, BlockData data) {
    emptyPositions.add(new PositionData(pos, data));
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
