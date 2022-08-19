package com.gestankbratwurst.revenant.projectrevenant.survival.weight;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class ItemWeight {

  private static final NamespacedKey weightKey = NamespaceFactory.provide("item-weight");

  public static double get(ItemStack itemStack) {
    if(itemStack == null) {
      return 0;
    }
    return Optional.ofNullable(itemStack.getItemMeta()).map(meta -> meta.getPersistentDataContainer().get(weightKey, PersistentDataType.DOUBLE)).orElse(1.0) * itemStack.getAmount();
  }

  public static void set(ItemStack itemStack, double amount) {
    ItemMeta meta = itemStack.getItemMeta();
    PersistentDataContainer container = meta.getPersistentDataContainer();
    container.set(weightKey, PersistentDataType.DOUBLE, amount);
    itemStack.setItemMeta(meta);
  }

  public static double of(LivingEntity entity) {
    double weight = 0;
    if(entity instanceof InventoryHolder holder) {
      for(ItemStack itemStack : holder.getInventory()) {
        weight += get(itemStack);
      }
    } else {
      EntityEquipment equipment = entity.getEquipment();
      if(equipment != null) {
        for(EquipmentSlot slot : EquipmentSlot.values()) {
          weight += get(equipment.getItem(slot));
        }
      }
    }
    return weight;
  }

}
