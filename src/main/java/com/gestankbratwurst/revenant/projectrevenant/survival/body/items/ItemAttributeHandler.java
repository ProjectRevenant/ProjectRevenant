package com.gestankbratwurst.revenant.projectrevenant.survival.body.items;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemAttributeHandler {

  private static final NamespacedKey itemAttributeKey = NamespaceFactory.provide("item-attributes");
  private static final NamespacedKey twoHandedKey = NamespaceFactory.provide("two-handed-weapon");
  private static final NamespacedKey twoHandedPlaceholderKey = NamespaceFactory.provide("two-handed-weapon-icon-placeholder");

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

  public static ItemStack getTwoHandedPlaceholder() {
    return ItemBuilder.of(TextureModel.BLOCKED_HAND_ICON.getItem())
            .name(Component.text("§7Hand ist belegt"))
            .addPersistentData(twoHandedPlaceholderKey, PersistentDataType.INTEGER, 1)
            .build();
  }

  public static boolean isTwoHandedPlaceholder(ItemStack itemStack) {
    if(itemStack == null) {
      return false;
    }
    ItemMeta meta = itemStack.getItemMeta();
    if(meta == null) {
      return false;
    }
    return meta.getPersistentDataContainer().has(twoHandedPlaceholderKey);
  }

  public static void setAsTwoHanded(ItemStack itemStack) {
    itemStack.editMeta(meta -> meta.getPersistentDataContainer().set(twoHandedKey, PersistentDataType.INTEGER, 1));
  }

  public static boolean isTwoHandedItem(ItemStack itemStack) {
    if(itemStack == null) {
      return false;
    }
    ItemMeta meta = itemStack.getItemMeta();
    if(meta == null) {
      return false;
    }
    return meta.getPersistentDataContainer().has(twoHandedKey);
  }

}
