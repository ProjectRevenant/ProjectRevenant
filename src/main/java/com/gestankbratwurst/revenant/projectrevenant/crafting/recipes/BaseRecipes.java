package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.RevenantIngredient;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.CompoundLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.ScoreLoot;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.score.ScoreType;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.UUID;

@AllArgsConstructor
public enum BaseRecipes {

  DUMMY(IngredientRecipe.builder()
          .setRecipeId(UUID.fromString("70f50997-d8a6-4677-841c-bdb60195aa73"))
          .setName("Debug")
          .addIcon(ItemBuilder.of(RevenantItem.dummyFood()).name("Dummy Food Rezept").build())
          .setType(RecipeType.BAKED)
          .setResult(new CompoundLoot()
                  .addLoot(new SimpleItemLoot(ItemBuilder.of(RevenantItem.dummyFood()).amount(16).build()))
                  .addLoot(new ScoreLoot(ScoreType.CRAFTED_ITEMS, 15)))
          .setCraftTime(Duration.ofSeconds(5))
          .addIngredient(new RevenantIngredient(RevenantItem.dummyBow()), 1)
          .addIngredient(new RevenantIngredient(RevenantItem.tool()), 1)
          .build(), true),
  WATER_PURIFICATION(IngredientRecipe.builder()
          .setRecipeId(UUID.fromString("70f50997-d8a6-4677-841c-bdb60195aa74"))
          .setName("§7Trinkwasser")
          .addIcon(RevenantItem.clearWaterBottle())
          .setType(RecipeType.BREWED)
          .setResult(new CompoundLoot()
                  .addLoot(new SimpleItemLoot(RevenantItem.clearWaterBottle()))
                  .addLoot(new SimpleItemLoot(RevenantItem.emptyWaterBottle()))
                  .addLoot(new ScoreLoot(ScoreType.CRAFTED_ITEMS, 15)))
          .setCraftTime(Duration.ofSeconds(20))
          .addIngredient(new RevenantIngredient(RevenantItem.murkyWaterBottle()), 1)
          .build(), true);

  @Getter
  private final RevenantRecipe revenantRecipe;
  @Getter
  private final boolean startingRecipe;


}