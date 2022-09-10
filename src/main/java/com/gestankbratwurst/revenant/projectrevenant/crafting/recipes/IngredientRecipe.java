package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.Ingredient;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Builder
@AllArgsConstructor
public class IngredientRecipe implements RevenantRecipe {

  private final UUID recipeId;
  private final Loot result;
  private final Object2IntMap<Ingredient> ingredientMap;
  private final List<Predicate<Player>> conditions;
  private final ItemStack icon;
  private final Duration craftTime;

  public IngredientRecipe(UUID recipeId, Loot result, ItemStack icon, Duration craftTime) {
    this.recipeId = recipeId;
    this.result = result;
    this.icon = icon;
    this.craftTime = craftTime;
    this.ingredientMap = new Object2IntOpenHashMap<>();
    this.conditions = new ArrayList<>();
    addCondition(this::hasIngredients);
  }

  public IngredientRecipe setIngredient(Ingredient ingredient, int amount) {
    ingredientMap.put(ingredient, amount);
    return this;
  }

  public IngredientRecipe addCondition(Predicate<Player> condition) {
    conditions.add(condition);
    return this;
  }

  private boolean hasIngredients(Player player) {
    return false;
  }

  @Override
  public UUID getId() {
    return recipeId;
  }

  @Override
  public Loot getResult() {
    return result;
  }

  @Override
  public ItemStack infoIconFor(Player player) {
    ItemBuilder builder = new ItemBuilder(icon);
    builder.lore("");
    ingredientMap.forEach((ingredient, amount) -> builder.lore(Component.text("§fx" + amount + " ").append(ingredient.getInfo(player))));
    return icon;
  }

  @Override
  public boolean canCraft(Player player) {
    return conditions.stream().allMatch(condition -> condition.test(player));
  }

  @Override
  public Duration getCraftTime() {
    return craftTime;
  }

  @Override
  public void payResources(Player player) {
    ingredientMap.forEach((ingredient, amount) -> {
      for (int i = 0; i < amount; i++) {
        removeIngredient(player, ingredient);
      }
    });
  }

  private void removeIngredient(Player player, Ingredient ingredient) {
    for (ItemStack itemStack : player.getInventory()) {
      if (ingredient.test(itemStack)) {
        itemStack.subtract();
        return;
      }
    }
  }

}
