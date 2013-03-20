package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.datatypes.skills.SkillType;
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
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean statsHeaderPermissions() {
        return canAdvancedSalvage || canArcaneSalvage;
    }

    @Override
    protected void statsDisplay() {
        // TODO Auto-generated method stub

    }
}
