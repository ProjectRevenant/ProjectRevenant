package com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Values;
import com.gestankbratwurst.core.mmcore.util.Msg;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@CommandAlias("spawner")
@CommandPermission("admin")
public class SpawnerCommand extends BaseCommand {

  private final SpawnerManager spawnerManager;

  @Default
  public void onDefault(CommandSender sender) {
    sender.sendMessage("§6Spawners:");
    spawnerManager.getAllSpawnerNames().stream().map(spawnerManager::getSpawnerByName).forEach(spawner -> {
      StringBuilder line = new StringBuilder();
      line.append("§f- ").append("§e").append(spawner.getInternalName()).append(" §7");
      World world = Bukkit.getWorld(spawner.getWorldId());
      String name = world == null ? "[OFFLINE]" : "[" + world.getName() + "]";
      line.append(name);
      int max = spawner.getMaxAmountSpawned();
      int current = spawner.getCurrentAmountSpawned();
      line.append(" §f[§7").append(current).append("§f/§7").append(max).append("§f]");
      sender.sendMessage(line.toString());
    });
  }

  @Subcommand("create")
  @CommandCompletion("@nothing @RevenantSpawnerType")
  public void onCreate(Player sender, String name, @Values("@RevenantSpawnerType") SpawnerType spawnerType) {
    if(spawnerManager.getAllSpawnerNames().contains(name)) {
      Msg.sendError(sender, "A spawner with the name {} already exists.", name);
      return;
    }
    spawnerManager.createSpawner(spawnerType, sender.getWorld().getUID(), name);
    Msg.sendInfo(sender, "You created the spawner {} of type {}.", name, spawnerType);
    Msg.sendInfo(sender, "Type in {} for more infos.", "/spawner info " + name);
    Msg.sendInfo(sender, "Type in {} to edit the spawner.", "/spawner edit " + name);
  }

  @Subcommand("delete")
  @CommandCompletion("@RevenantSpawner")
  public void onDelete(Player sender, @Values("@RevenantSpawner") RevenantSpawner revenantSpawner) {
    spawnerManager.removeSpawner(revenantSpawner);
    Msg.sendInfo(sender, "You deleted the spawner {} of type {}.", revenantSpawner.getInternalName(), revenantSpawner.getSpawnerType());
  }

  @Subcommand("info")
  @CommandCompletion("@RevenantSpawner")
  public void onInfo(Player sender, @Values("@RevenantSpawner") RevenantSpawner revenantSpawner) {
    sender.sendMessage("§6Spawner: §f" + revenantSpawner.getInternalName());
    sender.sendMessage("§e Active: §f" + revenantSpawner.getCurrentAmountSpawned() + " / " + revenantSpawner.getMaxAmountSpawned());
    sender.sendMessage("§e Cooldown: §f" + revenantSpawner.getSpawnCooldown() + "ms");
    sender.sendMessage("§e Time left: §f" + revenantSpawner.getTimeLeft() + "ms");
    sender.sendMessage("§e Type: §f" + revenantSpawner.getSpawnerType());
    sender.sendMessage("§e Spawn positions: §f(§7" + revenantSpawner.getAllPositions().size() + ")");
    revenantSpawner.getAllPositions().forEach(position -> {
      sender.sendMessage(" §f- [§7" + position.getX() + "§f, §7" + position.getZ() + "§f, §7" + position.getZ() + "§f]");
    });
  }

  @Subcommand("edit addposition")
  @CommandCompletion("@RevenantSpawner")
  public void onAdd(Player sender, @Values("@RevenantSpawner") RevenantSpawner revenantSpawner) {
    revenantSpawner.addSpawnerPosition(SpawnerPosition.of(sender.getLocation()));
    Msg.sendInfo(sender, "Added your current position to the {} spawner.", revenantSpawner.getInternalName());
  }

  @Subcommand("edit removeposition")
  @CommandCompletion("@RevenantSpawner")
  public void onRemove(Player sender, @Values("@RevenantSpawner") RevenantSpawner revenantSpawner) {
    if(!revenantSpawner.hasSpawnerPosition(SpawnerPosition.of(sender.getLocation()))) {
      Msg.sendError(sender, "You current position is not part of the {} spawner.", revenantSpawner.getInternalName());
      return;
    }
    revenantSpawner.removeSpawnerPosition(SpawnerPosition.of(sender.getLocation()));
    Msg.sendInfo(sender, "Removed your current position from the {} spawner.", revenantSpawner.getInternalName());
  }

  @Subcommand("edit setcooldown")
  @CommandCompletion("@RevenantSpawner")
  public void onCooldown(Player sender, @Values("@RevenantSpawner") RevenantSpawner revenantSpawner, long cooldown) {
    if(cooldown < 0) {
      Msg.sendError(sender, "Negative cooldowns are not allowed.");
      return;
    }
    revenantSpawner.setSpawnCooldown(cooldown);
    String secondInfo = "%.1fs".formatted(cooldown / 1000.0);
    Msg.sendInfo(sender, "The cooldown for the {} spawner is now {}.", revenantSpawner.getInternalName(), secondInfo);
  }

  @Subcommand("edit setmax")
  @CommandCompletion("@RevenantSpawner")
  public void onMax(Player sender, @Values("@RevenantSpawner") RevenantSpawner revenantSpawner, int max) {
    if(max < 0) {
      Msg.sendError(sender, "Negative values are not allowed.");
      return;
    }
    revenantSpawner.setMaxAmountSpawned(max);
    Msg.sendInfo(sender, "The max amount for the {} spawner is now {}.", revenantSpawner.getInternalName(), max);
  }

}
