package com.gestankbratwurst.revenant.projectrevenant.metaprogression;

import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.score.ScoreType;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MetaProgressionManager {

  public void reportPlayerDeath(Player player){
    RevenantPlayer revenantPlayer = RevenantPlayer.of(player);
    Msg.sendInfo(player, "Du bist gestorben! :(");
    Msg.sendInfo(player, "Du hast {} Minuten überlebt!", Duration.ofMillis(revenantPlayer.getSurvivalTime()).toMinutes());
    Msg.sendInfo(player, "Dein Score: {}", revenantPlayer.getScore());
    Msg.sendInfo(player, "  {} für die überlebte Zeit", revenantPlayer.getScore(ScoreType.SURVIVED_TIME));
    Msg.sendInfo(player, "  {} für hergestellte Items", revenantPlayer.getScore(ScoreType.CRAFTED_ITEMS));
    Msg.sendInfo(player, "  {} für getötete Monster", revenantPlayer.getScore(ScoreType.KILLED_MOBS));
    Msg.sendInfo(player, "  {} für geplünderte Kisten", revenantPlayer.getScore(ScoreType.LOOTED_CHESTS));

    //only for debugging, clear scores properly when player decides to respawn!
    revenantPlayer.clearScores();
  }

}