package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.items.display.ItemDisplayCompiler;
import io.leangen.geantyref.TypeToken;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AbilityHandle {

  public static NamespacedKey ABILITY_KEY = NamespaceFactory.provide("abilities");
  private static final Type abilityListType = new TypeToken<List<Ability>>() {
  }.getType();

  public static void addTo(ItemStack itemStack, Ability... abilities) {
    ItemDisplayCompiler.addDisplayCompileKey(RevenantDisplayCompiler.ABILITY_COMPILER_KEY, itemStack);
    ItemMeta meta = itemStack.getItemMeta();
    addTo(meta.getPersistentDataContainer(), abilities);
    itemStack.setItemMeta(meta);
  }

  public static void removeFrom(ItemStack itemStack, Ability ability) {
    ItemMeta meta = itemStack.getItemMeta();
    removeFrom(meta.getPersistentDataContainer(), ability);
    itemStack.setItemMeta(meta);
  }

  public static void removeFrom(ItemStack itemStack, String identifier) {
    ItemMeta meta = itemStack.getItemMeta();
    removeFrom(meta.getPersistentDataContainer(), identifier);
    itemStack.setItemMeta(meta);
  }

  public static List<Ability> getFrom(ItemStack itemStack) {
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null) {
      return Collections.emptyList();
    }
    return getFrom(meta.getPersistentDataContainer());
  }

  @SafeVarargs
  public static <T extends Ability> void addTo(PersistentDataContainer container, T... abilities) {
    String data = container.get(ABILITY_KEY, PersistentDataType.STRING);
    List<Ability> abilityList = data == null ? new ArrayList<>() : MMCore.getGsonProvider().fromJson(data, abilityListType);
    abilityList.addAll(Arrays.asList(abilities));
    container.set(ABILITY_KEY, PersistentDataType.STRING, MMCore.getGsonProvider().toJson(abilityList));
  }

  public static <T extends Ability> void removeFrom(PersistentDataContainer container, T ability) {
    String data = container.get(ABILITY_KEY, PersistentDataType.STRING);
    List<Ability> abilityList = data == null ? new ArrayList<>() : MMCore.getGsonProvider().fromJson(data, abilityListType);
    abilityList.remove(ability);
    container.set(ABILITY_KEY, PersistentDataType.STRING, MMCore.getGsonProvider().toJson(abilityList));
  }

  public static void removeFrom(PersistentDataContainer container, String identifier) {
    String data = container.get(ABILITY_KEY, PersistentDataType.STRING);
    List<Ability> abilityList = data == null ? new ArrayList<>() : MMCore.getGsonProvider().fromJson(data, abilityListType);
    abilityList.removeIf(ability -> ability.getIdentifier().equals(identifier));
    container.set(ABILITY_KEY, PersistentDataType.STRING, MMCore.getGsonProvider().toJson(abilityList));
  }

  public static List<Ability> getFrom(PersistentDataContainer container) {
    String data = container.get(ABILITY_KEY, PersistentDataType.STRING);
    return data == null ? new ArrayList<>() : MMCore.getGsonProvider().fromJson(data, abilityListType);
  }

}
