package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ArmorColorizerReloadCommand extends SubCommand {

    private final ArmorColorizer plugin;

    public ArmorColorizerReloadCommand(ArmorColorizer plugin) {
        this.plugin = plugin;

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String s1, ArgsParser args) {
        plugin.reloadPluginConfig();

        ChatUtil.sendNormalMessage(sender, plugin.getMessagesConfig().getString("reloadMessage"));
        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.reload";
    }
}
