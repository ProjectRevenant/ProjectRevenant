package com.gestankbratwurst.revenant.projectrevenant.metaprogression.levelsystem;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("exp")
@RequiredArgsConstructor
public class ExperienceCommand extends BaseCommand {

  private final RevenantPlayerManager revenantPlayerManager;

  @Default
  public void onDefault(Player sender) {
    RevenantPlayer revenantPlayer = revenantPlayerManager.getOnline(sender.getUniqueId());
    LevelContainer levelContainer = revenantPlayer.getLevelContainer();
    int totalExp = (int) levelContainer.getExperience();
    int expForNext = (int) levelContainer.getExperienceTowardsNextLevel();
    int expTowards = (int) levelContainer.getExperienceProgressTowardsNextLevel();
    double percent = ((int) (1000.0 * levelContainer.getProgressPercent())) / 10.0;
    sender.sendMessage("§eLevel: §f" + levelContainer.getCurrentLevel() + "/" + 100);
    sender.sendMessage("§eTotal experience: §f" + totalExp);
    sender.sendMessage("§eProgress: §f" + expTowards + "/" + expForNext + " [§7" + percent + "%§f]");
  }

  @Subcommand("add")
  @CommandPermission("admin")
  @Syntax("<Target> <Amount>")
  public void onAdd(CommandSender sender, OnlinePlayer target, double amount) {
    RevenantPlayer revenantPlayer = revenantPlayerManager.getOnline(target.getPlayer().getUniqueId());
    revenantPlayer.addExperience(amount);
    sender.sendMessage("§aDu hast " + amount + " exp an " + target.getPlayer().getName() + " vergeben.");
  }

  @Subcommand("remove")
  @CommandPermission("admin")
  @Syntax("<Target> <Amount>")
  public void onRemove(CommandSender sender, OnlinePlayer target, double amount) {
    RevenantPlayer revenantPlayer = revenantPlayerManager.getOnline(target.getPlayer().getUniqueId());
    revenantPlayer.removeExperience(amount);
    sender.sendMessage("§aDu hast " + amount + " exp von " + target.getPlayer().getName() + " genommen.");
  }

}
