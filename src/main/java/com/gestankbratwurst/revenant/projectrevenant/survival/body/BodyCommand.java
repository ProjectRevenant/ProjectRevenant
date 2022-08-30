package com.gestankbratwurst.revenant.projectrevenant.survival.body;

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
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wounds.BleedingDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wounds.WoundInfectionDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

@RequiredArgsConstructor
@CommandAlias("body")
public class BodyCommand extends BaseCommand {

  private final BodyManager bodyManager;

  @Default
  public void onDefault(Player sender) {
    HumanBody body = (HumanBody) bodyManager.getBody(sender);
    BodyAttribute health = body.getAttribute(BodyAttribute.HEALTH);
    BodyAttribute water = body.getAttribute(BodyAttribute.WATER);
    BodyAttribute nutrition = body.getAttribute(BodyAttribute.NUTRITION);
    BodyAttribute weight = body.getAttribute(BodyAttribute.WEIGHT);
    BodyAttribute healthShift = body.getAttribute(BodyAttribute.HEALTH_SHIFT);
    BodyAttribute waterShift = body.getAttribute(BodyAttribute.WATER_SHIFT);
    BodyAttribute nutritionShift = body.getAttribute(BodyAttribute.NUTRITION_SHIFT);
    BodyAttribute weightShift = body.getAttribute(BodyAttribute.WEIGHT_SHIFT);

    sender.sendMessage("§eLeben: §f%.1f§9hp§e/§f%.1f§9hp".formatted(health.getCurrentValue(), health.getMaxValueModified()));
    sender.sendMessage("§eWasser: §f%.2f§9L§e/§f%.2f§9L".formatted(water.getCurrentValue(), water.getMaxValueModified()));
    sender.sendMessage("§eNahrung: §f%.1f§9kcal§e/§f%.1f§9kcal".formatted(nutrition.getCurrentValue(), nutrition.getMaxValueModified()));
    sender.sendMessage("§eGewicht: §f%.1f§9kg§e/§f%.1f§9kg".formatted(weight.getCurrentValue(), weight.getMaxValueModified()));
    sender.sendMessage("§eLebensänderung: §f%.2f§9hp/min".formatted(healthShift.getCurrentValueModified() * 20 * 60));
    sender.sendMessage("§eWasseränderung: §f%.2f§9L/min".formatted(waterShift.getCurrentValueModified() * 20 * 60));
    sender.sendMessage("§eEssensänderung: §f%.2f§9kcal/min".formatted(nutritionShift.getCurrentValueModified() * 20 * 60));
    sender.sendMessage("§eGewichtsänderung: §f%.2f§9kg/min".formatted(weightShift.getCurrentValueModified() * 20 * 60));
  }

  @Subcommand("listall")
  @CommandPermission("admin")
  @CommandCompletion("@players")
  public void onAttribute(CommandSender sender, OnlinePlayer player) {
    HumanBody body = RevenantPlayer.of(player.player).getBody();
    for(String identifier : BodyAttribute.getValues()) {
      BodyAttribute attribute = body.getAttribute(identifier);
      sender.sendMessage("§7%s: §e%.1f §7[§f%.1f§7/§f%.1f§7]".formatted(identifier, attribute.getCurrentValueModified(), attribute.getMinValue(), attribute.getMaxValueModified()));
    }
  }

  @Subcommand("attribute change")
  @CommandPermission("admin")
  @CommandCompletion("@players @BodyAttribute @Nothing")
  public void onAttribute(CommandSender sender, OnlinePlayer player, @Values("@BodyAttribute") String bodyAttribute, double value) {
    RevenantPlayer.of(player.player.getUniqueId()).getBody().getAttribute(bodyAttribute).setCurrentValue(value);
  }

  @Subcommand("illness add")
  @CommandPermission("admin")
  @CommandCompletion("@players @Illness")
  public void onAddIllness(CommandSender sender, OnlinePlayer target, @Values("@Illness") String illness) {
    Player player = target.player;
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    BleedingDebuff debuff = new BleedingDebuff();
    debuff.setDurationFromNow(Duration.ofMinutes(5));
    switch (illness) {
      case "BLEEDING_I":
        revenantPlayer.addAbility(debuff);
        break;
      case "BLEEDING_II":
        debuff.setIntensity(2);
        revenantPlayer.addAbility(debuff);
        break;
      case "BLEEDING_III":
        debuff.setIntensity(3);
        revenantPlayer.addAbility(debuff);
        break;
      case "BLEEDING_IV":
        debuff.setIntensity(4);
        revenantPlayer.addAbility(debuff);
        break;
      case "BLEEDING_V":
        debuff.setIntensity(5);
        revenantPlayer.addAbility(debuff);
        break;
      case "INFECTION":
        WoundInfectionDebuff woundInfectionDebuff = new WoundInfectionDebuff();
        revenantPlayer.addAbility(woundInfectionDebuff);
        break;
      default:
        Msg.sendError(sender, "Unknown illness {}.", illness);
        break;
    }
  }

  @Subcommand("illness remove")
  @CommandPermission("admin")
  @CommandCompletion("@players @Illness")
  public void onRemoveIllness(CommandSender sender, OnlinePlayer target, @Values("@Illness") String illness) {
    Player player = target.player;
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    BleedingDebuff debuff = new BleedingDebuff();
    debuff.setDurationFromNow(Duration.ofMinutes(5));
    switch (illness) {
      case "BLEEDING_I":
        revenantPlayer.removeAbility(debuff);
        break;
      case "BLEEDING_II":
        debuff.setIntensity(2);
        revenantPlayer.removeAbility(debuff);
        break;
      case "BLEEDING_III":
        debuff.setIntensity(3);
        revenantPlayer.removeAbility(debuff);
        break;
      case "BLEEDING_IV":
        debuff.setIntensity(4);
        revenantPlayer.removeAbility(debuff);
        break;
      case "BLEEDING_V":
        debuff.setIntensity(5);
        revenantPlayer.removeAbility(debuff);
      case "INFECTION":
        WoundInfectionDebuff woundInfectionDebuff = new WoundInfectionDebuff();
        revenantPlayer.removeAbility(woundInfectionDebuff);
        break;
      default:
        Msg.sendError(sender, "Unknown illness {}.", illness);
        break;
    }
  }

}
