package com.gmail.nossr50.skills.salvage;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.SkillManager;
import com.gmail.nossr50.skills.repair.Repair;
import com.gmail.nossr50.util.Misc;

public class SalvageManager extends SkillManager {
    public SalvageManager(McMMOPlayer mcMMOPlayer) {
        super(mcMMOPlayer, SkillType.SALVAGE);
    }

    public void handleSalvage(Location location, ItemStack item) {
        Player player = getPlayer();

        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        if (getSkillLevel() < Repair.salvageUnlockLevel) {
            player.sendMessage(LocaleLoader.getString("Repair.Skills.AdeptSalvage"));
            return;
        }

        if (item.getDurability() == 0) {
            player.setItemInHand(new ItemStack(Material.AIR));
            location.setY(location.getY() + 1);

            Misc.dropItems(location, new ItemStack(Salvage.getSalvagedItem(item)), Salvage.getSalvagedAmount(item) * item.getAmount());

            player.playSound(player.getLocation(), Sound.ANVIL_USE, Misc.ANVIL_USE_VOLUME, Misc.ANVIL_USE_PITCH);
            player.sendMessage(LocaleLoader.getString("Repair.Skills.SalvageSuccess"));
        }
        else {
            player.sendMessage(LocaleLoader.getString("Repair.Skills.NotFullDurability"));
        }
    }
}
