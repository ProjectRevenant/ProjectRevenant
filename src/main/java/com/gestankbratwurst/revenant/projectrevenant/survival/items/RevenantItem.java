package com.gestankbratwurst.revenant.projectrevenant.survival.items;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityHandle;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor.ChestplateAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor.HelmetAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.ConsumableHealthAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.ConsumablePotionAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.DebuffRemovalAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.SkeletonHealAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.consumables.implementations.ConsumableSpeedBuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.ClearBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.EmptyBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.MurkyBottleDrinkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.SaltPoisoningAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.SaltyBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food.FoodEatenAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.melee.WeaponDamageAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.ranged.RangedDamageAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.LegBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.items.ItemAttributeHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class RevenantItem {

  private static final NamespacedKey randomIdKey = NamespaceFactory.provide("unique-id");
  private static final NamespacedKey revenantInternalKey = NamespaceFactory.provide("revenant-internal-name");
  private static final Map<String, Supplier<ItemStack>> namedCreationMap = Map.copyOf(
          new HashMap<>() {{
            put("TEST", RevenantItem::testItem);
            put("EMPTY_WATER_BOTTLE", RevenantItem::emptyWaterBottle);
            put("CLEAR_WATER_BOTTLE", RevenantItem::clearWaterBottle);
            put("MURKY_WATER_BOTTLE", RevenantItem::murkyWaterBottle);
            put("SALT_WATER_BOTTLE", RevenantItem::saltyWaterBottle);
            put("DUMMY_SWORD", RevenantItem::dummySword);
            put("DUMMY_BOW", RevenantItem::dummyBow);
            put("DUMMY_FOOD", RevenantItem::dummyFood);
            put("DUMMY_HELMET", RevenantItem::dummyHelmet);
            put("DUMMY_CHESTPLATE", RevenantItem::dummyChestplate);
            put("DUMMY_SPEED_POTION", RevenantItem::dummySpeedConsumable);
            put("TOOLS", RevenantItem::tool);
            put("COMMON_WOOD", RevenantItem::commonWood);
          }}
  );

  public static void setUnique(ItemStack itemStack) {
    ItemMeta meta = itemStack.getItemMeta();
    meta.getPersistentDataContainer().set(randomIdKey, PersistentDataType.STRING, UUID.randomUUID().toString());
    itemStack.setItemMeta(meta);
  }

  public static List<String> getInternalNames() {
    return List.copyOf(namedCreationMap.keySet());
  }

  public static ItemStack getItemByInternalName(String name) {
    return namedCreationMap.getOrDefault(name, RevenantItem::testItem).get();
  }

  public static ItemStack testItem() {
    return TextureModel.RED_X.getItem();
  }

  private static ItemStack basic(String internalId, ItemStack baseItem, String name, ItemRarity rarity, double weight, Ability... abilities) {
    return basic(internalId, baseItem, name, rarity, weight, true, abilities);
  }

  private static ItemStack basic(String internalId, ItemStack baseItem, String name, ItemRarity rarity, double weight, boolean stackable, Ability... abilities) {
    AbilityHandle.addTo(baseItem, abilities);
    if(!stackable) {
      setUnique(baseItem);
    }
    ItemWeight.set(baseItem, weight);
    rarity.applyTo(baseItem);
    ItemMeta meta = baseItem.getItemMeta();
    meta.displayName(Component.text(name).style(Style.style(rarity.getColor(), TextDecoration.ITALIC.withState(false))));
    meta.getPersistentDataContainer().set(revenantInternalKey, PersistentDataType.STRING, internalId);
    baseItem.setItemMeta(meta);
    return baseItem;
  }

  private static ItemStack basic(String internalId, TextureModel model, String name, ItemRarity rarity, double weight, Ability... abilities) {
    return basic(internalId, model.getItem(), name, rarity, weight, true, abilities);
  }

  private static ItemStack basic(String internalId, TextureModel model, String name, ItemRarity rarity, double weight, boolean stackable, Ability... abilities) {
    return basic(internalId, model.getItem(), name, rarity, weight, stackable, abilities);
  }

  private static ItemStack meleeWeapon(String internalId, TextureModel model, String name, ItemRarity rarity, double weight, double baseDmg, double baseAtkSpeed, double knockback, Ability... abilities) {
    List<Ability> list = new ArrayList<>(List.of(abilities));
    list.add(new WeaponDamageAbility(baseDmg, baseAtkSpeed, knockback));
    return basic(internalId, model, name, rarity, weight, false, list.toArray(new Ability[0]));
  }

  private static ItemStack rangedWeapon(String internalId, TextureModel model, String name, ItemRarity rarity, double weight, double rangedDmg, double meleeDmg, double meleeAtkSpeed, double meleeKnockback, Ability... abilities) {
    List<Ability> list = new ArrayList<>(List.of(abilities));
    list.add(new RangedDamageAbility(rangedDmg, meleeDmg, meleeAtkSpeed, meleeKnockback));
    return basic(internalId, model, name, rarity, weight, false, list.toArray(new Ability[0]));
  }

  private static ItemStack food(String internalId, TextureModel model, String name, ItemRarity rarity, double weight, double nutrition, double water, boolean stackable, Ability... abilities) {
    List<Ability> list = new ArrayList<>(List.of(abilities));
    list.add(new FoodEatenAbility(nutrition, water));
    return basic(internalId, model, name, rarity, weight, stackable, list.toArray(new Ability[0]));
  }

  private static ItemStack food(String internalId, ItemStack model, String name, ItemRarity rarity, double weight, double nutrition, double water, boolean stackable, Ability... abilities) {
    List<Ability> list = new ArrayList<>(List.of(abilities));
    list.add(new FoodEatenAbility(nutrition, water));
    return basic(internalId, model, name, rarity, weight, stackable, list.toArray(new Ability[0]));
  }

  private static ItemStack basicBottle(String internalId, TextureModel model, String name, ItemRarity rarity, Ability... abilities) {
    ItemStack itemStack = basic(internalId, model, name, rarity, 1.1, abilities);
    PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
    meta.setColor(Color.WHITE);
    itemStack.setItemMeta(meta);
    return itemStack;
  }

  public static boolean sameInternalName(ItemStack source, ItemStack target) {
    if (source == null || target == null) {
      return false;
    }
    ItemMeta sourceMeta = source.getItemMeta();
    ItemMeta targetMeta = target.getItemMeta();
    if (sourceMeta == null || targetMeta == null) {
      return false;
    }
    PersistentDataContainer sourceContainer = sourceMeta.getPersistentDataContainer();
    PersistentDataContainer targetContainer = targetMeta.getPersistentDataContainer();

    return Objects.equals(sourceContainer.get(revenantInternalKey, PersistentDataType.STRING), targetContainer.get(revenantInternalKey, PersistentDataType.STRING));
  }

  //Bottles
  public static ItemStack emptyWaterBottle() {
    return basic("EMPTY_WATER_BOTTLE", TextureModel.EMPTY_WATER_BOTTLE, "Leere Wasserflasche", ItemRarity.COMMON, 0.1, new EmptyBottleAbility());
  }

  public static ItemStack clearWaterBottle() {
    return basicBottle("CLEAR_WATER_BOTTLE", TextureModel.CLEAR_WATER_BOTTLE, "Flasche mit klarem Wasser", ItemRarity.UNCOMMON, new ClearBottleAbility());
  }

  public static ItemStack murkyWaterBottle() {
    return basicBottle("MURKY_WATER_BOTTLE", TextureModel.MURKY_WATER_BOTTLE, "Flasche mit trübem Wasser", ItemRarity.COMMON, new MurkyBottleDrinkAbility());
  }

  public static ItemStack saltyWaterBottle() {
    return basicBottle("SALTY_WATER_BOTTLE", TextureModel.SALT_WATER_BOTTLE, "Flasche mit salzigem Wasser", ItemRarity.COMMON, new SaltyBottleAbility());
  }

  //Melee Weapons

  public static ItemStack dummySword() {
    ItemStack base = meleeWeapon("DUMMY_SWORD", TextureModel.RED_X, "Dummy-Sword", ItemRarity.DEBUG, 0.2, 5, 1.5, 1.0);
    return new ItemBuilder(base)
            .lore("§6[Debug]")
            .lore("§fNur zum testen!")
            .build();
  }

  //Ranged Weapons
  public static ItemStack dummyBow() {
    ItemStack base = rangedWeapon("DUMMY_BOW", TextureModel.RED_X_BOW, "Dummy-Bow", ItemRarity.DEBUG, 0.3, 8, 2, 0.5, 1.0);
    ItemAttributeHandler.setAsTwoHanded(base);
    return new ItemBuilder(base)
            .lore("§6[Debug]")
            .lore("§fNur zum testen!")
            .build();

  }

  //Food
  public static ItemStack dummyFood() {
    ItemStack base = food("DUMMY_FOOD", new ItemStack(Material.BREAD), "Dummy-Essen", ItemRarity.DEBUG, 0.2, 500, 0.5, true, new ConsumableHealthAbility(30, Duration.ofSeconds(30)));
    return new ItemBuilder(base)
            .lore("§6[Debug]")
            .lore("§fNur zum testen!")
            .build();
  }

  //Armor
  public static ItemStack dummyHelmet() {
    ItemStack base = basic("DUMMY_HELMET", new ItemStack(Material.LEATHER_HELMET), "Dummy-Helmet", ItemRarity.DEBUG, 1.5, new HelmetAbility(5, 2, 2));
    return new ItemBuilder(base)
            .lore("§6[Debug]")
            .lore("§fNur zum testen!")
            .build();
  }

  public static ItemStack dummyChestplate() {
    ItemStack base = basic("DUMMY_CHESTPLATE", new ItemStack(Material.LEATHER_CHESTPLATE), "Dummy-Chestplate", ItemRarity.DEBUG, 3, new ChestplateAbility(10, 5, 5));
    return new ItemBuilder(base)
            .lore("§6[Debug]")
            .lore("§fNur zum testen!")
            .build();
  }

  //Consumable
  public static ItemStack dummySpeedConsumable() {
    ConsumablePotionAbility speedAbility = new ConsumablePotionAbility(new ConsumableSpeedBuff(600, 2));
    SkeletonHealAbility healLegAbility = new SkeletonHealAbility(List.of(LegBone.LEFT, LegBone.RIGHT), false);
    DebuffRemovalAbility removeSalt = new DebuffRemovalAbility(List.of(SaltPoisoningAbility.class));
    ConsumableHealthAbility healthBuff = new ConsumableHealthAbility(30, Duration.ofSeconds(30));
    ItemStack base = basic("DUMMY_SPEED_POTION", new ItemStack(Material.POTION), "Dummy-Potion", ItemRarity.DEBUG, 3, healLegAbility, speedAbility, removeSalt, healthBuff);
    return new ItemBuilder(base)
            .lore("§6[Debug]")
            .lore("§fNur zum testen!")
            .build();
  }

  // Resources
  public static ItemStack tool() {
    return basic("TOOLS", TextureModel.TOOLS.getItem(), "Werkzeug", ItemRarity.COMMON, 0.12, true);
  }

  public static ItemStack commonWood() {
    return basic("COMMON_WOOD", TextureModel.RED_X, "Holz", ItemRarity.COMMON, 2, true);
  }

}