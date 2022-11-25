package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.guis.SavedColorsGui;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ArmorColorizerColorListCommand extends SubCommand {

    private final ArmorColorizer plugin;

    public ArmorColorizerColorListCommand(ArmorColorizer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String s1, ArgsParser args) {
        if (!(sender instanceof Player player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return true;
        }
        List<Color> colors = plugin.getPlayerColors(player);
        if (!colors.isEmpty()) {
            new SavedColorsGui(colors, player, plugin, false).open();
        } else {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNoSavedColors());
        }


        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.colorizer.listcolors";
    }
}
