package com.gestankbratwurst.revenant.projectrevenant.metaprogression.stash;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class StashManager {

  private static final NamespacedKey stashKey = NamespaceFactory.provide("stash-block");

  public boolean isStash(PersistentDataHolder holder) {
    return isStash(holder.getPersistentDataContainer());
  }

  public boolean isStash(PersistentDataContainer container) {
    return container.has(stashKey);
  }

  public void applyStashTo(PersistentDataContainer container) {
    container.set(stashKey, PersistentDataType.BYTE, (byte) 0x1);
  }

  public void applyStashTo(PersistentDataHolder holder) {
    applyStashTo(holder.getPersistentDataContainer());
  }

  public void openUI(Player player) {
    player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 0.5f, 1.0f);
    new StashUI(RevenantPlayer.of(player)).openFor(player);
  }

}
