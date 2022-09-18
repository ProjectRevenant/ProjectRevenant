package com.gestankbratwurst.revenant.projectrevenant.combatlog;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.container.CustomPersistentDataType;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.combat.CombatEvaluator;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CombatLogManager {

  private static final NamespacedKey playerReplacement = NamespaceFactory.provide("combat-log-npc");
  private final Map<UUID, PlayerData> activeReplacements;

  public CombatLogManager() {
    activeReplacements = new HashMap<>();
  }

  public void playerQuit(Player player) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);

    if (!revenantPlayer.inCombat()) {
      return;
    }
    System.out.println(player.getName() + " in combat quit");

    revenantPlayer.setDamageLog();

    UUID playerUUID = player.getUniqueId();

    BodyManager bodyManager = ProjectRevenant.getBodyManager();

    World world = player.getWorld();
    Entity spawnedEntity = world.spawnEntity(player.getLocation(), EntityType.VILLAGER, CreatureSpawnEvent.SpawnReason.DEFAULT, entity -> {
      bodyManager.addBodyTo((LivingEntity) entity, bodyManager.getBody(playerUUID));
      setPlayerReplacement(entity, playerUUID);
      entity.customName(Component.text(player.getName()));
      entity.setCustomNameVisible(true);
      activeReplacements.put(playerUUID, new PlayerData(RevenantPlayer.damageLogDuration, revenantPlayer.getLogoutInventory()));
    });
  }

  public void playerReplacementDeath(Entity deadPlayerReplacement) {
    if (!isPlayerReplacement(deadPlayerReplacement.getPersistentDataContainer())) {
      return;
    }

    UUID uuid = getPlayerFromReplacement(deadPlayerReplacement.getPersistentDataContainer());
    PlayerData playerData = activeReplacements.get(uuid);
    activeReplacements.remove(uuid);
    CombatEvaluator.managePlayerItemDrops(playerData.inventory(), deadPlayerReplacement.getLocation());
  }

  public boolean isPlayerReplacement(PersistentDataHolder holder) {
    return isPlayerReplacement(holder.getPersistentDataContainer());
  }

  public boolean isPlayerReplacement(PersistentDataContainer container) {
    return container.has(playerReplacement);
  }

  private UUID getPlayerFromReplacement(PersistentDataContainer container) {
    return container.get(playerReplacement, CustomPersistentDataType.UUIDType);
  }

  public void setPlayerReplacement(PersistentDataHolder holder, UUID uuid) {
    setPlayerReplacement(holder.getPersistentDataContainer(), uuid);
  }

  public void setPlayerReplacement(PersistentDataContainer container, UUID uuid) {
    container.set(playerReplacement, CustomPersistentDataType.UUIDType, uuid);
  }

  private record PlayerData(
          int combatTime,
          List<ItemStack> inventory
  ) {
  }

}