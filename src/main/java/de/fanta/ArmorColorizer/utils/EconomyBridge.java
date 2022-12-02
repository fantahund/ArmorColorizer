package de.fanta.ArmorColorizer.utils;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.events.ArmorColorizerTransactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EconomyBridge {
    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();
    private static boolean isEconomyActiv = false;

    public static boolean hasEnoughMoney(Player player, double price) {
         return plugin.getEconomy().has(player, price);
    }

    public static boolean withdrawMoney(Player player, double price, ItemStack stack) {
        if (plugin.getEconomy().withdrawPlayer(player, price).transactionSuccess()) {
            Bukkit.getPluginManager().callEvent(new ArmorColorizerTransactionEvent(player, stack, -price));
            ChatUtil.sendNormalMessage(player, plugin.getMessages().getMoneywithdrawn(price, getCurrencyName(price)));
            return true;
        } else {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNotenoughmoney());
            return false;
        }
    }

    public static String getCurrencyName(double price) {
        if (price > 1) {
            return plugin.getEconomy().currencyNamePlural();
        } else {
            return plugin.getEconomy().currencyNameSingular();
        }
    }

    public static boolean isEconomyActiv() {
        return isEconomyActiv;
    }

    public static void setEconomyActiv(boolean economyActiv) {
        isEconomyActiv = economyActiv;
    }
}
