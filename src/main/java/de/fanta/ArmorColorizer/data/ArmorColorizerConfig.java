package de.fanta.ArmorColorizer.data;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.iani.cubesideutils.bukkit.sql.SQLConfigBukkit;
import de.iani.cubesideutils.sql.SQLConfig;
import org.bukkit.configuration.file.FileConfiguration;

public class ArmorColorizerConfig {
    private final ArmorColorizer plugin;

    private SQLConfig sqlConfig;
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

        sqlConfig = new SQLConfigBukkit(config.getConfigurationSection("database"));
        language = config.getString("language");
        useEconomy = config.getBoolean("economy.enable");
        economyPrice = config.getDouble("economy.price");
    }

    public SQLConfig getSQLConfig() {
        return sqlConfig;
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
