package com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class SimpleIngredient implements Ingredient {

  private final ItemStack itemStack;

  public SimpleIngredient(ItemStack itemStack) {
    this.itemStack = itemStack.asOne();
  }

  @Override
  public Component getInfo(Player player) {
    return Component.text(getAsChar(player) + " ").append(Optional.ofNullable(itemStack.getItemMeta().displayName()).orElse(Component.text("ERROR")));
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
  public boolean test(ItemStack itemStack) {
    return itemStack.isSimilar(this.itemStack);
  }
}
