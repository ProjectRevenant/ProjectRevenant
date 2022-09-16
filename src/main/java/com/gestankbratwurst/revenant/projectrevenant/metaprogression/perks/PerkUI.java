package com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
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
    return null;
  }

  private GUIItem getUpIcon() {
    if(yOffset == 0) {
      return fillerItem;
    }
    return null;
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

              }
            }).build();
  }

  private GUIItem getFillerIcon() {
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.SPACING.getItem()).name(Component.text("")).build())
            .eventConsumer(event -> {
            }).build();
  }

}
