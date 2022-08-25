package com.gestankbratwurst.revenant.projectrevenant.loot.manager;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class LootManager {

  @Getter
  private static final LootManager instance = new LootManager();
  private static final NamespacedKey lootTypeKey = NamespaceFactory.provide("loot-type");
  private static final NamespacedKey removalKey = NamespaceFactory.provide("for-removal");

  private LootManager() {

  }

  public boolean hasLoot(PersistentDataHolder holder) {
    return hasLoot(holder.getPersistentDataContainer());
  }

  public boolean hasLoot(PersistentDataContainer container) {
    return container.has(lootTypeKey);
  }

  public void dropAt(Location location, Player player, PersistentDataHolder origin) {
    getTypeFrom(origin).getGenerator().apply(player).applyTo(player, location);
  }

  public void dropIn(Inventory inventory, Player player, PersistentDataHolder origin) {
    getTypeFrom(origin).getGenerator().apply(player).applyTo(player, inventory);
  }

  public LootType getTypeFrom(PersistentDataHolder holder) {
    return getTypeFrom(holder.getPersistentDataContainer());
  }

  public void applyTypeTo(PersistentDataHolder holder, LootType type) {
    this.applyTypeTo(holder.getPersistentDataContainer(), type);
  }


  public LootType getTypeFrom(PersistentDataContainer container) {
    return LootType.valueOf(container.get(lootTypeKey, PersistentDataType.STRING));
  }

  public void applyTypeTo(PersistentDataContainer container, LootType type) {
    container.set(lootTypeKey, PersistentDataType.STRING, type.name());
  }

  public void removeTypeFrom(PersistentDataContainer container) {
    container.remove(lootTypeKey);
  }

  public void tagForRemoval(PersistentDataContainer container){
    container.set(removalKey, PersistentDataType.BYTE, (byte) 0x1);
  }

  public boolean isForRemoval(PersistentDataContainer container){
    return container.has(removalKey);
  }

}
