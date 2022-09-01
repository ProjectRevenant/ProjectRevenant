package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.food;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.ThirstRecoveryBuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food.NutritionRecoveryBuff;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodEatenEffect extends AbilityEffect<PlayerItemConsumeEvent> {

  private final double nutrition;
  private final double water;

  public FoodEatenEffect(double nutrition, double water) {
    super(AbilityTrigger.CONSUME_ITEM);
    this.nutrition = nutrition;
    this.water = water;
  }

  @Override
  public void cast(PlayerItemConsumeEvent element) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(element.getPlayer());

    NutritionRecoveryBuff nutritionRecovery = new NutritionRecoveryBuff(nutrition);
    ThirstRecoveryBuff thirstRecovery = new ThirstRecoveryBuff(water);

    revenantPlayer.addAbility(nutritionRecovery);
    revenantPlayer.addAbility(thirstRecovery);
  }

}
