package com.gestankbratwurst.revenant.projectrevenant.survival.items;

import com.gestankbratwurst.core.mmcore.util.common.NamespaceFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.Color;

@RequiredArgsConstructor
public enum ItemRarity {

  POOR(TextColor.color(new Color(155, 155, 155).getRGB()), "Schlecht"),
  COMMON(TextColor.color(new Color(250, 250, 250).getRGB()), "Gewöhnlich"),
  UNCOMMON(TextColor.color(new Color(30, 239, 10).getRGB()), "Außergewöhnlich"),
  RARE(TextColor.color(new Color(0, 112, 221).getRGB()), "Selten"),
  EPIC(TextColor.color(new Color(163, 53, 238).getRGB()), "Episch"),
  LEGENDARY(TextColor.color(new Color(255, 128, 0).getRGB()), "Legendär"),
  ARTIFACT(TextColor.color(new Color(230, 204, 128).getRGB()), "Artefakt");

  private static final NamespacedKey itemRarityKey = NamespaceFactory.provide("item-rarity");

  @Getter
  private final TextColor color;
  @Getter
  private final String displayName;

  public static ItemRarity ofItem(ItemStack itemStack) {
    if(itemStack == null) {
      return null;
    }
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null) {
      return null;
    }
    PersistentDataContainer container = meta.getPersistentDataContainer();
    if(container.isEmpty()) {
      return null;
    }
    Integer rarityOrdinal = container.get(itemRarityKey, PersistentDataType.INTEGER);
    return rarityOrdinal == null ? null : ItemRarity.values()[rarityOrdinal];
  }

  public void applyTo(ItemStack itemStack) {
    if(itemStack == null) {
      return;
    }
    ItemMeta meta = itemStack.getItemMeta();
    if (meta == null) {
      return;
    }
    PersistentDataContainer container = meta.getPersistentDataContainer();
    container.set(itemRarityKey, PersistentDataType.INTEGER, this.ordinal());
    itemStack.setItemMeta(meta);
  }

}
