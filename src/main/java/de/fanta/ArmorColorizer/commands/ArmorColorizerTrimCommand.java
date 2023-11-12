package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.guis.ArmorTrimSelectGui;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;

import java.util.Collection;
import java.util.Collections;

public class ArmorColorizerTrimCommand extends SubCommand {

    private final ArmorColorizer plugin;

    public ArmorColorizerTrimCommand(ArmorColorizer plugin) {
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
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNoiteminhand());
            return true;
        }

        if (!(stack.getItemMeta() instanceof ArmorMeta)) {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getItemNoTrim());
            return true;
        }

        new ArmorTrimSelectGui(player, stack).open();

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
