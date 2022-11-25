package de.fanta.ArmorColorizer;

import de.fanta.ArmorColorizer.commands.CommandRegistration;
import de.fanta.ArmorColorizer.data.ArmorColorizerConfig;
import de.fanta.ArmorColorizer.data.LanguageManager;
import de.fanta.ArmorColorizer.data.Messages;
import de.fanta.ArmorColorizer.utils.guiutils.WindowManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorColorizer extends JavaPlugin {

    private static String PREFIX;
    private static ArmorColorizer plugin;
    private ArmorColorizerConfig armorColorizerConfig;
    private LanguageManager languageManager;
    private Messages messages;

    private Economy economy;

    public static ArmorColorizer getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.armorColorizerConfig = new ArmorColorizerConfig(this);
        this.languageManager = new LanguageManager(this, armorColorizerConfig);
        this.messages = new Messages(languageManager);
        PREFIX = messages.getPrefix();

        RegisteredServiceProvider<Economy> economyRegistration = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyRegistration == null) {
            throw new IllegalStateException("Could not find any vault economy provider.");
        }
        economy = economyRegistration.getProvider();

        new CommandRegistration(this).registerCommands();
        Bukkit.getPluginManager().registerEvents(new WindowManager(), plugin);
    }

    public String getPrefix() {
        return PREFIX;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ArmorColorizerConfig getArmorColorizerConfig() {
        return armorColorizerConfig;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public Messages getMessages() {
        return messages;
    }
}
