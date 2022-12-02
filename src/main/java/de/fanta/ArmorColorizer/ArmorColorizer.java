package de.fanta.ArmorColorizer;

import de.fanta.ArmorColorizer.commands.CommandRegistration;
import de.fanta.ArmorColorizer.data.ArmorColorizerConfig;
import de.fanta.ArmorColorizer.data.Database;
import de.fanta.ArmorColorizer.data.LanguageManager;
import de.fanta.ArmorColorizer.data.Messages;
import de.fanta.ArmorColorizer.utils.EconomyBridge;
import de.fanta.ArmorColorizer.utils.guiutils.WindowManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public final class ArmorColorizer extends JavaPlugin {

    private static String PREFIX;
    private static ArmorColorizer plugin;
    private ArmorColorizerConfig armorColorizerConfig;
    private LanguageManager languageManager;
    private Messages messages;
    private Database database;
    private Economy economy;
    private HashMap<UUID, List<Color>> playerColors;
    private boolean isPaperServer;
    private final List<UUID> noCostPlayerList = new ArrayList<>();

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

        this.database = new Database(armorColorizerConfig.getSQLConfig(), this);

        RegisteredServiceProvider<Economy> economyRegistration = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyRegistration != null) {
            EconomyBridge.setEconomyActiv(true);
            economy = economyRegistration.getProvider();
        }

        new CommandRegistration(this).registerCommands();
        Bukkit.getPluginManager().registerEvents(new WindowManager(), plugin);

        PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new ArmorColorizerListener(), this);

        playerColors = new HashMap<>();

        try {
            SkullMeta.class.getDeclaredMethod("getPlayerProfile");
            isPaperServer = true;
        } catch (Exception e) {
            getLogger().log(Level.INFO, "Server version spigot. We recommend to use Paper.");
            isPaperServer = false;
        }
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

    public Database getDatabase() {
        return database;
    }

    public void addPlayerColor(Player player, Color color) {
        List<Color> colorList = playerColors.getOrDefault(player.getUniqueId(), new ArrayList<>());
        colorList.add(color);
        playerColors.put(player.getUniqueId(), colorList);
    }

    public void removePlayerColor(Player player, Color color) {
        List<Color> colorList = playerColors.getOrDefault(player.getUniqueId(), new ArrayList<>());
        colorList.remove(color);
        playerColors.put(player.getUniqueId(), colorList);
    }

    public List<Color> getPlayerColors(Player player) {
        return playerColors.get(player.getUniqueId());
    }

    public void addPlayerColors(Player player, List<Color> colors) {
        playerColors.put(player.getUniqueId(), colors);
    }

    public void removePlayerColors(Player player) {
        playerColors.remove(player.getUniqueId());
    }

    public boolean isPaperServer() {
        return isPaperServer;
    }

    public List<UUID> getNoCostPlayerList() {
        return noCostPlayerList;
    }
}
