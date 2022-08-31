package com.gestankbratwurst.revenant.projectrevenant.survival.items;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityHandle;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor.ChestplateAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.armor.HelmetAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.ClearBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.EmptyBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.MurkyBottleDrinkAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.drinks.SaltyBottleAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food.FoodEatenAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.food.FoodHealthRecoveryBuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.melee.WeaponDamageAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.items.weapons.ranged.RangedDamageAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.items.ItemAttributeHandler;
import com.gestankbratwurst.revenant.projectrevenant.survival.weight.ItemWeight;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.time.Duration;
import java.util.ArrayList;
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
                put("DUMMY_SWORD", RevenantItem::dummySword);
                put("DUMMY_BOW", RevenantItem::dummyBow);
                put("DUMMY_FOOD", RevenantItem::dummyFood);
                put("DUMMY_HELMET", RevenantItem::dummyHelmet);
                put("DUMMY_CHESTPLATE", RevenantItem::dummyChestplate);
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

    private static ItemStack basic(ItemStack baseItem, String name, ItemRarity rarity, double weight, Ability... abilities) {
        AbilityHandle.addTo(baseItem, abilities);
        ItemWeight.set(baseItem, weight);
        rarity.applyTo(baseItem);
        ItemMeta meta = baseItem.getItemMeta();
        meta.displayName(Component.text(name).style(Style.style(rarity.getColor())));
        baseItem.setItemMeta(meta);
        return baseItem;
    }

    private static ItemStack basic(TextureModel model, String name, ItemRarity rarity, double weight, Ability... abilities) {
        return basic(model.getItem(), name, rarity, weight, abilities);
    }

    private static ItemStack meleeWeapon(TextureModel model, String name, ItemRarity rarity, double weight, double baseDmg, double baseAtkSpeed, double knockback, Ability... abilities) {
        List<Ability> list = new ArrayList<>(List.of(abilities));
        list.add(new WeaponDamageAbility(baseDmg, baseAtkSpeed, knockback));
        return basic(model, name, rarity, weight, list.toArray(new Ability[0]));
    }

    private static ItemStack rangedWeapon(TextureModel model, String name, ItemRarity rarity, double weight, double rangedDmg, double meleeDmg, double meleeAtkSpeed, double meleeKnockback, Ability... abilities) {
        List<Ability> list = new ArrayList<>(List.of(abilities));
        list.add(new RangedDamageAbility(rangedDmg, meleeDmg, meleeAtkSpeed, meleeKnockback));
        return basic(model, name, rarity, weight, list.toArray(new Ability[0]));
    }

    private static ItemStack food(TextureModel model, String name, ItemRarity rarity, double weight, double nutrition, double water, Ability... abilities) {
        List<Ability> list = new ArrayList<>(List.of(abilities));
        list.add(new FoodEatenAbility(nutrition, water, 0, Duration.ZERO));
        return basic(model, name, rarity, weight, list.toArray(new Ability[0]));
    }

    private static ItemStack food(ItemStack model, String name, ItemRarity rarity, double weight, double nutrition, double water, Ability... abilities) {
        List<Ability> list = new ArrayList<>(List.of(abilities));
        list.add(new FoodEatenAbility(nutrition, water, 0, Duration.ZERO));
        return basic(model, name, rarity, weight, list.toArray(new Ability[0]));
    }

    private static ItemStack basicBottle(TextureModel model, String name, ItemRarity rarity, Ability... abilities) {
        ItemStack itemStack = basic(model, name, rarity, 1.1, abilities);
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        meta.setColor(Color.WHITE);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    //Bottles

    public static ItemStack emptyWaterBottle() {
        return basic(TextureModel.EMPTY_WATER_BOTTLE, "Leere Wasserflasche", ItemRarity.COMMON, 0.1, new EmptyBottleAbility());
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

    //Melee Weapons

    public static ItemStack dummySword() {
        ItemStack base = meleeWeapon(TextureModel.RED_X, "Dummy-Sword", ItemRarity.DEBUG, 0.2, 5, 1.5, 1.0);
        return new ItemBuilder(base)
                .lore("§6[Debug]")
                .lore("§fNur zum testen!")
                .build();
    }

    //Ranged Weapons
    public static ItemStack dummyBow() {
        ItemStack base = rangedWeapon(TextureModel.RED_X_BOW, "Dummy-Bow", ItemRarity.DEBUG, 0.3, 8, 2, 0.5, 1.0);
        ItemAttributeHandler.setAsTwoHanded(base);
        return new ItemBuilder(base)
                .lore("§6[Debug]")
                .lore("§fNur zum testen!")
                .build();

    }

    //Food
    public static ItemStack dummyFood() {
        ItemStack base = food(new ItemStack(Material.BREAD), "Dummy-Essen", ItemRarity.DEBUG, 0.2, 500, 500, new FoodHealthRecoveryBuff(50, Duration.ofSeconds(30)));
        return new ItemBuilder(base)
                .lore("§6[Debug]")
                .lore("§fNur zum testen!")
                .build();
    }

    //Armor
    public static ItemStack dummyHelmet() {
        ItemStack base = basic(new ItemStack(Material.LEATHER_HELMET), "Dummy-Helmet", ItemRarity.DEBUG, 1.5, new HelmetAbility(5, 2, 2));
        return new ItemBuilder(base)
                .lore("§6[Debug]")
                .lore("§fNur zum testen!")
                .build();
    }

    public static ItemStack dummyChestplate() {
        ItemStack base = basic(new ItemStack(Material.LEATHER_CHESTPLATE), "Dummy-Chestplate", ItemRarity.DEBUG, 3, new ChestplateAbility(10, 5, 5));
        return new ItemBuilder(base)
                .lore("§6[Debug]")
                .lore("§fNur zum testen!")
                .build();
    }
}