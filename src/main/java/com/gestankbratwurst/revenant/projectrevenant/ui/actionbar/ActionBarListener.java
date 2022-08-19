package com.gestankbratwurst.revenant.projectrevenant.ui.actionbar;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.actionbar.ActionBarBoard;
import com.gestankbratwurst.core.mmcore.actionbar.ActionBarManager;
import com.gestankbratwurst.core.mmcore.actionbar.ActionLine;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
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
          if(ability instanceof TimedAbility timedAbility) {
            if(!timedAbility.isDone()) {
              builder.append(ability.getModel().getChar());
            }
          } else {
            builder.append(ability.getModel().getChar());
          }
        }
      });
      return builder.toString();
    });
    board.getSection(ActionBarBoard.Section.MIDDLE).addLayer(bodyLine);
    board.getSection(ActionBarBoard.Section.RIGHT).addLayer(buffLine);
    TaskManager.getInstance().runBukkitSync(() -> {
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateBody();
        userTablist.updateEffects();
        userTablist.updateStatistics();
      }
    });
  }

}
