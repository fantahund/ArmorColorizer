package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.guis.RGBDyeGui;
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

public class ArmorColorizerRGBCommand extends SubCommand {

    private final ArmorColorizer plugin;

    public ArmorColorizerRGBCommand(ArmorColorizer plugin) {
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
            new RGBDyeGui(player, stack.clone()).open();
        }

        if (args.remaining() == 3) {
            int r = args.getNext(-1);
            int g = args.getNext(-1);
            int b = args.getNext(-1);
            if (!(r <= 255 && r >= 0)) {
                ChatUtil.sendErrorMessage(player, "Der Rot Wert muss zwischen 0-255 liegen!");
                return true;
            }
            if (!(g <= 255 && g >= 0)) {
                ChatUtil.sendErrorMessage(player, "Der Grün Wert muss zwischen 0-255 liegen!");
                return true;
            }
            if (!(b <= 255 && b >= 0)) {
                ChatUtil.sendErrorMessage(player, "Der Blau Wert muss zwischen 0-255 liegen!");
                return true;
            }
            Color color = Color.fromRGB(r, g, b);
            ArmordDyeingUtil.applyColorToItem(player, stack, color);
        }

        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.colorizer.rgb";
    }

}
