package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.RevenantIngredient;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.UUID;

@AllArgsConstructor
public enum BaseRecipe {

  DUMMY(IngredientRecipe.builder()
          .setRecipeId(UUID.fromString("70f50997-d8a6-4677-841c-bdb60195aa73"))
          .setName("Debug")
          .addIcon(RevenantItem.dummyFood())
          .setResult(new SimpleItemLoot(ItemBuilder.of(RevenantItem.dummyFood()).amount(16).build()))
          .setCraftTime(Duration.ofSeconds(5))
          .addIngredient(new RevenantIngredient(RevenantItem.dummyBow()), 1)
          .addIngredient(new RevenantIngredient(RevenantItem.dummySword()), 1)
          .build(), true);

  @Getter
  private final RevenantRecipe revenantRecipe;
  @Getter
  private final boolean startingRecipe;

}
