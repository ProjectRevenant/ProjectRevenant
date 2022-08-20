package com.gestankbratwurst.revenant.projectrevenant.communication;

import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * Listens for Chat Events and manages the Proximity Chat
 */

public class ChatListener implements Listener {

  /**
   * normalProximityDistance -> Maximum chat range when in "normal" mode
   * shoutingProximityDistance -> Maximum chat range when in "shouting" mode
   * maxNameDistance -> Distance at which the player name is hidden
   * shoutingChar -> Character that determines if player is shouting
   * nearColor -> Color of player name when near to recipient
   * farColor -> Color of player name when far away and in "normal" mode
   * farShoutingColor -> Color of player name when far away and in "shouting" mode
   * textColor -> Color of chat message
   */

  private static final int normalProximityDistance = 80;
  private static final int shoutingProximityDistance = 150;
  private static final int maxNamedDistance = 50;
  private static final char shoutingChar = '!';
  private static final TextColor nearColor = TextColor.color(224, 224, 224);
  private static final TextColor farColor = TextColor.color(71, 67, 67);
  private static final TextColor nearShoutingColor = TextColor.color(131, 31, 23);
  private static final TextColor textColor = TextColor.color(169, 185, 191);


  @EventHandler
  public void onChat(AsyncChatEvent event) throws ExecutionException, InterruptedException {

    Player eventPlayer = event.getPlayer();

    event.viewers().clear();

    String messageText = PlainTextComponentSerializer.plainText().serialize(event.message());

    boolean shouting = messageText.startsWith(shoutingChar + "");

    double distance = shouting ? shoutingProximityDistance : normalProximityDistance;

    Collection<Player> inRadius = TaskManager.getInstance().callSyncMethod(() -> eventPlayer.getLocation().getNearbyPlayers(distance)).get();

    for (Player player : inRadius) {
      event.viewers().add(player);
    }

    if (shouting) {
      event.message(Component.text(messageText.substring(1)));

      event.renderer(new ProximityChatRenderer(shoutingProximityDistance, maxNamedDistance, nearShoutingColor, farColor, textColor));
    } else {
      event.renderer(new ProximityChatRenderer(normalProximityDistance, maxNamedDistance, nearColor, farColor, textColor));
    }

  }

}
