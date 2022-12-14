package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.consumables;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.List;

public class DebuffRemovalEffect extends AbilityEffect<PlayerItemConsumeEvent> {

  private final List<Class<? extends Ability>> removeAbilities;

  public DebuffRemovalEffect(List<Class<? extends Ability>> removeAbility) {
    super(AbilityTrigger.CONSUME_ITEM);
    this.removeAbilities = removeAbility;
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    RevenantPlayer player = RevenantPlayer.of(element.getPlayer());
    for (Class<? extends Ability> abilityClass : removeAbilities) {
      player.removeAbility(abilityClass);
    }
  }
}