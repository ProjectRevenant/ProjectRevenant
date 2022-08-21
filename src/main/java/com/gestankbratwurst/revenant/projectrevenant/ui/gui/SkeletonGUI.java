package com.gestankbratwurst.revenant.projectrevenant.ui.gui;

import com.gestankbratwurst.core.mmcore.inventories.guis.AbstractGUIInventory;
import com.gestankbratwurst.core.mmcore.inventories.guis.GUIItem;
import com.gestankbratwurst.core.mmcore.util.items.ItemBuilder;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.ArmBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Bone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.LegBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.RibsBone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Skeleton;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.SkullBone;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SkeletonGUI extends AbstractGUIInventory {
  @Override
  protected Inventory createInventory(Player player) {
    return Bukkit.createInventory(null, InventoryType.DROPPER, Component.text("Skelett"));
  }

  @Override
  protected void init(Player player) {
    Skeleton skeleton = RevenantPlayer.of(player).getBody().getSkeleton();
    this.setGUIItem(1, getBoneIcon(skeleton.getBone(SkullBone.TYPE)));
    this.setGUIItem(3, getBoneIcon(skeleton.getBone(ArmBone.LEFT)));
    this.setGUIItem(4, getBoneIcon(skeleton.getBone(RibsBone.TYPE)));
    this.setGUIItem(5, getBoneIcon(skeleton.getBone(ArmBone.RIGHT)));
    this.setGUIItem(6, getBoneIcon(skeleton.getBone(LegBone.LEFT)));
    this.setGUIItem(8, getBoneIcon(skeleton.getBone(LegBone.RIGHT)));
  }

  private GUIItem getBoneIcon(Bone bone) {
    return GUIItem.builder()
            .iconCreator(player -> {
              ItemBuilder builder = new ItemBuilder(bone.getCurrentGuiIcon().getItem())
                      .name("§6" + bone.getBoneDisplayName())
                      .lore("")
                      .lore("§7Status: " + bone.getBoneStatus().getDisplayName());

              List<Ability> abilityList = bone.getEffects();

              for (Ability ability : abilityList) {
                builder.lore("");
                builder.lore("§7- " + PlainTextComponentSerializer.plainText().serialize(ability.getInfoTitle(player)));
                for (Component component : ability.getInfos(player)) {
                  builder.lore(PlainTextComponentSerializer.plainText().serialize(component));
                }
              }

              return builder.build();
            })
            .eventConsumer(event -> {
            })
            .build();
  }

}
