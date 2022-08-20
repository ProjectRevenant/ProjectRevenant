package com.gestankbratwurst.revenant.projectrevenant.communication;

import io.papermc.paper.configuration.serializer.ComponentSerializer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;

/**
 * Listens for Chat Events and manages the Proximity Chat
 */

public class ChatListener implements Listener {

  //TODO fehlende Werte & Überprüfungen, dann testen

  /**
   * normalProximityDistance -> Maximum chat range when in "normal" mode
   * shoutingProximityDistance -> Maximum chat range when in "shouting" mode
   * maxNameDistance -> Distance at which the player name is hidden
   * shoutingChar -> Character that determines if player is shouting
   * nearColor -> Color of player name when near to recipient
   * farNormalColor -> Color of player name when far away and in "normal" mode
   * farShoutingColor -> Color of player name when far away and in "shouting" mode
   * textColor -> Color of chat message
   */

  private static final int normalProximityDistance = 100;
  private static final int shoutingProximityDistance = 150;
  private static final int maxNamedDistance = 50;
  private static final char shoutingChar = '!';
  private static final TextColor nearColor = TextColor.color(224, 224, 224);
  private static final TextColor farNormalColor = TextColor.color(90, 88, 88);
  private static final TextColor farShoutingColor = TextColor.color(131, 31, 23);
  private static final TextColor textColor = TextColor.color(226, 226, 226);


  @EventHandler
  public void onChat(AsyncChatEvent event) {

    Player eventPlayer = event.getPlayer();

    event.viewers().clear();

    String messageText = PlainTextComponentSerializer.plainText().serialize(event.message());

    boolean shouting = messageText.startsWith(shoutingChar + "");

    Collection<Player> inRadius;

    if(shouting){
       inRadius = eventPlayer.getLocation().getNearbyPlayers(normalProximityDistance);
    } else {
      inRadius = eventPlayer.getLocation().getNearbyPlayers(shoutingProximityDistance);
    }

    for (Player player : inRadius) {
      event.viewers().add(player);
    }

    if(shouting){
      event.renderer(new ProximityChatRenderer(shoutingProximityDistance, maxNamedDistance, nearColor, farShoutingColor, textColor));
    } else {
      event.renderer(new ProximityChatRenderer(normalProximityDistance, maxNamedDistance, nearColor, farNormalColor, textColor));
    }

  }

}
