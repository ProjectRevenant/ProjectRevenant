package com.gestankbratwurst.revenant.projectrevenant.ui.actionbar;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.actionbar.ActionBarBoard;
import com.gestankbratwurst.core.mmcore.actionbar.ActionBarManager;
import com.gestankbratwurst.core.mmcore.actionbar.ActionLine;
import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.DangerLevel;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class ActionBarListener implements Listener {

  private final BodyManager bodyManager;

  @EventHandler(priority = EventPriority.HIGH)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    ActionBarManager manager = MMCore.getActionBarManager();
    ActionBarBoard board = manager.getBoard(player);
    ActionLine bodyLine = new ActionLine(ActionLine.HIGH_PRIORITY, () -> bodyManager.getInfoIcons(player));
    ActionLine buffLine = new ActionLine(ActionLine.HIGH_PRIORITY, () -> {
      StringBuilder builder = new StringBuilder();
      EntityAbilityCache.getAbilities(player.getUniqueId()).forEach(ability -> {
        if (ability.shouldDisplayInActionbar()) {
          if (ability instanceof TimedAbility timedAbility) {
            if (!timedAbility.isDone()) {
              builder.append(ability.getModel().getChar());
            }
          } else {
            builder.append(ability.getModel().getChar());
          }
        }
      });
      return builder.toString();
    });
    ActionLine noiseLine = new ActionLine(ActionLine.MID_PRIORITY, () -> getDangerLevel(player) + " " + getNoiseBar(player));
    board.getSection(ActionBarBoard.Section.MIDDLE).addLayer(bodyLine);
    board.getSection(ActionBarBoard.Section.RIGHT).addLayer(buffLine);
    board.getSection(ActionBarBoard.Section.LEFT).addLayer(noiseLine);
    TaskManager.getInstance().runBukkitSync(() -> {
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateBody();
        userTablist.updateEffects();
        userTablist.updateStatistics();
      }
    });
  }

  private String getDangerLevel(Player player) {
    double heat = ProjectRevenant.getChunkHeatManager().getAverageHeatInRegion(player.getLocation(), 64);
    DangerLevel dangerLevel = DangerLevel.getByHeat(heat);
    return String.valueOf(dangerLevel.getTextureModel().getChar());
  }

  private String getNoiseBar(Player player) {
    StringBuilder builder = new StringBuilder();
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    double noise = revenantPlayer.getNoiseLevel();
    double max = revenantPlayer.getBody().getAttribute(BodyAttribute.NOISE).getMaxValue();
    double percent = 1.0 / max * noise;
    if (percent > 0.9) {
      return builder.append(TextureModel.SOUND_ICON_8.getChar()).toString();
    }
    if (percent > 0.75) {
      return builder.append(TextureModel.SOUND_ICON_7.getChar()).toString();
    }
    if (percent > 0.62) {
      return builder.append(TextureModel.SOUND_ICON_6.getChar()).toString();
    }
    if (percent >= 0.50) {
      return builder.append(TextureModel.SOUND_ICON_5.getChar()).toString();
    }
    if (percent > 0.365) {
      return builder.append(TextureModel.SOUND_ICON_4.getChar()).toString();
    }
    if (percent > 0.25) {
      return builder.append(TextureModel.SOUND_ICON_3.getChar()).toString();
    }
    if (percent > 0.125) {
      return builder.append(TextureModel.SOUND_ICON_2.getChar()).toString();
    }
    if (percent > 0.05) {
      return builder.append(TextureModel.SOUND_ICON_1.getChar()).toString();
    }
    return builder.append(TextureModel.SOUND_ICON_0.getChar()).toString();
  }

}
