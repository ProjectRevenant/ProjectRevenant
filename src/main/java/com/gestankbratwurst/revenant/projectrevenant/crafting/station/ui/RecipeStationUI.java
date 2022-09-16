package com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.core.mmcore.util.common.UtilPlayer;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.AbstractRecipeStation;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.score.ScoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class RecipeStationUI<T extends AbstractRecipeStation> extends AbstractGUIInventory {

  @Getter
  private final T station;
  private final TextureModel uiModel;

  @Override
  protected void preClose(InventoryCloseEvent event) {
    station.unregisterViewer((Player) event.getPlayer());
  }

  @Override
  protected Inventory createInventory(Player player) {
    return Bukkit.createInventory(null, 6 * 9, Component.text("§f" + TextureModel.PIXEL.getChar() + uiModel.getChar()));
  }

  @Override
  protected void init(Player player) {
    int x = 1;
    int y = 1;
    for (IngredientRecipe recipe : getViableRecipeList()) {
      GUIItem recipeIcon = recipeIcon(player, recipe);
      setGUIItem(y * 9 + x, recipeIcon);
      if (++x == 7) {
        x = 1;
        if (++y == 5) {
          throw new IllegalStateException("Too many recipes in UI");
        }
      }
    }
    setGUIItem(8, getClosingIcon());
  }

  protected List<IngredientRecipe> getViableRecipeList() {
    return station.getRecipeList();
  }

  protected GUIItem getClosingIcon() {
    return GUIItem.builder()
            .eventConsumer(event -> {
              UtilPlayer.playUIClick((Player) event.getWhoClicked());
              event.getWhoClicked().closeInventory();
            }).iconCreator(unused -> ItemBuilder.of(TextureModel.EMPTY.getItem())
                    .name(Component.text("Schließen").style(Style.style(NamedTextColor.RED, TextDecoration.ITALIC.withState(false))))
                    .build())
            .build();
  }

  protected GUIItem recipeIcon(Player player, IngredientRecipe recipe) {
    return recipeIcon(player, recipe, event -> {
      if (recipe.canCraft(player)) {
        station.activateWorkload(recipe, player);
        Msg.sendInfo(player, "Es wird jetzt {} gecraftet.", recipe.getName());
        UtilPlayer.playUIClick(player);
      } else {
        UtilPlayer.playSound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR, 0.75F, 0.5F);
      }
    });
  }

  protected GUIItem recipeIcon(Player player, IngredientRecipe recipe, Consumer<InventoryClickEvent> onClick) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    if (!revenantPlayer.hasRecipeUnlocked(recipe.getId())) {
      return GUIItem.builder()
              .eventConsumer(event -> {
              }).iconCreator(unused -> ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("Unbekannt").style(Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC.withState(false)))).build())
              .build();
    } else {
      return GUIItem.builder()
              .eventConsumer(onClick).iconCreator(unused -> recipe.infoIconFor(player)).build();
    }
  }

}
