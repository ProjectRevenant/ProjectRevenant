package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.world.InteractionHand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatPacketAdapter extends PacketAdapter {
  public CombatPacketAdapter() {
    super(JavaPlugin.getPlugin(ProjectRevenant.class), PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.ARM_ANIMATION);
  }

  @Override
  public void onPacketReceiving(PacketEvent event) {
    if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
      ServerboundInteractPacket packet = (ServerboundInteractPacket) event.getPacket().getHandle();
      ServerboundInteractPacket.ActionType actionType = packet.getActionType();
      if (actionType == ServerboundInteractPacket.ActionType.ATTACK) {
        event.setCancelled(true);
      }
    } else {
      ServerboundSwingPacket packet = (ServerboundSwingPacket) event.getPacket().getHandle();
      InteractionHand hand = packet.getHand();
      Runnable action = () -> PlayerSwingActionEvaluator.onSwing(event.getPlayer(), hand);
      if(Bukkit.isPrimaryThread()) {
        action.run();
      } else {
        TaskManager.getInstance().runBukkitSync(action);
      }
    }

  }
}
