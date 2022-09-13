package com.gestankbratwurst.revenant.projectrevenant.loot.chestloot;

import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class LootableChest implements Comparable<LootableChest> {

  private final LootType type;
  private final Position position;
  private final BlockData blockData;
  private final UUID owner;
  private long respawnTimestamp = 0L;

  public void setRespawnTimestamp(long respawnTimestamp) {
    this.respawnTimestamp = respawnTimestamp;
  }

  public void setRespawnTimeFromNow() {
    this.setRespawnTimestamp(System.currentTimeMillis() + type.getRespawnTimeMillis());
  }

  @Override
  public int hashCode() {
    return position.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof LootableChest lootChest)) {
      return false;
    }

    return this.position.equals(lootChest.position);
  }

  @Override
  public int compareTo(@NotNull LootableChest other) {
    return Long.compare(this.respawnTimestamp, other.respawnTimestamp);
  }
}