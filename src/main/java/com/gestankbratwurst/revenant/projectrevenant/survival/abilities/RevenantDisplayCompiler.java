package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.gestankbratwurst.core.mmcore.util.items.display.DisplayConverter;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.ItemRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.item.Rarity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RevenantDisplayCompiler implements DisplayConverter {

  public static final String ABILITY_COMPILER_KEY = "revenant-item-display";

  @Override
  public String getDisplayKey() {
    return ABILITY_COMPILER_KEY;
  }

  @Override
  public ItemStack compileInfo(Player player, ItemStack itemStack) {
    Collection<Ability> abilities = AbilityHandle.getFrom(itemStack);

    if(abilities.isEmpty()) {
      return itemStack;
    }

    ItemStack clone = itemStack.clone();
    ItemMeta currentMeta = itemStack.getItemMeta();
    List<Component> lore = currentMeta.hasLore() ? currentMeta.lore() : new ArrayList<>();

    assert lore != null;

    if(!abilities.isEmpty()) {
      lore.add(Component.text(""));
      lore.add(Component.text("Effekte").style(Style.style(TextColor.color(153, 204, 255))));
      for(Ability ability : abilities) {
        lore.add(Component.text("- ").color(NamedTextColor.WHITE).append(ability.getInfoTitle(player)));
        for(Component loreComp : ability.getInfos(player)) {
          lore.add(Component.text("   ").append(loreComp));
        }
      }
    }

    ItemMeta cloneMeta = clone.getItemMeta();
    cloneMeta.lore(lore);
    clone.setItemMeta(cloneMeta);
    return clone;
  }
}
