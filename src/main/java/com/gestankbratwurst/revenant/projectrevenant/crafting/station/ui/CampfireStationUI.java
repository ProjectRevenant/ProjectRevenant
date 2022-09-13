package com.gestankbratwurst.revenant.projectrevenant.crafting.station.ui;

import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilPlayer;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.RevenantIngredient;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.IngredientRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RecipeType;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.implementation.PlayerCampfireStation;
import com.gestankbratwurst.revenant.projectrevenant.loot.drops.SimpleItemLoot;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class CampfireStationUI extends RecipeStationUI<PlayerCampfireStation> {

  public CampfireStationUI(PlayerCampfireStation station) {
    super(station, TextureModel.CAMPFIRE_CRAFTING_UI);
  }

  @Override
  protected void init(Player player) {
    super.init(player);
    this.setGUIItem(47, campfireToggleIcon());
    this.setGUIItem(50, campfireFeedIcon(player));
  }

  @Override
  protected List<IngredientRecipe> getViableRecipeList() {
    if (getStation().isLit()) {
      super.getViableRecipeList();
    }
    return super.getViableRecipeList().stream().filter(recipe -> recipe.getType() == RecipeType.CRAFTED).toList();
  }

  private GUIItem campfireFeedIcon(Player player) {
    PlayerCampfireStation station = getStation();

    return GUIItem.builder()
            .eventConsumer(event -> {
              RevenantRecipe recipe = IngredientRecipe.builder()
                      .setType(RecipeType.CRAFTED)
                      .setRecipeId(UUID.randomUUID())
                      .setCraftTime(Duration.ZERO)
                      .setName("_INTERNAL_CAMPFIRE_CONSUMPTION_")
                      .addIcon(TextureModel.RED_X.getItem())
                      .setResult(new SimpleItemLoot(new ItemStack(Material.STICK)))
                      .addIngredient(new RevenantIngredient(RevenantItem.commonWood()), 1)
                      .build();
              if (recipe.canCraft(player)) {
                station.addLifetime(PlayerCampfireStation.additionalLifetime);
              } else {
                UtilPlayer.playSound(player, Sound.BLOCK_NOTE_BLOCK_GUITAR, 0.75F, 0.5F);
                return;
              }
              recipe.payResources(player);

              UtilPlayer.playSound(player, Sound.BLOCK_CAMPFIRE_CRACKLE);
              UtilPlayer.playSound(player, Sound.ENTITY_BLAZE_SHOOT, 0.2f, 0.66f);

              station.getViewers().forEach(viewer -> new CampfireStationUI(station).openFor(viewer));

            }).iconCreator(unused -> {
              long lifetimeSeconds = station.getLifetime().getSeconds();
              return ItemBuilder.of(Material.OAK_WOOD)
                      .lore("")
                      .lore("§fAktuelle Brennzeit: " + "§e" + (lifetimeSeconds >= 60 ? lifetimeSeconds / 60 + " Minuten" : lifetimeSeconds + "s"))
                      .lore("")
                      .lore("§fKlicke um Holz")
                      .lore("§fin das Feuer zu werfen")
                      .lore("§fund die Zeit um")
                      .lore("§e" + (PlayerCampfireStation.additionalLifetime / 1000 / 60) + " Minuten §fzu erhöhen.")
                      .build();
            })
            .autoUpdated(true)
            .build();
  }

  @EventHandler
  public void onDrop(BlockDropItemEvent event) {
    if (event.getBlockState() instanceof BlockInventoryHolder) {
      Material self = event.getBlockState().getType();
      for (Item item : event.getItems()) {
        if (item.getItemStack().getType() == self) {
          item.remove();
        }
      }
    }
  }

  private GUIItem campfireToggleIcon() {
    PlayerCampfireStation station = getStation();
    return GUIItem.builder()
            .eventConsumer(event -> {
              Player player = (Player) event.getWhoClicked();
              UtilPlayer.playUIClick(player);
              station.setLit(!station.isLit());

              if (station.isLit()) {
                station.setLifetimeFromNow(System.currentTimeMillis() + station.getLifetime().toMillis() / 2);
              } else {
                station.setLifetimeFromNow(System.currentTimeMillis() + station.getLifetime().toMillis() * 2);
              }

              station.getViewers().forEach(viewer -> new CampfireStationUI(station).openFor(viewer));
            })
            .iconCreator(player -> {
              if (station.isLit()) {
                return ItemBuilder.of(TextureModel.CAMPFIRE_ON.getItem())
                        .clearLore()
                        .lore("")
                        .lore("§fLösche das Lagerfeuer.")
                        .lore("§fDies verdoppelt die")
                        .lore("§fverbleibende Zeit")
                        .build();
              } else {
                return ItemBuilder.of(TextureModel.CAMPFIRE_OFF.getItem())
                        .clearLore()
                        .lore("")
                        .lore("§fEntzünde das Lagerfeuer")
                        .lore("§fum craften zu können.")
                        .lore("§fDies halbiert die")
                        .lore("§fverbleibende Zeit")
                        .build();
              }
            })
            .build();
  }

}
