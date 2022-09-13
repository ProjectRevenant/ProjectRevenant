package com.gestankbratwurst.revenant.projectrevenant.ui.gui;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.core.mmcore.util.common.UtilPlayer;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.RevenantIngredient;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RecipeType;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.PlayerCampfireStation;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.UUID;

public class PlayerMenuGUI extends AbstractGUIInventory {

  @Override
  protected Inventory createInventory(Player player) {
    return Bukkit.createInventory(null, 6 * 9, Component.text("Optionen"));
  }

  @Override
  protected void init(Player player) {
    setGUIItem(10, getCampfireIcon());
  }

  private GUIItem getCampfireIcon() {
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.CAMPFIRE_OFF.getItem())
                    .name(Component.text("§fLagerfeuer platzieren"))
                    .lore("")
                    .lore("§7Versorgt dich mit Wärme")
                    .lore("§7und primitiven")
                    .lore("§7Craftmöglichkeiten")
                    .lore("")
                    .lore("§fx4 " + TextureModel.ofItemStack(RevenantItem.commonWood()).getChar() + " Holz")
                    .build())
            .eventConsumer(event -> {
              Player player = (Player) event.getWhoClicked();

              RevenantRecipe recipe = IngredientRecipe.builder()
                      .setType(RecipeType.CRAFTED)
                      .setRecipeId(UUID.randomUUID())
                      .setCraftTime(Duration.ZERO)
                      .setName("_INTERNAL_CAMPFIRE_")
                      .addIcon(TextureModel.CAMPFIRE_OFF.getItem())
                      .setResult(new SimpleItemLoot(new ItemStack(Material.STICK)))
                      .addIngredient(new RevenantIngredient(RevenantItem.commonWood()), 4)
                      .build();

              if(!recipe.canCraft(player)) {
                UtilPlayer.playSound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR, 0.75F, 0.5F);
                return;
              }

              recipe.payResources(player);
              UtilPlayer.playUIClick(player);
              Block block = player.getLocation().getBlock();
              ProjectRevenant.getCraftingStationManager().createStation(block, new PlayerCampfireStation(Position.at(block)));
              Msg.sendInfo(player, "Du hast ein {} aufgebaut.", "Lagerfeuer");

            }).build();
  }

}
