package de.fanta.ArmorColorizer.commands.armorcolorizer;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.guis.DyeLeatherArmorGui;
import de.fanta.ArmorColorizer.utils.ArmordDyeingUtil;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.bukkit.items.ItemGroups;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class armorColorizerDyeCommand extends SubCommand {

    private final ArmorColorizer plugin;
    private final int price = 100;

    public armorColorizerDyeCommand(ArmorColorizer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String s1, ArgsParser args) {
        if (!(sender instanceof Player player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return true;
        }
        ItemStack stack = player.getInventory().getItemInMainHand();

        if (stack.getType() == Material.AIR) {
            ChatUtil.sendErrorMessage(player, plugin.getMessagesConfig().getString("noiteminhand"));
            return true;
        }

        if (!ItemGroups.isDyeableItem(stack.getType())) {
            ChatUtil.sendErrorMessage(player, plugin.getMessagesConfig().getString("notdyeableitem"));
            return true;
        }

        if (!args.hasNext()) {
            new DyeLeatherArmorGui(player, stack.clone()).open();
            return true;
        }
        String hexColor = args.getNext();

        Color color = ArmordDyeingUtil.hex2Color(hexColor);
        if (color == null) {
            ChatUtil.sendErrorMessage(player, plugin.getMessagesConfig().getString("wrongcolor"));
            return true;
        }

        if (ArmordDyeingUtil.itemHasSameColor(stack, hexColor)) {
            ChatUtil.sendErrorMessage(player, plugin.getMessagesConfig().getString("itemHasSameColor"));
            return true;
        }

        if (plugin.getEconomy().withdrawPlayer(player, price).transactionSuccess()) {
            ArmordDyeingUtil.dyeingLeatherItem(stack, hexColor);
            ChatUtil.sendNormalMessage(player, plugin.getMessagesConfig().getString("itemsuccessfullycolored"));
            ChatUtil.sendNormalMessage(player, String.format(plugin.getMessagesConfig().getString("moneywithdrawn"), price + " " + plugin.getEconomy().currencyNamePlural()));
        } else {
            ChatUtil.sendErrorMessage(player, plugin.getMessagesConfig().getString("notenoughmoney"));
        }
        return true;
    }


}
