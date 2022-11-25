package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.iani.cubesideutils.bukkit.commands.CommandRouter;

public record CommandRegistration(ArmorColorizer plugin) {

    public void registerCommands() {

        CommandRouter testRouter = new CommandRouter(plugin.getCommand("armorcolorizer"));
        testRouter.addCommandMapping(new ArmorColorizerHexCommand(), "hex");
        testRouter.addCommandMapping(new ArmorColorizerRGBCommand(plugin), "rgb");
        testRouter.addCommandMapping(new ArmorColorizerHSBCommand(plugin), "hsb");
        testRouter.addCommandMapping(new ArmorColorizerReloadCommand(plugin), "reload");
    }
}
