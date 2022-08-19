package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.util.DistributedTask;
import org.bukkit.entity.Player;

public class AbilitySecondTask extends DistributedTask<Player> {
  public AbilitySecondTask() {
    super(20);
  }

  @Override
  public void onTick(Player element) {
    EntityAbilityCache.autoUpdate(element, Player.class);
    EntityAbilityCache.getAbilities(element.getUniqueId()).forEach(ability -> ability.reactOn(element, AbilityTrigger.PLAYER_EVERY_SECOND, element));
  }
}
