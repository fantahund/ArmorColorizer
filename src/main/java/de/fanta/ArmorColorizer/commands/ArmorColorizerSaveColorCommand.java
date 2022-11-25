package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
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
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.sql.SQLException;
import java.util.logging.Level;

public class ArmorColorizerSaveColorCommand extends SubCommand {

    private final ArmorColorizer plugin;

    public ArmorColorizerSaveColorCommand(ArmorColorizer plugin) {
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

        if (!ItemGroups.isDyeableItem(stack.getType())) {
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getNotdyeableitem());
            return true;
        }

        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
        Color color = meta.getColor();
        try {
            if (!plugin.getPlayerColors(player).contains(color)) {
                plugin.getDatabase().insertColor(player.getUniqueId(), color.asRGB());
                plugin.addPlayerColor(player, color);
                ChatUtil.sendNormalMessage(player, plugin.getMessages().getInsertColorSuccessful());
            } else {
                ChatUtil.sendWarningMessage(player, plugin.getMessages().getInsertColorAlreadyAvailable());
            }

        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "color " + color.asRGB() + "could not be saved", e);
            ChatUtil.sendErrorMessage(player, plugin.getMessages().getInsertColorError());
        }
        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.colorizer.savecolor";
    }
}
