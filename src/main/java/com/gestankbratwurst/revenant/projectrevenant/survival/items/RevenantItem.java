package com.gestankbratwurst.revenant.projectrevenant.survival.items;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityHandle;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.clearwaterbottle.ClearBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.emptybottle.EmptyBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.murkywaterbottle.MurkyBottleDrinkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.saltpoisoning.SaltPoisoningAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.saltywaterbottle.SaltyBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.effects.items.saltybottle.SaltyBottleDrinkEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RevenantItem {

  private static final Map<String, Supplier<ItemStack>> namedCreationMap = Map.copyOf(
          new HashMap<>() {{
            put("TEST", RevenantItem::testItem);
            put("EMPTY_WATER_BOTTLE", RevenantItem::emptyWaterBottle);
            put("CLEAR_WATER_BOTTLE", RevenantItem::clearWaterBottle);
            put("MURKY_WATER_BOTTLE", RevenantItem::murkyWaterBottle);
            put("SALT_WATER_BOTTLE", RevenantItem::saltyWaterBottle);
          }}
  );

  public static List<String> getInternalNames() {
    return List.copyOf(namedCreationMap.keySet());
  }

  public static ItemStack getItemByInternalName(String name) {
    return namedCreationMap.getOrDefault(name, RevenantItem::testItem).get();
  }

  public static ItemStack testItem() {
    return TextureModel.RED_X.getItem();
  }

  private static ItemStack basic(TextureModel model, String name, ItemRarity rarity, Ability... abilities) {
    ItemStack itemStack = model.getItem();
    AbilityHandle.addTo(itemStack, abilities);
    rarity.applyTo(itemStack);
    ItemMeta meta = itemStack.getItemMeta();
    meta.displayName(Component.text(name).style(Style.style(rarity.getColor())));
    itemStack.setItemMeta(meta);
    return itemStack;
  }

  private static ItemStack basicBottle(TextureModel model, String name, ItemRarity rarity, Ability... abilities) {
    ItemStack itemStack = basic(model, name, rarity, abilities);
    PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
    meta.setColor(Color.WHITE);
    itemStack.setItemMeta(meta);
    return itemStack;
  }

  public static ItemStack emptyWaterBottle() {
    return basic(TextureModel.EMPTY_WATER_BOTTLE, "Leere Wasserflasche", ItemRarity.COMMON, new EmptyBottleAbility());
  }

  public static ItemStack clearWaterBottle() {
    return basicBottle(TextureModel.CLEAR_WATER_BOTTLE, "Flasche mit klarem Wasser", ItemRarity.UNCOMMON, new ClearBottleAbility());
  }

  public static ItemStack murkyWaterBottle() {
    return basicBottle(TextureModel.MURKY_WATER_BOTTLE, "Flasche mit trübem Wasser", ItemRarity.COMMON, new MurkyBottleDrinkAbility());
  }

  public static ItemStack saltyWaterBottle() {
    return basicBottle(TextureModel.SALT_WATER_BOTTLE, "Flasche mit salzigem Wasser", ItemRarity.COMMON, new SaltyBottleAbility());
  }

}
