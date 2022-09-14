package com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui;

import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilPlayer;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.RevenantIngredient;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RecipeType;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.CookingStation;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class CookingStationUI extends RecipeStationUI<CookingStation> {

  private static final TextureModel[] waterLevelTextures = {
          TextureModel.COOKING_UI_0,
          TextureModel.COOKING_UI_1,
          TextureModel.COOKING_UI_2
  };

  public CookingStationUI(CookingStation station) {
    super(station, waterLevelTextures[station.getWaterLevel()]);
  }

  @Override
  protected void init(Player player) {
    super.init(player);
    this.setGUIItem(47, getWaterAdditionIcon());
  }

  @Override
  protected List<IngredientRecipe> getViableRecipeList() {
    if (getStation().getWaterLevel() > 0) {
      return super.getViableRecipeList();
    }
    return super.getViableRecipeList().stream().filter(recipe -> recipe.getType() != RecipeType.COOKED).toList();
  }

  private GUIItem getWaterAdditionIcon() {
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.CLEAR_WATER_BOTTLE.getItem())
                    .name(Component.text("§6Wasser auffüllen"))
                    .lore(Component.text(""))
                    .lore(Component.text("§7Klicke hier, um §e1L§7 Wasser aufzufüllen."))
                    .lore(Component.text("§7Einige Rezepte benötigen Wasser zum kochen."))
                    .applyAsPotion(potionMeta -> potionMeta.setColor(Color.WHITE))
                    .build())
            .eventConsumer(event -> {
              Player player = (Player) event.getWhoClicked();

              if(getStation().getWaterLevel() >= CookingStation.MAX_WATER_LEVEL) {
                return;
              }

              RevenantRecipe recipe = IngredientRecipe.builder()
                      .setType(RecipeType.CRAFTED)
                      .setRecipeId(UUID.randomUUID())
                      .setCraftTime(Duration.ZERO)
                      .setName("_INTERNAL_COOKING_CONSUMPTION_")
                      .addIcon(TextureModel.RED_X.getItem())
                      .setResult(new SimpleItemLoot(new ItemStack(Material.STICK)))
                      .addIngredient(new RevenantIngredient(RevenantItem.clearWaterBottle()), 1)
                      .build();

              if (recipe.canCraft(player)) {
                getStation().incrementWaterLevel();
              } else {
                UtilPlayer.playSound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR, 0.75F, 0.5F);
                return;
              }

              recipe.payResources(player);
              UtilPlayer.playSound(player, Sound.ENTITY_GENERIC_SPLASH, 0.5f, 0.66f);

              getStation().getViewers().forEach(viewer -> new CookingStationUI(getStation()).openFor(viewer));
            }).build();
  }

}
