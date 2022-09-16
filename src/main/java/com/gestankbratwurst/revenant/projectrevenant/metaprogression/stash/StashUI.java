package com.gestankbratwurst.revenant.projectrevenant.metaprogression.stash;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StashUI extends AbstractGUIInventory {

  private final RevenantPlayer owner;
  private final int stashSlots;
  private final int inventorySize;
  private final int blockedSlots;

  public StashUI(RevenantPlayer owner) {
    this.owner = owner;
    this.stashSlots = owner.getStashSize();
    this.inventorySize = ((stashSlots / 9) + 1) * 9;
    this.blockedSlots = inventorySize - stashSlots;
  }

  @Override
  protected Inventory createInventory(Player player) {
    return Bukkit.createInventory(null, inventorySize, player.name().append(Component.text("'s Stash")));
  }

  @Override
  protected void init(Player player) {
    for (int i = inventorySize - 1; i >= inventorySize - blockedSlots; i--) {
      setGUIItem(i, getFillerItem());
    }
  }

  @Override
  public void handleOpen(InventoryOpenEvent event) {
    List<ItemStack> stashItems = owner.getStashItems();
    Inventory inventory = event.getInventory();
    super.handleOpen(event);

    for (int i = 0; i < stashItems.size(); i++) {
      inventory.setItem(i, stashItems.get(i));
    }
  }

  @Override
  protected void handleSecondaryInventoryClick(InventoryClickEvent event) {

  }

  @Override
  protected void preClose(InventoryCloseEvent event) {
    Inventory inventory = event.getInventory();
    List<ItemStack> stashedItems = new ArrayList<>(Arrays.asList(inventory.getContents())).subList(0, stashSlots);
    owner.setStashItems(stashedItems);
  }

  private GUIItem getFillerItem() {
    return GUIItem.builder()
            .iconCreator(player -> ItemBuilder.of(TextureModel.SPACING.getItem())
                    .name("§7Blockierter Slot")
                    .clearLore()
                    .lore("")
                    .lore("§eSchalte diesen Slot mit Perk-Punken frei!")
                    .build())
            .eventConsumer(event -> {
            }).build();
  }

}
