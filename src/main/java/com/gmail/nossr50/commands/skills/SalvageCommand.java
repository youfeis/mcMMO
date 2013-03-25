package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.salvage.Salvage;
import com.gmail.nossr50.skills.salvage.SalvageManager;
import com.gmail.nossr50.util.Permissions;

public class SalvageCommand extends SkillCommand {
    private boolean canAdvancedSalvage;
    private boolean canArcaneSalvage;

    public SalvageCommand() {
        super(SkillType.SALVAGE);
    }

    @Override
    protected void dataCalculations() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void permissionsCheck() {
        canAdvancedSalvage = Permissions.advancedSalvage(player);
        canArcaneSalvage = Permissions.arcaneSalvage(player);
    }

    @Override
    protected boolean effectsHeaderPermissions() {
        return canAdvancedSalvage || canArcaneSalvage;
    }

    @Override
    protected void effectsDisplay() {
        if (canAdvancedSalvage) {
            player.sendMessage(LocaleLoader.getString("Effects.Template", "Advanced Salvage", "Salvage damaged items")); // TODO: Localize
        }

        if (canArcaneSalvage) {
            player.sendMessage(LocaleLoader.getString("Effects.Template", "Arcane Salvaging", "Extract enchantments from items")); // TODO: Localize
        }
    }

    @Override
    protected boolean statsHeaderPermissions() {
        return canAdvancedSalvage || canArcaneSalvage;
    }

    @Override
    protected void statsDisplay() {
        if (canAdvancedSalvage) {
            if (skillValue < Salvage.advancedSalvageUnlockLevel) {
                player.sendMessage(LocaleLoader.getString("Ability.Generic.Template.Lock", "LOCKED UNTIL {0}+ SKILL (ADVANCED SALVAGE)", Salvage.advancedSalvageUnlockLevel));
            }
            else {
                player.sendMessage(LocaleLoader.getString("Ability.Generic.Template", "Advanced Salvage", "Can salvage damaged items."));
            }
        }

        if (canArcaneSalvage) {
            SalvageManager salvageManager = mcMMOPlayer.getSalvageManager();

            player.sendMessage(LocaleLoader.getString("Ability.Generic.Template", "Arcane Salvaging: ", "Rank {0}/5", salvageManager.getArcaneSalvageRank())); // TODO: Localize

            if (Salvage.arcaneSalvageEnchantLoss) {
                player.sendMessage(LocaleLoader.getString("Ability.Generic.Template", "[[GRAY]]AS Full-Enchant Chance", salvageManager.getExtractFullEnchantChance()));
            }

            if (Salvage.arcaneSalvageDowngrades) {
                player.sendMessage(LocaleLoader.getString("Ability.Generic.Template", "[[GRAY]]AS Partial-Enchant Chance", salvageManager.getExtractPartialEnchantChance()));
            }
        }
    }
}
