package com.gestankbratwurst.revenant.projectrevenant.data.player;

import com.gestankbratwurst.core.mmcore.MMCore;
import com.gestankbratwurst.core.mmcore.data.json.DeserializationPostProcessable;
import com.gestankbratwurst.core.mmcore.data.mongodb.annotationframework.Identity;
import com.gestankbratwurst.core.mmcore.tablist.implementation.AbstractTabList;
import com.gestankbratwurst.core.mmcore.util.Msg;
import com.gestankbratwurst.revenant.projectrevenant.ProjectRevenant;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.BaseRecipes;
import com.gestankbratwurst.revenant.projectrevenant.crafting.recipes.RevenantRecipe;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.levelsystem.LevelContainer;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks.Perk;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks.PerkAbility;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.perks.PerkRegistry;
import com.gestankbratwurst.revenant.projectrevenant.metaprogression.score.ScoreType;
import com.gestankbratwurst.revenant.projectrevenant.spawnsystem.player.PlayerSpawnListener;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.Ability;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.Mergeable;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import com.gestankbratwurst.revenant.projectrevenant.ui.tab.RevenantUserTablist;
import com.gestankbratwurst.revenant.projectrevenant.util.Position;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class RevenantPlayer implements DeserializationPostProcessable {

  public static RevenantPlayer of(Player player) {
    return of(player.getUniqueId());
  }

  public static RevenantPlayer of(UUID playerId) {
    return ProjectRevenant.getRevenantPlayerManager().getOnline(playerId);
  }

  private static final double sprintingNoiseMod = 2;
  private static final double sneakingNoiseMod = 0.5;

  public static final int damageLogDuration = 30 * 20;

  @Identity
  private final UUID playerId;
  @Getter
  private final LevelContainer levelContainer;
  private final Map<Class<? extends Ability>, Ability> abilityMap;
  private final transient BossBar experienceBossBar;
  private transient int levelBarCounter = 0;
  private final Set<UUID> unlockedRecipes;
  //Survived Time
  @Getter
  @Setter
  private long survivalTime;
  @Getter
  @Setter
  private long joinTimestamp;
  //Stash
  @Getter
  private int stashSize = 3;
  private final List<ItemStack> stashItems;
  //Spawning & Logout/ins
  @Getter
  @Setter
  private Position logoutPosition = Position.ZERO;
  @Getter
  @Setter
  private boolean inLobby;
  @Getter
  private int damageLog;
  //Score
  private final Map<ScoreType, Integer> scoreMap;
  //Perks
  @Getter
  private int availablePerkPoints = 0;
  @Getter
  private int spentPerkPoints = 0;
  private final Set<Class<? extends PerkAbility>> chosenPerks;

  public RevenantPlayer(UUID playerId) {
    this.playerId = playerId;
    this.levelContainer = new LevelContainer();
    this.experienceBossBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_10);
    this.abilityMap = new HashMap<>();
    this.scoreMap = new HashMap<>();
    this.chosenPerks = new HashSet<>();
    this.unlockedRecipes = new HashSet<>();
    this.stashItems = new ArrayList<>();
    for (BaseRecipes recipe : BaseRecipes.values()) {
      if (recipe.isStartingRecipe()) {
        //ToDo remove player message, only for debugging
        unlockRecipe(recipe.getRevenantRecipe(), true);
      }
    }
  }

  protected RevenantPlayer() {
    this(null);
  }

  public Set<Class<? extends PerkAbility>> getChosenPerks() {
    return new HashSet<>(chosenPerks);
  }

  public <T extends PerkAbility> void addPerk(Class<T> perkClass) {
    chosenPerks.add(perkClass);
    ensurePerkIntegrity();
  }

  public void grantPerkPoints(int amount) {
    availablePerkPoints += amount;
  }

  public void spendPerPoints(int amount) {
    Preconditions.checkArgument(amount <= availablePerkPoints, "Cant spend more points than available.");
    availablePerkPoints -= amount;
    spentPerkPoints += amount;
  }

  public void setDamageLog(){
    this.damageLog = damageLogDuration;
  }

  public boolean inCombat(){
    return damageLog != 0;
  }

  public void setStashItems(List<ItemStack> newStashItems) {
    stashItems.clear();
    stashItems.addAll(newStashItems);
  }

  public List<ItemStack> getStashItems() {
    return new ArrayList<>(stashItems);
  }

  public void increaseStashSize(int value) {
    stashSize += value;
  }

  public void addSurvivalTime(long survivalTime) {
    this.survivalTime += survivalTime;
  }

  public void addScore(ScoreType type, int score) {
    scoreMap.compute(type, (key, curValue) -> curValue == null ? score : curValue + score);
  }

  public void clearScores() {
    scoreMap.clear();
  }

  public int getScore() {
    return scoreMap.values().stream().mapToInt(Integer::intValue).sum();
  }

  public int getScore(ScoreType type) {
    return scoreMap.getOrDefault(type, 0);
  }

  public void unlockRecipe(RevenantRecipe recipe, boolean playerMessage) {
    unlockedRecipes.add(recipe.getId());

    if (playerMessage) {
      applyToOnlinePlayer(player -> Msg.sendInfo(player, "??7Du hast das ??e" + recipe.getName() + "??7-Rezept freigeschaltet!"));
    }
  }

  public boolean hasRecipeUnlocked(UUID recipeId) {
    return unlockedRecipes.contains(recipeId);
  }

  public double getNoiseLevel() {
    Player player = getBukkitPlayer();
    double base = getBody().getAttribute(BodyAttribute.NOISE).getCurrentValueModified();

    if (player.isSprinting()) {
      return base * sprintingNoiseMod;
    }

    if (player.isSneaking()) {
      return base * sneakingNoiseMod;
    }

    return base;
  }

  public boolean isInSpawnPod() {
    return getBukkitPlayer().getScoreboardTags().contains(PlayerSpawnListener.SPAWN_POD_TAG);
  }

  public double getNoiseLevelAt(Location location) {
    Player player = getBukkitPlayer();
    double distanceSq = Math.max(location.distanceSquared(player.getLocation()), 1.0);
    double playerNoise = getNoiseLevel();

    double distNoiseScalar = (1.0010005) / Math.exp(Math.tanh(distanceSq * 0.0008) * distanceSq * 0.00015);

    return playerNoise * distNoiseScalar;
  }

  public Player getBukkitPlayer() {
    Player player = Bukkit.getPlayer(this.playerId);

    if (player == null) {
      throw new RuntimeException("Tried to create null-player from RevenantPlayer UUID");
    }
    return player;
  }

  public void pauseAbilities() {
    getActiveAbilities().stream().filter(TimedAbility.class::isInstance).map(TimedAbility.class::cast).forEach(TimedAbility::pause);
  }

  public void unpauseAbilities() {
    getActiveAbilities().stream().filter(TimedAbility.class::isInstance).map(TimedAbility.class::cast).forEach(TimedAbility::unpause);
  }

  public void updateAbilities(List<Ability> abilityList) {
    abilityMap.clear();
    abilityList.forEach(ability -> abilityMap.put(ability.getClass(), ability));
    Player player = Bukkit.getPlayer(playerId);
    if (player == null) {
      return;
    }
    ensurePerkIntegrity();
    EntityAbilityCache.autoUpdate(player, Player.class);
  }

  public <T extends Ability> boolean hasAbility(Class<T> identifier) {
    return abilityMap.containsKey(identifier);
  }

  public boolean hasAbility(Ability ability) {
    return hasAbility(ability.getClass());
  }

  public boolean hasPerk(Perk<?> perk) {
    return chosenPerks.contains(perk.getAbilityClass());
  }

  @SuppressWarnings("unchecked")
  public <T extends Ability> T getAbility(Class<T> identifier) {
    return (T) abilityMap.get(identifier);
  }

  public Collection<Ability> getActiveAbilities() {
    return abilityMap.values();
  }

  public void cleanTimedOutAbilities() {
    abilityMap.values().removeIf(ability -> ability instanceof TimedAbility timed && timed.isDone() && timed.hasStarted());
  }

  @SuppressWarnings("unchecked")
  public void addAbility(Ability ability) {
    if (ability instanceof Mergeable<?> && hasAbility(ability)) {
      ((Mergeable<Ability>) getAbility(ability.getClass())).merge(ability);
    } else {
      abilityMap.put(ability.getClass(), ability);
    }
    Player player = Bukkit.getPlayer(playerId);
    if (player == null) {
      return;
    }
    ensurePerkIntegrity();
    EntityAbilityCache.autoUpdate(player, Player.class);
  }

  public <T extends Ability> void removeAbility(Class<T> identifier) {
    abilityMap.remove(identifier);
    Player player = Bukkit.getPlayer(playerId);
    if (player == null) {
      return;
    }
    ensurePerkIntegrity();
    EntityAbilityCache.autoUpdate(player, Player.class);
  }

  public void ensurePerkIntegrity() {
    chosenPerks.forEach(perkClass -> {
      if (!hasAbility(perkClass)) {
        addAbility(PerkRegistry.createPerkInstance(perkClass));
      }
    });
  }

  public void removeAbility(Ability ability) {
    removeAbility(ability.getClass());
  }

  public RevenantUserTablist getTabList() {
    Player player = getBukkitPlayer();
    AbstractTabList tabList = MMCore.getTabListManager().getView(player).getTablist();
    if (!(tabList instanceof RevenantUserTablist userTablist)) {
      return null;
    }
    return userTablist;
  }

  public HumanBody getBody() {
    return Optional.ofNullable(getBukkitPlayer())
            .map(ProjectRevenant.getBodyManager()::getBody)
            .map(HumanBody.class::cast)
            .orElse(null);
  }

  public void addExperience(double exp) {
    int levelBefore = levelContainer.getCurrentLevel();
    levelContainer.addExp(exp);
    experienceCheckup(levelBefore);
  }

  public void removeExperience(double exp) {
    int levelBefore = levelContainer.getCurrentLevel();
    levelContainer.removeExp(exp);
    experienceCheckup(levelBefore);
  }

  private void experienceCheckup(int levelBefore) {
    showLevelBar();
    int levelAfter = levelContainer.getCurrentLevel();
    if (levelAfter > levelBefore) {
      onLevelUp();
    }
    applyToOnlinePlayer(player -> {
      if (MMCore.getTabListManager().getView(player).getTablist() instanceof RevenantUserTablist userTablist) {
        userTablist.updateStatistics();
      }
    });
  }

  protected void tick() {
    if(damageLog > 0){
      damageLog--;
    }
    if (levelBarCounter > 0) {
      levelBarCounter--;
      if (levelBarCounter == 0) {
        experienceBossBar.setVisible(false);
      }
    }
  }

  private void showLevelBar() {
    applyToOnlinePlayer(player -> {
      experienceBossBar.setProgress(levelContainer.getProgressPercent());
      int nextExp = (int) levelContainer.getExperienceTowardsNextLevel();
      int currentExp = (int) levelContainer.getExperienceProgressTowardsNextLevel();
      experienceBossBar.setTitle("??e" + currentExp + " / " + nextExp + " [??6" + (levelContainer.getCurrentLevel() + 1) + "??e]");
      experienceBossBar.addPlayer(player);
      experienceBossBar.setVisible(true);
      levelBarCounter = 30;
    });
  }

  private void onLevelUp() {
    int gainedPoints = Math.max(1, levelContainer.getCurrentLevel() % 10);
    this.grantPerkPoints(gainedPoints);
    applyToOnlinePlayer(player -> {
      player.playSound(player.getEyeLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
      Title.Times times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(1));
      Title title = Title.title(Component.text(""), Component.text("??eYou are now level ??6" + levelContainer.getCurrentLevel()), times);
      player.showTitle(title);
    });
  }

  private void applyToOnlinePlayer(Consumer<Player> consumer) {
    Optional.ofNullable(Bukkit.getPlayer(playerId)).ifPresent(consumer);
  }

  @Override
  public void gsonPostProcess() {
    for (BaseRecipes recipe : BaseRecipes.values()) {
      if (recipe.isStartingRecipe()) {
        unlockRecipe(recipe.getRevenantRecipe(), false);
      }
    }
    System.out.println("Recipes: " + Arrays.toString(this.unlockedRecipes.toArray()));
  }
}
