package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

public class ArmorColorizerFreeColorCommand extends SubCommand {

    private final ArmorColorizer plugin;
    private final boolean enable;

    public ArmorColorizerFreeColorCommand(ArmorColorizer plugin, boolean enable) {
        this.plugin = plugin;
        this.enable = enable;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String s1, ArgsParser args) {
        if (!(sender instanceof Player player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return true;
        }

        if (enable) {
            if (!plugin.getNoCostPlayerList().contains(player.getUniqueId())) {
                plugin.getNoCostPlayerList().add(player.getUniqueId());
                ChatUtil.sendNormalMessage(player, plugin.getMessages().getFreeModeEnable());
            } else {
                ChatUtil.sendErrorMessage(player, plugin.getMessages().getFreeModeAlreadyEnabled());
            }
        } else {
            if (plugin.getNoCostPlayerList().contains(player.getUniqueId())) {
                plugin.getNoCostPlayerList().remove(player.getUniqueId());
                ChatUtil.sendNormalMessage(player, plugin.getMessages().getFreeModeDisable());
            } else {
                ChatUtil.sendErrorMessage(player, plugin.getMessages().getFreeModeAlreadyDisabled());
            }
        }
        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.admin.freemode";
    }

    @Override
    public Collection<String> onTabComplete(CommandSender sender, Command command, String alias, ArgsParser args) {
        return Collections.emptySet();
    }
}
