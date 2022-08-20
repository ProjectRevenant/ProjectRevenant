package com.gestankbratwurst.revenant.projectrevenant.communication;

import io.papermc.paper.chat.ChatRenderer;
import lombok.AllArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

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

      /*
        Gets the absolute block distance as well as a lerped (0.0 -> 1.0) distance
       */

      double distance = source.getLocation().distanceSquared(targetPlayer.getLocation());
      float lerpedDistance = (float) ((1.0 / maxDistance) * distance);

      /*
        Obscures Player name to "?????" if distance is larger then maxNamedDistance
       */

      Component styledMessage = distance >= maxNamedDistance ? Component.text("?????") : sourceDisplayName;


      styledMessage = styledMessage.color(TextColor.lerp(lerpedDistance, nearColor, farColor));

      styledMessage = styledMessage.append(Component.text(": ")).append(message.color(textColor));

      return styledMessage;
    }

    return sourceDisplayName.append(Component.text(": ")).append(message.color(textColor));
  }
}
