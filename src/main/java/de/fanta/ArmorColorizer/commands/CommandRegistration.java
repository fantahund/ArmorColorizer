package de.fanta.ArmorColorizer.commands;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.commands.armorcolorizer.armorColorizerDyeCommand;
import de.fanta.ArmorColorizer.commands.armorcolorizer.armorColorizerReloadCommand;
import de.iani.cubesideutils.bukkit.commands.CommandRouter;

public record CommandRegistration(ArmorColorizer plugin) {

    public void registerCommands() {

        CommandRouter testRouter = new CommandRouter(plugin.getCommand("armorcolorizer"));
        testRouter.addCommandMapping(new armorColorizerDyeCommand(plugin), "dye");
        testRouter.addCommandMapping(new armorColorizerReloadCommand(plugin), "reload");
    }
}
