package de.fanta.ArmorColorizer.utils;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.events.ArmorColorizerTransactionEvent;
import de.iani.cubesideutils.bukkit.items.ItemGroups;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmordDyeingUtil {

    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    public static void dyeingLeatherItem(ItemStack stack, Color color) {
        if (color == null) {
            return;
        }

        LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        itemMeta.setColor(color);
        stack.setItemMeta(itemMeta);
    }

    public static boolean itemHasSameColor(ItemStack stack, Color color) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
        Color itemColor = itemMeta.getColor();
        return itemColor.toString().equals(color.toString());
    }

    public static void applyColorToItem(Player player, ItemStack stack, Color color) {
        if (stack.getType() == Material.AIR) {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNoiteminhand());
            return;
        }

        if (!ItemGroups.isDyeableItem(stack.getType())) {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNotdyeableitem());
            return;
        }

        if (color == null) {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getWrongcolor());
            return;
        }

        if (ArmordDyeingUtil.itemHasSameColor(stack, color)) {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getItemHasSameColor());
            return;
        }

        if (player.getGameMode() == GameMode.CREATIVE) {
            ArmordDyeingUtil.dyeingLeatherItem(stack, org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
            ChatUtil.sendNormalMessage(player, plugin.getMessages().getItemsuccessfullycolored());
            player.closeInventory();
            return;
        }

        double price = plugin.getArmorColorizerConfig().getUseEconomy() ? plugin.getArmorColorizerConfig().getEconomyPrice() : 0;
        if (plugin.getEconomy().withdrawPlayer(player, price).transactionSuccess()) {
            ArmordDyeingUtil.dyeingLeatherItem(stack, org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
            Bukkit.getPluginManager().callEvent(new ArmorColorizerTransactionEvent(player, stack, -price));
            ChatUtil.sendNormalMessage(player, plugin.getMessages().getItemsuccessfullycolored());
            ChatUtil.sendNormalMessage(player, plugin.getMessages().getMoneywithdrawn(price, plugin.getEconomy().currencyNamePlural()));
        } else {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNotenoughmoney());
        }
    }
}
