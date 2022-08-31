package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.survival.dry;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wet.WetDebuff;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DryEffect extends AbilityEffect<Player> {
  public DryEffect() {
    super(AbilityTrigger.PLAYER_EVERY_SECOND);
  }

  @Override
  public void cast(Player element) {
    Optional.ofNullable(EntityAbilityCache.getAbility(element.getUniqueId(), WetDebuff.class)).ifPresent(debuff -> debuff.dryUp(0.0067));
  }
}
