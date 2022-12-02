package de.fanta.ArmorColorizer.utils;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.iani.cubesideutils.bukkit.items.ItemGroups;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmordDyeingUtil {

    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    public static ItemStack dyeingLeatherItem(ItemStack stack, Color color) {
        if (color == null) {
            return null;
        }

        LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        itemMeta.setColor(color);
        stack.setItemMeta(itemMeta);
        return stack;
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

        if (player.getGameMode() == GameMode.CREATIVE || plugin.getNoCostPlayerList().contains(player.getUniqueId())) {
            ArmordDyeingUtil.dyeingLeatherItem(stack, org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
            ChatUtil.sendNormalMessage(player, plugin.getMessages().getItemsuccessfullycolored());
            player.closeInventory();
            return;
        }


        if (EconomyBridge.isEconomyActiv()) {
            double price = plugin.getArmorColorizerConfig().getEconomyPrice();
            if (EconomyBridge.hasEnoughMoney(player, price)) {
                ItemStack eventItem = ArmordDyeingUtil.dyeingLeatherItem(stack.clone(), org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
                if (EconomyBridge.withdrawMoney(player, price, eventItem)) {
                    ArmordDyeingUtil.dyeingLeatherItem(stack, org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
                    ChatUtil.sendNormalMessage(player, plugin.getMessages().getItemsuccessfullycolored());
                }
            } else {
                ChatUtil.sendErrorMessage(player, plugin.getMessages().getNotenoughmoney());
            }
        } else {
            ArmordDyeingUtil.dyeingLeatherItem(stack, org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
            ChatUtil.sendNormalMessage(player, plugin.getMessages().getItemsuccessfullycolored());
        }
    }
}
