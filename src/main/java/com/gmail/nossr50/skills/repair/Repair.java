package com.gmail.nossr50.skills.repair;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.nossr50.config.AdvancedConfig;
import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.salvage.Salvage;
import com.gmail.nossr50.util.ItemUtils;

public class Repair {
    public static int    repairMasteryMaxBonusLevel = AdvancedConfig.getInstance().getRepairMasteryMaxLevel();
    public static double repairMasteryMaxBonus      = AdvancedConfig.getInstance().getRepairMasteryMaxBonus();

    public static int    superRepairMaxBonusLevel = AdvancedConfig.getInstance().getSuperRepairMaxLevel();
    public static double superRepairMaxChance     = AdvancedConfig.getInstance().getSuperRepairChanceMax();

    public static int salvageUnlockLevel = AdvancedConfig.getInstance().getSalvageUnlockLevel();

    public static int repairAnvilId = Config.getInstance().getRepairAnvilId();
    public static boolean anvilMessagesEnabled = Config.getInstance().getRepairAnvilMessagesEnabled();

    /**
     * Checks if the item is salvageable.
     *
     * @param item Item to check
     * @return true if the item is salvageable, false otherwise
     */
    public static boolean isSalvageable(ItemStack item) {
        if (Config.getInstance().getSalvageTools() && (ItemUtils.isMinecraftTool(item) || ItemUtils.isStringTool(item) || item.getType() == Material.BUCKET)) {
            return true;
        }

        if (Config.getInstance().getSalvageArmor() && ItemUtils.isMinecraftArmor(item)) {
            return true;
        }

        return false;
    }

    /**
     * Search the inventory for an item and return the index.
     *
     * @param inventory PlayerInventory to scan
     * @param itemId Item id to look for
     * @return index location where the item was found, or -1 if not found
     */
    protected static int findInInventory(PlayerInventory inventory, int itemId) {
        int location = inventory.first(itemId);

        // VALIDATE
        if (inventory.getItem(location).getTypeId() == itemId) {
            return location;
        }

        return -1;
    }

    /**
     * Search the inventory for an item and return the index.
     *
     * @param inventory PlayerInventory to scan
     * @param itemId Item id to look for
     * @param metadata Metadata to look for
     * @return index location where the item was found, or -1 if not found
     */
    protected static int findInInventory(PlayerInventory inventory, int itemId, byte metadata) {
        int location = -1;

        ItemStack[] contents = inventory.getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];

            if (item == null) {
                continue;
            }

            if (item.getTypeId() == itemId && item.getData().getData() == metadata) {
                return i;
            }
        }

        return location;
    }

    /**
     * Decrease the amount of items in this slot by one
     *
     * @param inventory PlayerInventory to work in
     * @param index Item index to decrement
     */
    protected static void removeOneFrom(PlayerInventory inventory, int index) {
        ItemStack item = inventory.getItem(index).clone();
        item.setAmount(1);

        inventory.removeItem(item);
    }

    protected static String[] getSpoutAnvilMessages(int blockId) {
        if (blockId == repairAnvilId) {
            return new String[] {LocaleLoader.getString("Repair.AnvilPlaced.Spout1"), LocaleLoader.getString("Repair.AnvilPlaced.Spout2")};
        }

        if (blockId == Salvage.salvageAnvilId) {
            return new String[] {"[mcMMO] Anvil Placed", "Right click to salvage!"};
        }

        return new String[] {"", ""};
    }

    protected static String getAnvilMessage(int blockId) {
        if (blockId == repairAnvilId) {
            return LocaleLoader.getString("Repair.Listener.Anvil");
        }

        if (blockId == Salvage.salvageAnvilId) {
            return LocaleLoader.getString("Repair.Listener.Anvil2");
        }

        return "";
    }
}
