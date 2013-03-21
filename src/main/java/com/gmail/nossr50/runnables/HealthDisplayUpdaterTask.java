package com.gmail.nossr50.runnables;

import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthDisplayUpdaterTask extends BukkitRunnable {
    private LivingEntity target;
    private String oldName;

    public HealthDisplayUpdaterTask(LivingEntity target, String oldName) {
        this.target = target;
        this.oldName = oldName;
    }

    @Override
    public void run() {
        target.setCustomName(oldName);

        if (oldName != null && !oldName.isEmpty()) {
            target.setCustomNameVisible(false);
        }
    }
}
