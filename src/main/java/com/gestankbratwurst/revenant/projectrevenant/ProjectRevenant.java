package com.gestankbratwurst.revenant.projectrevenant;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.InvalidCommandArgument;
import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.communication.ChatListener;
import com.gestankbratwurst.revenant.projectrevenant.crafting.CraftingListener;
import com.gestankbratwurst.revenant.projectrevenant.crafting.RevenantRecipeManager;
import com.gestankbratwurst.revenant.projectrevenant.crafting.ingredients.Ingredient;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStation;
import com.gestankbratwurst.revenant.projectrevenant.crafting.station.CraftingStationManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerDataFlushTask;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayerTickTask;
import com.gestankbratwurst.revenant.projectrevenant.data.player.ReventantPlayerListener;
import com.gestankbratwurst.revenant.projectrevenant.debug.DebugCommand;
import com.gestankbratwurst.revenant.projectrevenant.levelsystem.ExperienceCommand;
import com.gestankbratwurst.revenant.projectrevenant.levelsystem.MinecraftExpListener;
import com.gestankbratwurst.revenant.projectrevenant.loot.LootListener;
import com.gestankbratwurst.revenant.projectrevenant.loot.chestloot.LootChestSpawnArea;
import com.gestankbratwurst.revenant.projectrevenant.loot.generators.LootType;
import com.gestankbratwurst.revenant.projectrevenant.loot.manager.LootChestManager;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomEntityType;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobListener;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobManager;
import com.gestankbratwurst.revenant.projectrevenant.mobs.CustomMobType;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.SpawnSystemListener;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.GlobalSpawnManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.GlobalSpawnTask;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.ChunkHeatManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.global.ChunkHeatTask;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.player.PlayerSpawnListener;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.player.PlayerSpawnManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.RevenantSpawner;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerCommand;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerManager;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerRunnable;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.spawner.SpawnerType;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEffect;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityEvaluationRegistry;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilitySecondTask;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.AbilityTrigger;
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
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.BoneType;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.bones.SkeletonCommand;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.items.ItemAttributeListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.combat.CombatListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.combat.CombatPacketAdapter;
import com.gestankbratwurst.revenant.projectrevenant.survival.items.RevenantItem;
import com.gestankbratwurst.revenant.projectrevenant.ui.RevenantDisplayCompiler;
import com.gestankbratwurst.revenant.projectrevenant.ui.actionbar.ActionBarListener;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.TabListListener;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.TabListTask;
import com.gestankbratwurst.revenant.projectrevenant.util.gson.AbilityTriggerSerializer;
import com.gestankbratwurst.revenant.projectrevenant.util.gson.BlockDataSerializer;
import com.gestankbratwurst.revenant.projectrevenant.util.gson.DurationSerializer;
import com.gestankbratwurst.revenant.projectrevenant.util.gson.PotionEffectSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class ProjectRevenant extends JavaPlugin {

  private RevenantPlayerManager revenantPlayerManager;
  private BodyManager bodyManager;
  private LootChestManager lootChestManager;
  private ChunkHeatManager chunkHeatManager;
  private GlobalSpawnManager globalSpawnManager;
  private SpawnerManager spawnerManager;
  private RevenantRecipeManager revenantRecipeManager;
  private CraftingStationManager craftingStationManager;
  private PlayerSpawnManager playerSpawnManager;

  public static RevenantPlayerManager getRevenantPlayerManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).revenantPlayerManager;
  }

  public static SpawnerManager getSpawnerManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).spawnerManager;
  }

  public static ChunkHeatManager getChunkHeatManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).chunkHeatManager;
  }

  public static GlobalSpawnManager getGlobalSpawnManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).globalSpawnManager;
  }

  public static BodyManager getBodyManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).bodyManager;
  }

  public static LootChestManager getLootChestManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).lootChestManager;
  }

  public static CraftingStationManager getCraftingStationManager(){
    return JavaPlugin.getPlugin(ProjectRevenant.class).craftingStationManager;
  }

  public static RevenantRecipeManager getRevenantRecipeManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).revenantRecipeManager;
  }

  public static PlayerSpawnManager getPlayerSpawnManager() {
    return JavaPlugin.getPlugin(ProjectRevenant.class).playerSpawnManager;
  }

  @Override
  public void onLoad() {
    CustomEntityType.touch();
  }

  @Override
  public void onEnable() {
    setupGsonTypeAdapters();

    setupRevenantPlayerManager();

    setupBodyManager();

    MMCore.getProtocolManager().addPacketListener(new CombatPacketAdapter());

    setupLootManager();

    setupRecipeManager();

    Bukkit.getPluginManager().registerEvents(new ItemAttributeListener(bodyManager), this);
    Bukkit.getPluginManager().registerEvents(new CombatListener(bodyManager), this);
    Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    Bukkit.getPluginManager().registerEvents(new MinecraftExpListener(), this);
    Bukkit.getPluginManager().registerEvents(new LootListener(lootChestManager), this);

    setupCustomMobManager();

    setupAbilityManager();

    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("RevenantItem", RevenantItem.getInternalNames());
    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("LootType", Arrays.stream(LootType.values()).map(Enum::toString).toList());
    MMCore.getPaperCommandManager().registerCommand(new DebugCommand());

    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("CustomMobType", Arrays.stream(CustomMobType.values()).map(Enum::toString).toList());

    setupSpawnerManager();

    setupUI();

    setupGamerules();
  }

  private void setupRecipeManager() {
    Bukkit.clearRecipes();
    this.revenantRecipeManager = new RevenantRecipeManager();
    this.craftingStationManager = new CraftingStationManager();
    this.craftingStationManager.init();
    Bukkit.getPluginManager().registerEvents(new CraftingListener(), this);
    Arrays.stream(BaseRecipe.values()).map(BaseRecipe::getRevenantRecipe).forEach(revenantRecipeManager::registerRecipe);
    TaskManager.getInstance().runRepeatedBukkit(craftingStationManager::tickStations, 20, 1);
  }

  private static void setupGamerules() {
    TaskManager.getInstance().runBukkitSync(() -> Bukkit.getWorlds().forEach(world -> {
      world.setGameRule(GameRule.NATURAL_REGENERATION, false);
      world.setGameRule(GameRule.DISABLE_RAIDS, true);
      world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
      world.setGameRule(GameRule.DO_INSOMNIA, false);
      world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
      world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
      world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
      world.setGameRule(GameRule.MOB_GRIEFING, false);
      world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }));
  }

  private void setupUI() {
    MMCore.getTabListManager().setDefaultTabListProvider(player -> new RevenantUserTablist(player.getUniqueId()));
    TabListTask tabListTask = new TabListTask();
    TabListListener tabListListener = new TabListListener(tabListTask);
    TaskManager.getInstance().runRepeatedBukkit(tabListTask, 40, 1);
    Bukkit.getPluginManager().registerEvents(tabListListener, this);
    Bukkit.getPluginManager().registerEvents(new ActionBarListener(bodyManager), this);
  }

  private void setupSpawnerManager() {
    this.playerSpawnManager = PlayerSpawnManager.create();
    this.chunkHeatManager = new ChunkHeatManager();
    this.globalSpawnManager = new GlobalSpawnManager();
    this.spawnerManager = SpawnerManager.create();
    Bukkit.getPluginManager().registerEvents(new PlayerSpawnListener(), this);
    Bukkit.getPluginManager().registerEvents(new SpawnSystemListener(chunkHeatManager, spawnerManager, globalSpawnManager), this);
    TaskManager.getInstance().runRepeatedBukkitAsync(new ChunkHeatTask(chunkHeatManager), 60, 20);
    TaskManager.getInstance().runRepeatedBukkit(new GlobalSpawnTask(globalSpawnManager), 60, 40);
    TaskManager.getInstance().runRepeatedBukkit(new SpawnerRunnable(spawnerManager), 60, 1);
    MMCore.getPaperCommandManager().getCommandCompletions().registerCompletion("RevenantSpawner", context -> spawnerManager.getAllSpawnerNames());
    MMCore.getPaperCommandManager().getCommandContexts().registerContext(RevenantSpawner.class, context -> spawnerManager.getSpawnerByName(context.popFirstArg()));
    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("RevenantSpawnerType", Arrays.stream(SpawnerType.values()).map(Enum::toString).toList());
    MMCore.getPaperCommandManager().registerCommand(new SpawnerCommand(spawnerManager));
  }

  private void setupAbilityManager() {
    AbilitySecondTask abilitySecondTask = new AbilitySecondTask();
    Bukkit.getPluginManager().registerEvents(new AbilityListener(abilitySecondTask), this);
    AbilityEvaluationRegistry.register(new PlayerAbilityEvaluator());
    AbilityEvaluationRegistry.register(new LivingEntityAbilityEvaluator());
    AbilityEvaluationRegistry.register(new ItemStackAbilityEvaluator());
    AbilityEvaluationRegistry.register(new PersistentDataContainerAbilityEvaluator());
    MMCore.getDisplayCompiler().registerConverter(new RevenantDisplayCompiler());
    TaskManager.getInstance().runRepeatedBukkit(abilitySecondTask, 1, 1);
  }

  private void setupCustomMobManager() {
    CustomMobManager.setupLiveEntities();
    Bukkit.getPluginManager().registerEvents(new CustomMobListener(), this);
    MMCore.getPaperCommandManager().registerCommand(new ExperienceCommand(revenantPlayerManager));
  }

  private void setupLootManager() {
    lootChestManager = LootChestManager.create();
    long lootManagerFlushDelay = Duration.ofMinutes(15).toSeconds() * 20;
    TaskManager.getInstance().runRepeatedBukkitAsync(() -> lootChestManager.flush(), lootManagerFlushDelay, lootManagerFlushDelay);
    TaskManager.getInstance().runRepeatedBukkit(() -> lootChestManager.checkRespawnQueue(), 0, 10);

    MMCore.getPaperCommandManager().getCommandCompletions().registerCompletion("LootChestArea", context -> lootChestManager.getSpawnAreaNames());
    MMCore.getPaperCommandManager().getCommandContexts().registerContext(LootChestSpawnArea.class, context -> lootChestManager.getSpawnArea(context.popFirstArg()));
  }

  private void setupBodyManager() {
    bodyManager = new BodyManager();
    Bukkit.getPluginManager().registerEvents(new BodyListener(bodyManager), this);
    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("BodyAttribute", Arrays.asList(BodyAttribute.getValues()));
    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("Illness", List.of(
            "BLEEDING_I",
            "BLEEDING_II",
            "BLEEDING_III",
            "BLEEDING_IV",
            "BLEEDING_V",
            "INFECTION"
    ));
    MMCore.getPaperCommandManager().registerCommand(new BodyCommand(bodyManager));
    MMCore.getPaperCommandManager().getCommandCompletions().registerStaticCompletion("@BoneType", Arrays.asList(BoneType.values()));
    MMCore.getPaperCommandManager().registerCommand(new SkeletonCommand());
    TaskManager.getInstance().runRepeatedBukkit(new BodyRunnable(bodyManager), 1, 1);
  }

  private void setupRevenantPlayerManager() {
    revenantPlayerManager = new RevenantPlayerManager();
    Bukkit.getPluginManager().registerEvents(new ReventantPlayerListener(revenantPlayerManager), this);
    long flushDelay = RevenantPlayerDataFlushTask.TICKS_BETWEEN_SAVES;
    TaskManager.getInstance().runRepeatedBukkitAsync(new RevenantPlayerDataFlushTask(revenantPlayerManager), flushDelay, flushDelay);
    TaskManager.getInstance().runRepeatedBukkit(new RevenantPlayerTickTask(revenantPlayerManager), 1, 1);
  }

  private void setupGsonTypeAdapters() {
    MMCore.getGsonProvider().registerTypeHierarchyAdapter(BlockData.class, new BlockDataSerializer());
    MMCore.getGsonProvider().registerAbstractClassHierarchy(Body.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(BodyAttributeModifier.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(Ability.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(AbilityEffect.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(Bone.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(RevenantSpawner.class);
    MMCore.getGsonProvider().registerAbstractClassHierarchy(CraftingStation.class);
    MMCore.getGsonProvider().registerTypeAdapter(AbilityTrigger.class, new AbilityTriggerSerializer());
    MMCore.getGsonProvider().registerTypeAdapter(Duration.class, new DurationSerializer());
    MMCore.getGsonProvider().registerTypeAdapter(PotionEffect.class, new PotionEffectSerializer());
  }

  @Override
  public void onDisable() {
    craftingStationManager.terminate();
    revenantPlayerManager.flush();
    lootChestManager.flush();
    spawnerManager.flush();
  }
}
