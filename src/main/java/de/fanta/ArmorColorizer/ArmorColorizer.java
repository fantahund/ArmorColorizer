package de.fanta.ArmorColorizer;

import de.fanta.ArmorColorizer.commands.CommandRegistration;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.guiutils.WindowManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ArmorColorizer extends JavaPlugin {

    private static String PREFIX;
    private static ArmorColorizer plugin;

    public File messagesConfigFile;
    public FileConfiguration messagesConfig;
    private static String language;

    private Economy economy;

    public static ArmorColorizer getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        loadConfig();
        createMessagesConfig();

        PREFIX = ChatUtil.BLUE + "[" + ChatUtil.GREEN + "ArmorColorizer" + ChatUtil.BLUE + "]";

        RegisteredServiceProvider<Economy> economyRegistration = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyRegistration == null) {
            throw new IllegalStateException("Could not find any vault economy provider.");
        }
        economy = economyRegistration.getProvider();

        new CommandRegistration(this).registerCommands();
        Bukkit.getPluginManager().registerEvents(new WindowManager(), plugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPrefix() {
        return PREFIX;
    }

    public Economy getEconomy() {
        return economy;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    private void createMessagesConfig() {
        messagesConfigFile = new File(getDataFolder(), "language/" + language + ".yml");
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            saveResource("language/" + language + ".yml", false);
        }
        this.messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesConfigFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        language = getConfig().getString("language");
    }

    public void reloadPluginConfig() {
        reloadConfig();
        loadConfig();
        createMessagesConfig();
    }
}
