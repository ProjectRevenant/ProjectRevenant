package com.gestankbratwurst.revenant.projectrevenant.crafting.recipes;

import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.Ingredient;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.Loot;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class IngredientRecipe implements RevenantRecipe {

  public static IngredientRecipeBuilder builder() {
    return new IngredientRecipeBuilder();
  }

  private final UUID recipeId;
  private final String name;
  private final RecipeType type;
  private final Loot result;
  private final Object2IntMap<Ingredient> ingredientMap;
  private final List<Predicate<Player>> conditions;
  private final ItemStack icon;
  private final long craftTime;


  public IngredientRecipe(UUID recipeId, String name, RecipeType type, Loot result, ItemStack icon, Duration craftTime) {
    this.recipeId = recipeId;
    this.name = name;
    this.type = type;
    this.result = result;
    this.icon = icon;
    this.craftTime = craftTime.toMillis();
    this.ingredientMap = new Object2IntOpenHashMap<>();
    this.conditions = new ArrayList<>();
    this.conditions.add(this::hasIngredients);
  }

  private boolean hasIngredients(Player player) {
    return getMissingIngredients(player).isEmpty();
  }

  public Map<Ingredient, Integer> getMissingIngredients(Player player) {
    Map<Ingredient, Integer> missing = new HashMap<>();
    Map<ItemStack, Integer> playerContent = new HashMap<>();

    for (ItemStack itemStack : player.getInventory()) {
      if (itemStack == null) {
        continue;
      }
      playerContent.compute(itemStack.asOne(), (key, curValue) -> curValue == null ? itemStack.getAmount() : (curValue + itemStack.getAmount()));
    }

    Object2IntMap<Ingredient> ingredientSpecs = new Object2IntOpenHashMap<>(ingredientMap);

    ingredientLoop:
    for (Object2IntMap.Entry<Ingredient> ingredientEntry : ingredientSpecs.object2IntEntrySet()) {
      Ingredient ingredient = ingredientEntry.getKey();
      for (Map.Entry<ItemStack, Integer> contentEntry : playerContent.entrySet()) {
        ItemStack itemStack = contentEntry.getKey();
        if (ingredient.test(itemStack)) {
          int ingredientAmount = ingredientEntry.getIntValue();
          int contentAmount = contentEntry.getValue();
          if (contentAmount >= ingredientAmount) {
            contentEntry.setValue(contentAmount - ingredientAmount);
            ingredientEntry.setValue(0);
            continue ingredientLoop;
          } else {
            contentEntry.setValue(0);
            ingredientEntry.setValue(ingredientAmount - contentAmount);
          }
        }
      }

      if(ingredientEntry.getIntValue() > 0) {
        missing.put(ingredient, ingredientEntry.getIntValue());
      }
    }

    return missing;
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
    ItemBuilder builder = new ItemBuilder(icon.clone());
    builder.clearLore();
    builder.name(name);
    builder.lore("");
    Map<Ingredient, Integer> missing = getMissingIngredients(player);
    ingredientMap.forEach((ingredient, amount) -> {
      Component component = Component.text("§fx" + amount + " ").append(ingredient.getInfo(player));
      if (missing.containsKey(ingredient)) {
        component = component.style(Style.style(TextDecoration.STRIKETHROUGH));
      }
      builder.lore(component);
    });
    return builder.build();
  }

  @Override
  public boolean canCraft(Player player) {
    return conditions.stream().allMatch(condition -> condition.test(player));
  }

  @Override
  public Duration getCraftTime() {
    return Duration.ofMillis(craftTime);
  }

  @Override
  public void payResources(Player player) {
    ingredientMap.forEach((ingredient, amount) -> {
      if(ingredient.isConsumed()) {
        for (int i = 0; i < amount; i++) {
          removeIngredient(player, ingredient);
        }
      }
    });
  }

  @Override
  public String getName() {
    return name;
  }

  private void removeIngredient(Player player, Ingredient ingredient) {
    for (ItemStack itemStack : player.getInventory()) {
      if (ingredient.test(itemStack)) {
        itemStack.subtract();
        return;
      }
    }
    throw new IllegalStateException("Could not remove ingredient: " + ingredient.getInfo(player));
  }

  @Override
  public RecipeType getType(){
    return type;
  }

  public static class IngredientRecipeBuilder {
    private UUID recipeId;
    private String name;
    private RecipeType type;
    private Loot result;
    private ItemStack icon;
    private Duration craftTime;
    private final Object2IntMap<Ingredient> ingredientMap;
    private final List<Predicate<Player>> conditions;

    public IngredientRecipeBuilder() {
      this.ingredientMap = new Object2IntOpenHashMap<>();
      this.conditions = new ArrayList<>();
    }

    public IngredientRecipeBuilder setRecipeId(UUID uuid) {
      this.recipeId = uuid;
      return this;
    }

    public IngredientRecipeBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public IngredientRecipeBuilder setType(RecipeType type){
      this.type = type;
      return this;
    }

    public IngredientRecipeBuilder setResult(Loot result) {
      this.result = result;
      return this;
    }

    public IngredientRecipeBuilder addIcon(ItemStack icon) {
      this.icon = icon;
      return this;
    }

    public IngredientRecipeBuilder setCraftTime(Duration duration) {
      this.craftTime = duration;
      return this;
    }


    public IngredientRecipeBuilder addIngredient(Ingredient ingredient, int amount) {
      ingredientMap.compute(ingredient, (key, currentAmount) -> currentAmount == null ? amount : amount + currentAmount);
      return this;
    }

    public IngredientRecipeBuilder addCondition(Predicate<Player> condition) {
      conditions.add(condition);
      return this;
    }


    public IngredientRecipe build() {
      if (Stream.of(recipeId, name, result, icon, craftTime).anyMatch(Objects::isNull)) {
        throw new IllegalStateException("Illegal Recipe: " + (name == null ? "missing_name" : name));
      }

      IngredientRecipe recipe = new IngredientRecipe(recipeId, name, type, result, icon, craftTime);
      recipe.ingredientMap.putAll(this.ingredientMap);
      recipe.conditions.addAll(this.conditions);
      return recipe;
    }

  }

}
