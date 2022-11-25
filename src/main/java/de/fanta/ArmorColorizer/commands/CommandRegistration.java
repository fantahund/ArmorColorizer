package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.iani.cubesideutils.bukkit.commands.CommandRouter;

public record CommandRegistration(ArmorColorizer plugin) {

    public void registerCommands() {

        CommandRouter armorColorizeRouter = new CommandRouter(plugin.getCommand("armorcolorizer"));
        armorColorizeRouter.addCommandMapping(new ArmorColorizerHexCommand(), "hex");
        armorColorizeRouter.addCommandMapping(new ArmorColorizerRGBCommand(plugin), "rgb");
        armorColorizeRouter.addCommandMapping(new ArmorColorizerHSBCommand(plugin), "hsb");
        armorColorizeRouter.addCommandMapping(new ArmorColorizerSaveColorCommand(plugin), "savecolor");
        armorColorizeRouter.addCommandMapping(new ArmorColorizerColorListCommand(plugin), "listcolors");
    }
}
