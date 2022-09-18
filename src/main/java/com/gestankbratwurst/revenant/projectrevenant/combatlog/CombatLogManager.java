package com.gestankbratwurst.revenant.projectrevenant.combatlog;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.container.CustomPersistentDataType;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanDummyBody;
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

import javax.print.attribute.Attribute;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CombatLogManager {

  private static final double logoutHealthScalar = 0.5;
  private static final double logoutArmorScalar = 0.75;
  private static final double logoutSpeedScalar = 0.75;

  private static final NamespacedKey playerReplacement = NamespaceFactory.provide("combat-log-npc");
  private final Map<LivingEntity, PlayerData> activeReplacements;
  private final Set<UUID> playersToDie;

  public CombatLogManager() {
    activeReplacements = new HashMap<>();
    playersToDie = new HashSet<>();
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
    Body playerBody = bodyManager.getBody(playerUUID);
    Entity spawnedEntity = world.spawnEntity(player.getLocation(), EntityType.VILLAGER, CreatureSpawnEvent.SpawnReason.DEFAULT, entity -> {
      double health = playerBody.getAttribute(BodyAttribute.HEALTH).getCurrentValueModified() * logoutHealthScalar;
      double armor = playerBody.getAttribute(BodyAttribute.PHYSICAL_ARMOR).getCurrentValueModified() * logoutArmorScalar;
      double speed = playerBody.getAttribute(BodyAttribute.SPEED).getCurrentValueModified() * logoutSpeedScalar;
      bodyManager.addBodyTo((LivingEntity) entity, new HumanDummyBody(health, armor, speed));
      setPlayerReplacement(entity, playerUUID);
      entity.customName(Component.text(player.getName()));
      entity.setCustomNameVisible(true);
      activeReplacements.put((LivingEntity) entity, new PlayerData(System.currentTimeMillis() + (RevenantPlayer.damageLogDuration * 50), Arrays.asList(player.getInventory().getContents()), playerUUID));
    });
  }

  public void playerReplacementDeath(Entity deadPlayerReplacement) {
    if (!isPlayerReplacement(deadPlayerReplacement.getPersistentDataContainer())) {
      return;
    }

    PlayerData playerData = activeReplacements.get((LivingEntity) deadPlayerReplacement);
    activeReplacements.remove((LivingEntity) deadPlayerReplacement);
    CombatEvaluator.managePlayerItemDrops(playerData.inventory(), deadPlayerReplacement.getLocation().getBlock());
    playersToDie.add(playerData.uuid);
  }

  public void replacedPlayerJoined(Player player){
    if(playersToDie.contains(player.getUniqueId())){
      player.getInventory().clear();
      ProjectRevenant.getBodyManager().getBody(player).getAttribute(BodyAttribute.HEALTH).setCurrentValue(0);
    }
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

  private void tick(){
    for(LivingEntity replacementEntity : activeReplacements.keySet()){
      PlayerData data = activeReplacements.get(replacementEntity);
      if(data.duration >= System.currentTimeMillis()){
        replacementEntity.remove();
        activeReplacements.remove(replacementEntity);
      }
    }
  }

  private record PlayerData(
          long duration,
          List<ItemStack> inventory,
          UUID uuid
  ) {}

}