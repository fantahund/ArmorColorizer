package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.utils.ArmordDyeingUtil;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.ColorUtils;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorColorizerHexCommand extends SubCommand {

    public ArmorColorizerHexCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String s1, ArgsParser args) {
        if (!(sender instanceof Player player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return true;
        }

        if (!args.hasNext()) {
            ChatUtil.sendErrorMessage(player, "/armorcolorizer hex <Color (#ffffff)>");
            return true;
        }

        ItemStack stack = player.getInventory().getItemInMainHand();
        ArmordDyeingUtil.applyColorToItem(player, stack, ColorUtils.hex2Color(args.getNext()));
        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.colorizer.hex";
    }
}
