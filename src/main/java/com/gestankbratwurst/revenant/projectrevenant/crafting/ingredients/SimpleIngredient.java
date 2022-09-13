package com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@EqualsAndHashCode
public class SimpleIngredient implements Ingredient {

  @Getter
  private final ItemStack itemStack;
  private final boolean consumed;

  public SimpleIngredient(ItemStack itemStack, boolean consumed) {
    this.itemStack = itemStack.asOne();
    this.consumed = consumed;
  }

  @Override
  public Component getInfo(Player player) {
    return Component.text(getAsChar(player) + " ").append(Optional.ofNullable(itemStack.getItemMeta().displayName()).orElse(Component.text("ERROR"))).style(Style.style(NamedTextColor.WHITE, TextDecoration.ITALIC.withState(false)));
  }

  @Override
  public ItemStack getAsIcon(Player player) {
    return itemStack.clone();
  }

  @Override
  public char getAsChar(Player player) {
    return Optional.ofNullable(TextureModel.ofItemStack(itemStack)).map(TextureModel::getChar).orElse('X');
  }

  @Override
  public boolean isConsumed() {
    return consumed;
  }

  @Override
  public boolean test(ItemStack itemStack) {
    return this.itemStack.isSimilar(itemStack);
  }
}
