package com.gestankbratwurst.revenant.projectrevenant.mobs;

import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.mobs.implementations.RevenantZombie;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;

public class CustomEntityType {

  public static void touch() {
    JavaPlugin.getPlugin(ProjectRevenant.class).getLogger().info("Registering custom entity types");
  }

  private static void setFrozen(boolean value) {
    DefaultedRegistry<EntityType<?>> registry = Registry.ENTITY_TYPE;
    if (value) {
      registry.freeze();
      return;
    }
    Class<MappedRegistry> registryClass = MappedRegistry.class;

    try {
      Field frozenField = registryClass.getDeclaredField("ca");
      frozenField.setAccessible(true);
      frozenField.set(registry, false);
      frozenField.setAccessible(false);

      Field mapField = registryClass.getDeclaredField("cc");
      mapField.setAccessible(true);
      mapField.set(registry, new HashMap<EntityType<?>, Holder.Reference<EntityType<?>>>());
      mapField.setAccessible(false);
    } catch (ReflectiveOperationException exception) {
      exception.printStackTrace();
    }
  }

  static {
    setFrozen(false);
    REVENANT_ZOMBIE = register("revenant-zombie", EntityType.Builder.<RevenantZombie>of(RevenantZombie::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));;
    setFrozen(true);
  }

  public static final EntityType<RevenantZombie> REVENANT_ZOMBIE;

  private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
    return Registry.register(Registry.ENTITY_TYPE, id, type.build(id));
  }

}