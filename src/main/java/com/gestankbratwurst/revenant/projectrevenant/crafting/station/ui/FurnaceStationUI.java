package com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui;

import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilPlayer;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.RevenantIngredient;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.FurnaceRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RecipeType;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.FurnaceStation;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class FurnaceStationUI extends RecipeStationUI<FurnaceStation> {

  private static final TextureModel[] tempTextures = {
          TextureModel.FURNACE_CRAFTING_UI_0,
          TextureModel.FURNACE_CRAFTING_UI_1,
          TextureModel.FURNACE_CRAFTING_UI_2,
          TextureModel.FURNACE_CRAFTING_UI_3,
          TextureModel.FURNACE_CRAFTING_UI_4,
          TextureModel.FURNACE_CRAFTING_UI_5,
          TextureModel.FURNACE_CRAFTING_UI_6
  };

  public FurnaceStationUI(FurnaceStation station) {
    super(station, tempTextures[station.getHeatLevel()]);
  }

  @Override
  protected void init(Player player) {
    super.init(player);
    this.setGUIItem(47, getFurnaceBlowIcon());
  }

  @Override
  protected List<IngredientRecipe> getViableRecipeList() {
    return super.getViableRecipeList().stream().filter(recipe -> {
      if (recipe instanceof FurnaceRecipe furnaceRecipe) {
        return getStation().getHeatLevel() >= furnaceRecipe.getRequiredHeatLevel();
      }
      return true;
    }).toList();
  }

  private GUIItem getFurnaceBlowIcon() {

    return GUIItem.builder()
            .iconCreator(player -> {
              FurnaceStation station = getStation();
              TextureModel model = station.getBurnCoolDown() == 0 ? TextureModel.BELLOW_OPEN : TextureModel.BELLOW_CLOSED;
              double percent = station.getCooldownPercent();
              ItemBuilder builder = ItemBuilder.of(model.getItem())
                      .name(Component.text("§6Aufheizen"))
                      .lore("");

              if (model == TextureModel.BELLOW_CLOSED) {
                builder.setDamagePercent(percent);
              }

              if (station.getHeatLevel() < FurnaceStation.MAX_HEAT_LEVEL) {
                builder.lore("§7Du kannst diesen Ofen aufheizen.")
                        .lore("§7Einige Rezepte benötigen eine")
                        .lore("§7höhere Temperatur.")
                        .lore("")
                        .lore("§7Benötigt §ex" + station.getCoalCost() + " Kohle");
              } else {
                builder.lore("§7Der Ofen hat die maximale Hitze erreicht.");
              }
              return builder.build();
            })
            .eventConsumer(event -> {
              Player player = (Player) event.getWhoClicked();

              RevenantRecipe recipe = IngredientRecipe.builder()
                      .setType(RecipeType.CRAFTED)
                      .setRecipeId(UUID.randomUUID())
                      .setCraftTime(Duration.ZERO)
                      .setName("_INTERNAL_FURNACE_CONSUMPTION_")
                      .addIcon(TextureModel.RED_X.getItem())
                      .setResult(new SimpleItemLoot(new ItemStack(Material.STICK)))
                      .addIngredient(new RevenantIngredient(RevenantItem.coal()), getStation().getCoalCost())
                      .build();

              if (recipe.canCraft(player) && getStation().getHeatLevel() < FurnaceStation.MAX_HEAT_LEVEL && getStation().getBurnCoolDown() == 0) {
                getStation().increaseHeatLevel();
              } else {
                UtilPlayer.playSound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR, 0.75F, 0.5F);
                return;
              }

              recipe.payResources(player);
              UtilPlayer.playSound(player, Sound.ENTITY_BLAZE_SHOOT, 0.5F + 0.1F * getStation().getHeatLevel(), 0.5F - 0.033F * getStation().getHeatLevel());

              getStation().getViewers().forEach(viewer -> new FurnaceStationUI(getStation()).openFor(viewer));
            })
            .autoUpdated(true)
            .build();
  }

}
