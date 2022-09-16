package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.UtilPlayer;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PerkUI extends AbstractGUIInventory {

  private final PerkType perkType;
  private final GUIItem fillerItem;
  private int yOffset = 0;

  public PerkUI(PerkType perkType) {
    this.perkType = perkType;
    this.fillerItem = getFillerIcon();
  }

  @Override
  protected Inventory createInventory(Player player) {
    return Bukkit.createInventory(null, 6 * 9, Component.text(perkType.getDisplayName()));
  }

  @Override
  protected void init(Player player) {
    int inventoryIndex = 0;
    for (int y = 0; y < 6; y++) {
      for (int x = 0; x < 9; x++) {
        int id = PerkLayout.getIdAt(perkType, x, y + yOffset);
        GUIItem icon;
        if (id == 0) {
          icon = fillerItem;
        } else {
          Perk<?> perk = PerkLayout.getPerkOfId(perkType, id);
          icon = getPerkIcon(perk, player);
        }
        this.setGUIItem(inventoryIndex++, icon);
      }
    }
  }

  private GUIItem getPerkIcon(Perk<?> perk, Player player) {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    PerkAbility perkAbility = perk.getPerkAbilitySupplier().get();
    boolean hasPoints = revenantPlayer.getAvailablePerkPoints() >= perk.getCost();
    ItemBuilder builder = new ItemBuilder(perkAbility.getModel().getItem());
    String appendix = revenantPlayer.hasPerk(perk) ? " §a[§fFreigeschaltet§a]" : "";
    builder.name(perkAbility.getInfoTitle(player).append(Component.text(appendix)));
    builder.lore("");
    builder.lore(perkAbility.getInfos(player));
    if(revenantPlayer.hasPerk(perk)) {
      builder.lore("");
      builder.lore("§7Bereits freigeschaltet");
    } else {
      builder.lore("");
      builder.lore("§6Kosten: " + (hasPoints ? "§a" : "§c") + perk.getCost() + " Punkte");
    }

    return null;
  }

  private GUIItem getUpIcon() {
    if(yOffset == 0) {
      return fillerItem;
    }
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.DOUBLE_GRAY_ARROW_DOWN.getItem())
                    .name(Component.text("§7Aufwärts scrollen"))
                    .build())
            .eventConsumer(event -> {
              if(yOffset == 0) {
                return;
              }
              yOffset--;
              UtilPlayer.playUIClick((Player) event.getWhoClicked());
              this.update((Player) event.getWhoClicked());
            }).build();
  }

  private GUIItem getDownIcon() {
    if(yOffset == PerkLayout.getMaxYOffset(perkType)) {
      return fillerItem;
    }
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.DOUBLE_GRAY_ARROW_DOWN.getItem())
                    .name(Component.text("§7Abwärts scrollen"))
                    .build())
            .eventConsumer(event -> {
              if(yOffset == PerkLayout.getMaxYOffset(perkType)) {
                return;
              }
              yOffset++;
              UtilPlayer.playUIClick((Player) event.getWhoClicked());
              this.update((Player) event.getWhoClicked());
            }).build();
  }

  private GUIItem getFillerIcon() {
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.SPACING.getItem()).name(Component.text("")).build())
            .eventConsumer(event -> {
            }).build();
  }

}
