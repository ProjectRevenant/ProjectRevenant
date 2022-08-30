package com.gestankbratwurst.revenant.projectrevenant.survival.combat;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import net.minecraft.world.InteractionHand;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class PlayerSwingActionEvaluator {

  public static void onSwing(Player player, InteractionHand hand) {
    if (hand == InteractionHand.OFF_HAND) {
      System.out.println(">> Swing with " + hand);
    }
    HumanBody body = RevenantPlayer.of(player).getBody();
    double range = body.getAttribute(BodyAttribute.MELEE_RANGE).getCurrentValueModified();
    double size = body.getAttribute(BodyAttribute.MELEE_GIRTH).getCurrentValueModified();
    World world = player.getWorld();
    Location start = player.getEyeLocation();
    Vector direction = start.getDirection();
    RayTraceResult result = world.rayTrace(start, direction, range, FluidCollisionMode.NEVER, true, size, (entity) -> !entity.equals(player));
    if (result == null) {
      return;
    }
    if (result.getHitBlock() != null) {
      return;
    }
    Entity entity = result.getHitEntity();
    if (!(entity instanceof LivingEntity livingEntity)) {
      return;
    }
    player.attack(livingEntity);
  }

}
