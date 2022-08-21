package com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.ui.gui.SkeletonGUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("skeleton|skelett|s")
public class SkeletonCommand extends BaseCommand {

  @Default
  public void onCommand(Player sender) {
    new SkeletonGUI().openFor(sender);
    Msg.sendInfo(sender, "Du studierst dein Skelett.");
  }

  @CommandPermission("admin")
  @Subcommand("breakbone")
  @CommandCompletion("@players @BoneType")
  public void onBreakBone(Player sender, OnlinePlayer target, @Values("@BoneType") String boneType) {
    RevenantPlayer.of(target.player).getBody().getSkeleton().getBone(boneType).breakBone();
    target.player.playSound(target.player.getLocation(), Sound.ENTITY_TURTLE_EGG_CRACK, 1.2F, 0.66F);
    Msg.sendInfo(sender, "Du hast den {} Knochen von {} gebrochen.", boneType, target.player.getName());
  }

  @CommandPermission("admin")
  @Subcommand("healbone")
  @CommandCompletion("@players @BoneType")
  public void onHealBone(Player sender, OnlinePlayer target, @Values("@BoneType") String boneType) {
    RevenantPlayer.of(target.player).getBody().getSkeleton().getBone(boneType).healBone();
    Msg.sendInfo(sender, "Du hast den {} Knochen von {} geheilt.", boneType, target.player.getName());
  }

  @CommandPermission("admin")
  @Subcommand("bandagebone")
  @CommandCompletion("@players @BoneType")
  public void onBandageBone(Player sender, OnlinePlayer target, @Values("@BoneType") String boneType) {
    RevenantPlayer.of(target.player).getBody().getSkeleton().getBone(boneType).bandageBone();
    Msg.sendInfo(sender, "Du hast den {} Knochen von {} bandagiert.", boneType, target.player.getName());
  }

}
