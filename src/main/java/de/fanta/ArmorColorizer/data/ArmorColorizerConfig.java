package de.fanta.ArmorColorizer.data;

import de.fanta.ArmorColorizer.ArmorColorizer;
import org.bukkit.configuration.file.FileConfiguration;

public class ArmorColorizerConfig {
    private final ArmorColorizer plugin;
    private String language;
    private boolean useEconomy;
    private double economyPrice;

    public ArmorColorizerConfig(ArmorColorizer plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reloadConfig();
    }

    public void reloadConfig() {
        FileConfiguration config = plugin.getConfig();
        config.options().copyDefaults(true);
        plugin.saveConfig();

        language = config.getString("language");
        useEconomy = config.getBoolean("economy.enable");
        economyPrice = config.getDouble("economy.price");
    }

    public String getLanguage() {
        return language;
    }

    public boolean getUseEconomy() {
        return useEconomy;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }
}
