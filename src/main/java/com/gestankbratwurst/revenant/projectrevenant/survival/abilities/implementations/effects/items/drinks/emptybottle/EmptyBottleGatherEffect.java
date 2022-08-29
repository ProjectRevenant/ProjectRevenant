package com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.drinks.emptybottle;

import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityHandle;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment.WaterType;
import com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment.WorldEnvironmentFetcher;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

public class EmptyBottleGatherEffect extends AbilityEffect<PlayerInteractEvent> {
  public EmptyBottleGatherEffect() {
    super(AbilityTrigger.PLAYER_INTERACT, "fill-bottle-effect");
  }

  @Override
  public void cast(PlayerInteractEvent element) {
    if (!(element.getAction() == Action.RIGHT_CLICK_BLOCK || element.getAction() == Action.LEFT_CLICK_AIR)) {
      return;
    }
    RayTraceResult result = element.getPlayer().rayTraceBlocks(2.25, FluidCollisionMode.ALWAYS);
    if (result == null) {
      return;
    }
    Block block = result.getHitBlock();
    if (block == null) {
      return;
    }
    ItemStack usedItem = element.getItem();
    if (usedItem == null) {
      return;
    }
    if (AbilityHandle.getFrom(usedItem).stream().noneMatch(ability -> ability.getIdentifier().equals(RevenantAbility.EMPTY_BOTTLE_FILL))) {
      return;
    }
    if (block.getType() == Material.WATER_CAULDRON) {
      fillBottle(element.getPlayer(), block.getLocation().add(0.5, 0.5, 0.5), element.getHand(), WaterType.CLEAR);
    } else if (block.getType() == Material.WATER) {
      WaterType waterType = WorldEnvironmentFetcher.getWaterTypeAt(block.getLocation());
      fillBottle(element.getPlayer(), block.getLocation().add(0.5, 0.5, 0.5), element.getHand(), waterType);
    }
  }

  private void fillBottle(Player player, Location location, EquipmentSlot equipmentSlot, WaterType waterType) {
    ItemStack filled = switch (waterType) {
      case MURKY:
        yield RevenantItem.murkyWaterBottle();
      case SALTY:
        yield RevenantItem.saltyWaterBottle();
      case CLEAR:
        yield RevenantItem.clearWaterBottle();
    };
    player.getInventory().setItem(equipmentSlot, filled);
    player.playSound(location, Sound.ITEM_BOTTLE_FILL, 0.8F, 1F);
  }

}
