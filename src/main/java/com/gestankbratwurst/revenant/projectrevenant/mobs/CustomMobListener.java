package com.gestankbratwurst.revenant.projectrevenant.mobs;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.event.Listener;

public class CustomMobListener implements Listener {

  public void onAdd(EntityAddToWorldEvent event) {
    if (((CraftEntity) event.getEntity()).getHandle() instanceof RevenantMob<?> revenantMob) {
      revenantMob.postSpawnSetup();
    }
  }

}
