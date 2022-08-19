package com.gestankbratwurst.revenant.projectrevenant.ui.tab;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.revenant.projectrevenant.util.DistributedTask;
import org.bukkit.entity.Player;

public class TabListTask extends DistributedTask<Player> {

  public TabListTask() {
    super(20);
  }

  @Override
  public void onTick(Player element) {
    if (MMCore.getTabListManager().getView(element).getTablist() instanceof RevenantUserTablist userTablist) {
      userTablist.updateServerInfo();
    }
  }
}
