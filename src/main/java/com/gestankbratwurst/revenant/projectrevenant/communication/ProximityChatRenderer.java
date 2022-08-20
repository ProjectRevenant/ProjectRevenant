package com.gestankbratwurst.revenant.projectrevenant.communication;

import io.papermc.paper.chat.ChatRenderer;
import lombok.AllArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Used to render proximity chat messages with their correct color
 */

@AllArgsConstructor
public class ProximityChatRenderer implements ChatRenderer {

  private double maxDistance;
  private double maxNamedDistance;
  private TextColor nearColor;
  private TextColor farColor;
  private TextColor textColor;

  @Override
  public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {

    if (viewer instanceof Player targetPlayer) {

      double maxDistanceSq = maxDistance * maxNamedDistance;
      double distanceSq = source.getLocation().distanceSquared(targetPlayer.getLocation());
      float lerpedDistance = (float) ((1.0 / maxDistanceSq) * distanceSq);

      Component styledMessage = distanceSq >= maxDistanceSq ? Component.text("?????") : sourceDisplayName;

      styledMessage = styledMessage.color(TextColor.lerp(lerpedDistance, nearColor, farColor));
      styledMessage = styledMessage.append(Component.text(": ")).append(message.color(textColor));

      return styledMessage;
    }

    return sourceDisplayName.append(Component.text(": ")).append(message.color(textColor));
  }
}
