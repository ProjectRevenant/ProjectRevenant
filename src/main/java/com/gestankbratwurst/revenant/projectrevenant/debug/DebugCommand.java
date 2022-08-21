package com.gestankbratwurst.revenant.projectrevenant.debug;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import org.bukkit.entity.Player;

@CommandPermission("admin")
@CommandAlias("debug")
public class DebugCommand extends BaseCommand {

  @Subcommand("getitem")
  @CommandCompletion("@RevenantItem")
  public void onAbilityItem(Player sender, @Values("@RevenantItem") String internalName) {
    sender.getInventory().addItem(RevenantItem.getItemByInternalName(internalName));
    Msg.sendInfo(sender, "Du hast das " + internalName + " item erhalten.");
  }

  @Subcommand("droploot")
  @CommandCompletion("@LootType")
  public void onDropLoot(Player sender, @Values("@LootType") LootType type) {
    type.getGenerator().apply(sender).applyTo(sender, sender.getLocation());
  }
}
