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

import java.util.Collection;
import java.util.Collections;

public class ArmorColorizerTrimRandomCommand extends SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String s1, ArgsParser args) {
        if (!(sender instanceof Player player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return true;
        }

        ItemStack stack = player.getInventory().getItemInMainHand();
        ArmordDyeingUtil.applyTrimAndColorToArmor(player, stack, ColorUtils.getRandomArmorTrim());

        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.colorizer.trim";
    }

    @Override
    public Collection<String> onTabComplete(CommandSender sender, Command command, String alias, ArgsParser args) {
        return Collections.emptySet();
    }
}
