package com.gestankbratwurst.revenant.projectrevenant;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerDataFlushTask;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerTickTask;
import com.gestankbratwurst.revenant.projectrevenant.data.player.ReventantPlayerListener;
import com.gestankbratwurst.revenant.projectrevenant.debug.DebugCommand;
import com.gestankbratwurst.revenant.projectrevenant.levelsystem.ExperienceCommand;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluationRegistry;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilitySecondTask;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.RevenantDisplayCompiler;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators.ItemStackAbilityEvaluator;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators.LivingEntityAbilityEvaluator;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators.PersistentDataContainerAbilityEvaluator;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.evaluators.PlayerAbilityEvaluator;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.Body;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttributeModifier;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyCommand;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyManager;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyRunnable;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.Bone;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.items.ItemAttributeListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.combat.CombatListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import com.gestankbratwurst.revenant.projectrevenant.ui.actionbar.ActionBarListener;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.TabListListener;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.TabListTask;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class ProjectRevenant extends JavaPlugin {

  private RevenantPlayerManager revenantPlayerManager;
  private BodyManager bodyManager;

  public static RevenantPlayerManager getRevenantPlayerManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).revenantPlayerManager;
  }

  public static BodyManager getBodyManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).bodyManager;
  }

  @Override
  public void onEnable() {
    MMCore.getGsonProvider().registerAbstractClassHierarchy(Body.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(BodyAttributeModifier.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(Ability.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(AbilityEffect.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(Bone.class);

    revenantPlayerManager = new RevenantPlayerManager();
    Bukkit.getPluginManager().registerEvents(new ReventantPlayerListener(revenantPlayerManager), this);
    long flushDelay = RevenantPlayerDataFlushTask.TICKS_BETWEEN_SAVES;
    TaskManager.getInstance().runRepeatedBukkitAsync(new RevenantPlayerDataFlushTask(revenantPlayerManager), flushDelay, flushDelay);
    TaskManager.getInstance().runRepeatedBukkit(new RevenantPlayerTickTask(revenantPlayerManager), 1, 1);

    bodyManager = new BodyManager();
    Bukkit.getPluginManager().registerEvents(new BodyListener(bodyManager), this);
    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("BodyAttribute", Arrays.asList(BodyAttribute.getValues()));
    MMCore.getPaperCommandManager().registerCommand(new BodyCommand(bodyManager));
    TaskManager.getInstance().runRepeatedBukkit(new BodyRunnable(bodyManager), 1, 1);

    Bukkit.getPluginManager().registerEvents(new ItemAttributeListener(bodyManager), this);
    Bukkit.getPluginManager().registerEvents(new CombatListener(bodyManager), this);



    MMCore.getPaperCommandManager().registerCommand(new ExperienceCommand(revenantPlayerManager));

    AbilitySecondTask abilitySecondTask = new AbilitySecondTask();
    Bukkit.getPluginManager().registerEvents(new AbilityListener(abilitySecondTask), this);
    AbilityEvaluationRegistry.register(new PlayerAbilityEvaluator());
    AbilityEvaluationRegistry.register(new LivingEntityAbilityEvaluator());
    AbilityEvaluationRegistry.register(new ItemStackAbilityEvaluator());
    AbilityEvaluationRegistry.register(new PersistentDataContainerAbilityEvaluator());
    MMCore.getDisplayCompiler().registerConverter(new RevenantDisplayCompiler());
    TaskManager.getInstance().runRepeatedBukkit(abilitySecondTask, 1, 1);

    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("RevenantItem", RevenantItem.getInternalNames());
    MMCore.getPaperCommandManager().registerCommand(new DebugCommand());

    MMCore.getTabListManager().setDefaultTabListProvider(player -> new RevenantUserTablist(player.getUniqueId()));
    TabListTask tabListTask = new TabListTask();
    TabListListener tabListListener = new TabListListener(tabListTask);
    TaskManager.getInstance().runRepeatedBukkit(tabListTask, 1, 1);
    Bukkit.getPluginManager().registerEvents(tabListListener, this);
    Bukkit.getPluginManager().registerEvents(new ActionBarListener(bodyManager), this);

    TaskManager.getInstance().runBukkitSync(() -> Bukkit.getWorlds().forEach(world -> {
      world.setGameRule(GameRule.NATURAL_REGENERATION, false);
      world.setGameRule(GameRule.DISABLE_RAIDS, true);
      world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
      world.setGameRule(GameRule.DO_INSOMNIA, false);
      world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
      world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
      world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
      world.setGameRule(GameRule.MOB_GRIEFING, false);
    }));
  }

  @Override
  public void onDisable() {
    revenantPlayerManager.flush();
  }
}
