package com.gestankbratwurst.revenant.projectrevenant.survival.abilities;

import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbilityTrigger<T> {

  @Getter
  private final Class<T> triggerClass;
  @Getter
  private final String identifier;

  private AbilityTrigger(Class<T> triggerClass, String identifier) {
    this.triggerClass = triggerClass;
    this.identifier = identifier;
  }

  public AbilityTrigger() {
    this(null, null);
  }

  @Override
  public int hashCode() {
    return identifier.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof AbilityTrigger<?> other && identifier.equals(other.identifier);
  }

  public static final AbilityTrigger<EntityDamageByEntityEvent> COMBAT_ATTACK = new AbilityTrigger<>(EntityDamageByEntityEvent.class, "COMBAT_ATTACK");
  public static final AbilityTrigger<EntityDamageByEntityEvent> COMBAT_DEFEND = new AbilityTrigger<>(EntityDamageByEntityEvent.class, "COMBAT_DEFEND");
  public static final AbilityTrigger<PlayerItemConsumeEvent> CONSUME_ITEM = new AbilityTrigger<>(PlayerItemConsumeEvent.class, "CONSUME_ITEM");
  public static final AbilityTrigger<Body> PASSIVE_ATTRIBUTE = new AbilityTrigger<>(Body.class, "PASSIVE_ATTRIBUTE");
  public static final AbilityTrigger<Player> PLAYER_EVERY_SECOND = new AbilityTrigger<>(Player.class, "PLAYER_EVERY_SECOND");
  public static final AbilityTrigger<PlayerInteractEvent> PLAYER_INTERACT = new AbilityTrigger<>(PlayerInteractEvent.class, "PLAYER_INTERACT");

  private static final Map<String, AbilityTrigger<?>> triggerMap = new ConcurrentHashMap<>();

  public static AbilityTrigger<?> fromIdentifier(String identifier) {
    return triggerMap.get(identifier);
  }

  static {
    try {
      Method idMethod = AbilityTrigger.class.getMethod("getIdentifier");
      for (Field field : AbilityTrigger.class.getDeclaredFields()) {
        if (Modifier.isStatic(field.getModifiers()) && field.getType() == AbilityTrigger.class) {
          AbilityTrigger<?> trigger = (AbilityTrigger<?>) field.get(null);
          String identifier = (String) idMethod.invoke(trigger);
          triggerMap.put(identifier, trigger);
        }
      }
    } catch (ReflectiveOperationException exception) {
      exception.printStackTrace();
    }
  }

}
