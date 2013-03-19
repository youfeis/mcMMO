package com.gmail.nossr50.runnables;

import org.bukkit.entity.LivingEntity;

public class HealthDisplayUpdaterTask implements Runnable {
    private LivingEntity target;

    public HealthDisplayUpdaterTask(LivingEntity target) {
        this.target = target;
    }

    @Override
    public void run() {
        target.setCustomNameVisible(false);
    }
}
