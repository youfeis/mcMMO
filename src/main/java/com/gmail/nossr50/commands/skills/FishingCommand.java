package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.config.AdvancedConfig;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.fishing.Fishing;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.player.UserManager;

public class FishingCommand extends SkillCommand {
    private int lootTier;
    private String magicChance;
    private String magicChanceLucky;
    private String chanceRaining = "";
    private String shakeChance;
    private String shakeChanceLucky;
    private int fishermansDietRank;

    private boolean canTreasureHunt;
    private boolean canMagicHunt;
    private boolean canShake;
    private boolean canFishermansDiet;

    public FishingCommand() {
        super(SkillType.FISHING);
    }

    @Override
    protected void dataCalculations() {
        // TREASURE HUNTER
        if (canTreasureHunt) {
            lootTier = mcMMOPlayer.getFishingManager().getLootTier();
            double enchantChance = lootTier * AdvancedConfig.getInstance().getFishingMagicMultiplier();

            if (player.getWorld().hasStorm()) {
                chanceRaining = LocaleLoader.getString("Fishing.Chance.Raining");
                enchantChance *= 1.1D;
            }

            String[] treasureHunterStrings = calculateAbilityDisplayValues(enchantChance);
            magicChance = treasureHunterStrings[0];
            magicChanceLucky = treasureHunterStrings[1];
        }

        // SHAKE
        if (canShake) {
            String[] shakeStrings = calculateAbilityDisplayValues(UserManager.getPlayer(player).getFishingManager().getShakeProbability());
            shakeChance = shakeStrings[0];
            shakeChanceLucky = shakeStrings[1];
        }

        // FISHERMAN'S DIET
        if (canFishermansDiet) {
            fishermansDietRank = calculateRank(Fishing.fishermansDietMaxLevel, Fishing.fishermansDietRankLevel1);
        }
    }

    @Override
    protected void permissionsCheck() {
        canTreasureHunt = Permissions.fishingTreasureHunter(player);
        canMagicHunt = Permissions.magicHunter(player);
        canShake = Permissions.shake(player);
        canFishermansDiet = Permissions.fishermansDiet(player);
    }

    @Override
    protected boolean effectsHeaderPermissions() {
        return canTreasureHunt || canMagicHunt || canShake;
    }

    @Override
    protected void effectsDisplay() {
        luckyEffectsDisplay();

        if (canTreasureHunt) {
            player.sendMessage(LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Fishing.Effect.0"), LocaleLoader.getString("Fishing.Effect.1")));
        }

        if (canMagicHunt) {
            player.sendMessage(LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Fishing.Effect.2"), LocaleLoader.getString("Fishing.Effect.3")));
        }

        if (canShake) {
            player.sendMessage(LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Fishing.Effect.4"), LocaleLoader.getString("Fishing.Effect.5")));
        }

        if (canFishermansDiet) {
            player.sendMessage(LocaleLoader.getString("Effects.Template", LocaleLoader.getString("Fishing.Effect.6"), LocaleLoader.getString("Fishing.Effect.7")));
        }
    }

    @Override
    protected boolean statsHeaderPermissions() {
        return canTreasureHunt || canMagicHunt || canShake;
    }

    @Override
    protected void statsDisplay() {
        if (canTreasureHunt) {
            player.sendMessage(LocaleLoader.getString("Fishing.Ability.Rank", lootTier));
        }

        if (canMagicHunt) {
            player.sendMessage(LocaleLoader.getString("Fishing.Enchant.Chance", magicChance) + chanceRaining + (isLucky ? LocaleLoader.getString("Perks.lucky.bonus", magicChanceLucky) : ""));
        }

        if (canShake) {
            int unlockLevel = AdvancedConfig.getInstance().getShakeUnlockLevel();

            if (skillValue < unlockLevel) {
                player.sendMessage(LocaleLoader.getString("Ability.Generic.Template.Lock", LocaleLoader.getString("Fishing.Ability.Locked.0", unlockLevel)));
            }
            else {
                player.sendMessage(LocaleLoader.getString("Fishing.Ability.Shake", shakeChance) + (isLucky ? LocaleLoader.getString("Perks.lucky.bonus", shakeChanceLucky) : ""));
            }
        }

        if (canFishermansDiet) {
            player.sendMessage(LocaleLoader.getString("Fishing.Ability.FD", fishermansDietRank));
        }
    }
}
