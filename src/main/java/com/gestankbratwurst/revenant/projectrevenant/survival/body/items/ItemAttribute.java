package com.gestankbratwurst.revenant.projectrevenant.survival.body.items;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemAttribute {

  private static final NamespacedKey itemAttributeKey = NamespaceFactory.provide("item-attributes");

  public static ItemAttributeContainer getAttributes(ItemStack itemStack) {
    ItemAttributeContainer container = ItemAttributeContainer.empty();
    if (itemStack == null) {
      return container;
    }
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null) {
      return container;
    }
    PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
    String data = dataContainer.get(itemAttributeKey, PersistentDataType.STRING);
    if (data == null) {
      return container;
    }
    return MMCore.getGsonProvider().fromJson(data, ItemAttributeContainer.class);
  }

  public static void addAttributeModifier(ItemStack itemStack, BodyAttributeModifier modifier) {
    ItemAttributeContainer container = new ItemAttributeContainer();
    if (itemStack == null) {
      return;
    }
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null) {
      return;
    }
    PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
    String data = dataContainer.get(itemAttributeKey, PersistentDataType.STRING);
    if (data != null) {
      container = MMCore.getGsonProvider().fromJson(data, ItemAttributeContainer.class);
    }
    container.add(modifier);
    data = MMCore.getGsonProvider().toJson(container);
    dataContainer.set(itemAttributeKey, PersistentDataType.STRING, data);
  }

}
