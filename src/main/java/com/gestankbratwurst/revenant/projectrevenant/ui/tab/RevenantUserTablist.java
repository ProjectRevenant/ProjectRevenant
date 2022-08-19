package com.gestankbratwurst.revenant.projectrevenant.ui.tab;

import com.gestankbratwurst.core.mmcore.resourcepack.skins.TextureModel;
import com.gestankbratwurst.core.mmcore.tablist.implementation.AbstractTabList;
import com.gestankbratwurst.core.mmcore.tablist.implementation.TabLine;
import com.gestankbratwurst.core.mmcore.util.common.UtilMath;
import com.gestankbratwurst.core.mmcore.util.tasks.TaskManager;
import com.gestankbratwurst.revenant.projectrevenant.data.player.RevenantPlayer;
import com.gestankbratwurst.revenant.projectrevenant.levelsystem.LevelContainer;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.cache.EntityAbilityCache;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.TimedAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.RevenantAbility;
import com.gestankbratwurst.revenant.projectrevenant.survival.abilities.implementations.abilities.survival.wet.WetDebuff;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.BodyAttribute;
import com.gestankbratwurst.revenant.projectrevenant.survival.body.human.HumanBody;
import com.gestankbratwurst.revenant.projectrevenant.survival.worldenvironment.WorldEnvironmentFetcher;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class RevenantUserTablist extends AbstractTabList {

  private final UUID userId;

  @Override
  public void init() {
    this.setHeader(TextureModel.REVENANT_TITLE.getChar() + "\n".repeat(4));
    for (int i = 0; i < 4 * 20; i++) {
      this.addLine(new TabLine(i, ""));
    }
    TaskManager.getInstance().runBukkitSync(() -> {
      this.updateDisplay(0, String.valueOf(TextureModel.TAB_ICON_STATS.getChar()));
      this.updateDisplay(20, String.valueOf(TextureModel.TAB_ICON_BODY.getChar()));
      this.updateDisplay(40, String.valueOf(TextureModel.TAB_ICON_EFFECTS.getChar()));
      this.updateDisplay(60, String.valueOf(TextureModel.TAB_ICON_SERVER.getChar()));
    });

    this.setFooter("");
  }

  public void updateBody() {
    HumanBody body = RevenantPlayer.of(userId).getBody();
    BodyAttribute health = body.getAttribute(BodyAttribute.HEALTH);
    BodyAttribute water = body.getAttribute(BodyAttribute.WATER);
    BodyAttribute nutrition = body.getAttribute(BodyAttribute.NUTRITION);
    BodyAttribute weight = body.getAttribute(BodyAttribute.WEIGHT);

    double healthPercent = 1.0 / health.getMaxValueModified() * health.getCurrentValue();
    double waterPercent = 1.0 / water.getMaxValueModified() * water.getCurrentValue();
    double nutritionPercent = 1.0 / nutrition.getMaxValueModified() * nutrition.getCurrentValue();
    double weightPercent = 1.0 / weight.getMaxValueModified() * weight.getCurrentValue();

    String healthPrefix = healthPercent < 0.25 ? "§c" : "§f";
    String waterPrefix = waterPercent < 0.25 ? "§c" : "§f";
    String nutritionPrefix = nutritionPercent < 0.25 ? "§c" : "§f";
    String weightPrefix = weightPercent < 0.80 ? "§f" : "§c";

    updateDisplay(22, "§eLeben:" + healthPrefix + " %.1f §9hp".formatted(health.getCurrentValue()));
    updateDisplay(23, "§eWasser:" + waterPrefix + " %.2f §9L".formatted(water.getCurrentValue()));
    updateDisplay(24, "§eNahrung:" + nutritionPrefix + " %.0f §9kcal".formatted(nutrition.getCurrentValue()));
    updateDisplay(25, "§eGewicht:" + weightPrefix + " %.2f §9kg".formatted(weight.getCurrentValue()));

    int infoIndex = 27;
    if (waterPercent < 0.1) {
      updateDisplay(infoIndex++, "§cDu verdurstest!");
    }
    if (nutritionPercent < 0.1) {
      updateDisplay(infoIndex++, "§cDu verhungerst!");
    }
    if (weightPercent >= 1.0) {
      updateDisplay(infoIndex++, "§cDu bist überladen!");
    }

    while (infoIndex < 32) {
      updateDisplay(infoIndex++, "");
    }
  }

  public void updateStatistics() {
    RevenantPlayer revenantPlayer = RevenantPlayer.of(userId);
    LevelContainer container = revenantPlayer.getLevelContainer();
    int level = container.getCurrentLevel();
    int expProgress = (int) container.getExperienceProgressTowardsNextLevel();
    int expNeeded = (int) container.getExperienceTowardsNextLevel();
    this.updateDisplay(2, "§eLevel: §f" + level);
    this.updateDisplay(4, "§eExp: §f" + expProgress + "/" + expNeeded);
    String bar = UtilMath.getPercentageBar(expProgress, expNeeded, 50, "|");
    this.updateDisplay(5, "§f[" + bar + "§f]");
  }

  public void updateEffects() {
    AtomicInteger effectIndex = new AtomicInteger(42);

    Player player = Bukkit.getPlayer(userId);

    EntityAbilityCache.getAbilities(this.userId).forEach(ability -> {
      if (ability.shouldDisplayInTab()) {
        if (ability instanceof TimedAbility timedAbility) {
          if (!timedAbility.isDone()) {
            if (effectIndex.get() <= 52) {
              String line = PlainTextComponentSerializer.plainText().serialize(ability.getInfoTitle(player));
              updateDisplay(effectIndex.getAndIncrement(), line);
            }
          }
        } else {
          if (effectIndex.get() <= 52) {
            String line = PlainTextComponentSerializer.plainText().serialize(ability.getInfoTitle(player));
            updateDisplay(effectIndex.getAndIncrement(), line);
          }
        }
      }
    });

    while (effectIndex.get() <= 52) {
      updateDisplay(effectIndex.getAndIncrement(), "");
    }
  }

  public void updateServerInfo() {
    int online = Bukkit.getOnlinePlayers().size();
    int max = Bukkit.getMaxPlayers();
    double tps = ((int) (Bukkit.getTPS()[1] * 10.0)) / 10.0;
    int ping = Objects.requireNonNull(Bukkit.getPlayer(userId)).getPing();
    this.updateDisplay(62, "§eOnline: §f" + online + "/" + max);
    this.updateDisplay(64, "§eTPS: §f" + tps);
    this.updateDisplay(65, "§ePing: §f" + ping + "ms");
    Player player = Bukkit.getPlayer(this.userId);
    if (player != null && player.isOp()) {
      HumanBody body = RevenantPlayer.of(player).getBody();
      BodyAttribute tempAttr = body.getAttribute(BodyAttribute.TEMPERATURE);
      BodyAttribute tempGradAttr = body.getAttribute(BodyAttribute.TEMPERATURE_SHIFT);
      this.updateDisplay(67, "§cDebug Info [Admin only]");
      this.updateDisplay(68, "§eEnv Temp: §f%.1f".formatted(WorldEnvironmentFetcher.getTemperatureAt(player.getLocation(), true)) + " §9°C");
      this.updateDisplay(69, "§eBody Temp: §f%.1f".formatted(tempAttr.getCurrentValue()) + " §9°C");
      this.updateDisplay(70, "§eTemp Gradient: §f%.1f".formatted(tempGradAttr.getCurrentValue() * 20 * 60) + " §9°C/min");
      this.updateDisplay(71, "§eDrying: §f%s".formatted(
              EntityAbilityCache.getAbilities(player.getUniqueId())
                      .stream()
                      .anyMatch(ability -> ability.getIdentifier().equals(RevenantAbility.DRY_BUFF)))
      );
      this.updateDisplay(72, "§eWetness: §f%.3f".formatted(
                      EntityAbilityCache.getAbilities(player.getUniqueId())
                              .stream()
                              .filter(ability -> ability.getIdentifier().equals(RevenantAbility.WET_DEBUFF))
                              .map(WetDebuff.class::cast)
                              .findAny()
                              .map(WetDebuff::getLitres)
                              .orElse(0.0)
              ) + " §9L"
      );
    }
  }

}
