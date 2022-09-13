package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

public class LootChestSpawnArea {

  private final List<Position> emptyPositions;
  private final Map<LootType, Integer> availableTypes;
  private final List<LootableChest> activeChests;

  @Getter
  private final int maxActive;

  public LootChestSpawnArea(int maxActive){
    emptyPositions = new ArrayList<>();
    availableTypes = new HashMap<>();
    activeChests = new ArrayList<>();

    this.maxActive = maxActive;
  }


  public void addPosition(Position pos){
    emptyPositions.add(pos);
  }

  public void removePosition(Position pos){
    emptyPositions.remove(pos);
  }

  public void addType(LootType type){
    availableTypes.compute(type, (key, curVal) -> curVal == null ? 1 : curVal + 1);
  }

  public void removeOneOf(LootType type){
    availableTypes.compute(type, (key, curVal) -> curVal == null ? null : curVal - 1);
  }

  public void addToActiveChests(LootableChest chest) {
    activeChests.add(chest);
    LootType type = chest.getType();
    availableTypes.compute(type, (key, curValue) -> curValue == null ? null : curValue - 1);
    if(availableTypes.get(type) == 0){
      availableTypes.remove(type);
    }
    emptyPositions.remove(chest.getPosition());
  }

  public boolean isActiveIn(LootableChest chest){
    return activeChests.contains(chest);
  }

  public void removeActive(LootableChest chest){
    activeChests.remove(chest);
    availableTypes.compute(chest.getType(), (key, curValue) -> curValue == null ? 1 : curValue + 1);
    emptyPositions.add(chest.getPosition());
  }

  public Position getRandomEmpty(){
    return emptyPositions.get(ThreadLocalRandom.current().nextInt(emptyPositions.size()-1));
  }

  public LootType getRandomAvailableType(){
    LootType type = LootType.DUMMY_LOOT;
    int randomIndex = ThreadLocalRandom.current().nextInt(availableTypes.size());

    return type;
  }

  public int getMissingActive(){
    return maxActive - activeChests.size();
  }

}
