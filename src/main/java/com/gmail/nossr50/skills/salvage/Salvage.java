package com.gmail.nossr50.skills.salvage;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.config.Config;
import com.gmail.nossr50.util.ItemUtils;

public class Salvage {
    public static int salvageAnvilId = Config.getInstance().getSalvageAnvilId();

    public static int    salvageMaxBonusLevel = 1000;
    public static double salvageMaxBonus      = 100.0D;

    public static int advancedSalvageUnlockLevel = 350;

    public static double extractFullEnchantChance1 = 2.5D;
    public static double extractFullEnchantChance2 = 10.0D;
    public static double extractFullEnchantChance3 = 17.5D;
    public static double extractFullEnchantChance4 = 25.0D;
    public static double extractFullEnchantChance5 = 32.5D;

    public static double extractPartialEnchantChance1 = 7.5D;
    public static double extractPartialEnchantChance2 = 10.0D;
    public static double extractPartialEnchantChance3 = 12.5D;
    public static double extractPartialEnchantChance4 = 15.0D;
    public static double extractPartialEnchantChance5 = 17.5D;

    public static int arcaneSalvageRank1 = 200;
    public static int arcaneSalvageRank2 = 400;
    public static int arcaneSalvageRank3 = 600;
    public static int arcaneSalvageRank4 = 800;
    public static int arcaneSalvageRank5 = 1000;

    public static boolean arcaneSalvageDowngrades = true;
    public static boolean arcaneSalvageEnchantLoss = true;

    protected static Material getSalvagedItem(ItemStack inHand) {
        if (ItemUtils.isDiamondTool(inHand) || ItemUtils.isDiamondArmor(inHand)) {
            return Material.DIAMOND;
        }
        else if (ItemUtils.isGoldTool(inHand) || ItemUtils.isGoldArmor(inHand)) {
            return Material.GOLD_INGOT;
        }
        else if (ItemUtils.isIronTool(inHand) || ItemUtils.isIronArmor(inHand)) {
            return Material.IRON_INGOT;
        }
        else if (ItemUtils.isStoneTool(inHand)) {
            return Material.COBBLESTONE;
        }
        else if (ItemUtils.isWoodTool(inHand)) {
            return Material.WOOD;
        }
        else if (ItemUtils.isLeatherArmor(inHand)) {
            return Material.LEATHER;
        }
        else if (ItemUtils.isStringTool(inHand)) {
            return Material.STRING;
        }
        else {
            return null;
        }
    }

    protected static int getSalvagedAmount(ItemStack inHand) {
        if (ItemUtils.isPickaxe(inHand) || ItemUtils.isAxe(inHand) || inHand.getType() == Material.BOW || inHand.getType() == Material.BUCKET) {
            return 3;
        }
        else if (ItemUtils.isShovel(inHand) || inHand.getType() == Material.FLINT_AND_STEEL) {
            return 1;
        }
        else if (ItemUtils.isSword(inHand) || ItemUtils.isHoe(inHand) || inHand.getType() == Material.CARROT_STICK || inHand.getType() == Material.FISHING_ROD || inHand.getType() == Material.SHEARS) {
            return 2;
        }
        else if (ItemUtils.isHelmet(inHand)) {
            return 5;
        }
        else if (ItemUtils.isChestplate(inHand)) {
            return 8;
        }
        else if (ItemUtils.isLeggings(inHand)) {
            return 7;
        }
        else if (ItemUtils.isBoots(inHand)) {
            return 4;
        }
        else {
            return 0;
        }
    }

    protected static int calculateSalvageableAmount(short currentDurability, short maxDurability, int baseAmount) {
        double percentDamaged = (double) (maxDurability - currentDurability) / maxDurability;

        return (int) Math.floor(baseAmount * percentDamaged);
    }
}
