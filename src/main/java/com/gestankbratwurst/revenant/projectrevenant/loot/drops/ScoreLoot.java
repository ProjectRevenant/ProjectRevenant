package com.gestankbratwurst.revenant.projectrevenant.loot.drops;

import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.score.ScoreType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Score;

public class ScoreLoot implements Loot{

  private final ScoreType type;
  private final int score;

  public ScoreLoot(ScoreType type, int score){
    this.type = type;
    this.score = score;
  }

  @Override
  public void applyTo(Player looter, Location location) {
    RevenantPlayer.of(looter).addScore(type, score);
  }

  @Override
  public void applyTo(Player looter, Inventory inventory) {
    applyTo(looter, looter.getLocation());
  }
}
