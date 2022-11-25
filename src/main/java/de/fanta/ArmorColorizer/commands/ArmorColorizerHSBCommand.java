package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.guis.HSBDyeGui;
import de.fanta.ArmorColorizer.utils.ArmordDyeingUtil;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.HSBColor;
import de.iani.cubesideutils.bukkit.commands.SubCommand;
import de.iani.cubesideutils.bukkit.items.ItemGroups;
import de.iani.cubesideutils.commands.ArgsParser;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorColorizerHSBCommand extends SubCommand {

    private final ArmorColorizer plugin;

    public ArmorColorizerHSBCommand(ArmorColorizer plugin) {
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

        if (!args.hasNext()) {
            new HSBDyeGui(player, stack.clone()).open();
        }

        if (args.remaining() == 3) {
            int hue = args.getNext(-1);
            int saturation = args.getNext(-1);
            int brightness = args.getNext(-1);
            if (!(hue <= 360 && hue >= 0)) {
                ChatUtil.sendErrorMessage(player, "Der HUE Wert muss zwischen 0-360 liegen!");
                return true;
            }
            if (!(saturation <= 100 && saturation >= 0)) {
                ChatUtil.sendErrorMessage(player, "Der Saturation Wert muss zwischen 0-100 liegen!");
                return true;
            }
            if (!(brightness <= 100 && brightness >= 0)) {
                ChatUtil.sendErrorMessage(player, "Der Brightness Wert muss zwischen 0-100 liegen!");
                return true;
            }

            HSBColor color = new HSBColor(hue, saturation, brightness);
            ArmordDyeingUtil.applyColorToItem(player, stack, Color.fromRGB(color.getRGB().getRed(), color.getRGB().getGreen(), color.getRGB().getBlue()));
        }
        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "armorcolorizer.colorizer.hsb";
    }


}
