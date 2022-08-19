package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.dry;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wet.WetDebuff;
import org.bukkit.entity.Player;

public class DryEffect extends AbilityEffect<Player> {
  public DryEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND, "dry-effect");
  }

  @Override
  public void cast(Player element) {
    EntityAbilityCache.getAbilities(element.getUniqueId())
            .stream()
            .filter(ability -> ability.getIdentifier().equals(RevenantAbility.WET_DEBUFF))
            .map(WetDebuff.class::cast)
            .forEach(wetDebuff -> {
              wetDebuff.dryUp(0.0067);
              if(wetDebuff.isDry()) {
                TaskManager.getInstance().runBukkitSync(() -> RevenantPlayer.of(element).removeAbility(wetDebuff));
              }
            });
  }
}
