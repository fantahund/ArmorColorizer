package de.fanta.ArmorColorizer;

import de.fanta.ArmorColorizer.commands.CommandRegistration;
import de.fanta.ArmorColorizer.data.ArmorColorizerConfig;
import de.fanta.ArmorColorizer.data.Database;
import de.fanta.ArmorColorizer.data.LanguageManager;
import de.fanta.ArmorColorizer.data.Messages;
import de.fanta.ArmorColorizer.utils.EconomyBridge;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
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
    private static HashMap<Material, TrimPattern> trimPatternMap;
    private static HashMap<Material, TrimMaterial> trimColorMap;

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

        if (getArmorColorizerConfig().getUseEconomy()) {
            try {
                RegisteredServiceProvider<Economy> economyRegistration = getServer().getServicesManager().getRegistration(Economy.class);
                if (economyRegistration != null) {
                    EconomyBridge.setEconomyActiv(true);
                    economy = economyRegistration.getProvider();
                }
            } catch (NoClassDefFoundError ex) {
                getLogger().log(Level.WARNING, "Could not find any vault economy provider.");
            }
        }

        new CommandRegistration(this).registerCommands();

        PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new ArmorColorizerListener(), this);

        playerColors = new HashMap<>();
        trimPatternMap = createTrimPatternMap();
        trimColorMap = createTrimColorMap();

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

    public HashMap<Material, TrimPattern> getTrimPatternMap() {
        return trimPatternMap;
    }

    public HashMap<Material, TrimMaterial> getTrimColorMap() {
        return trimColorMap;
    }

    private HashMap<Material, TrimPattern> createTrimPatternMap() {
        HashMap<Material, TrimPattern> trims = new HashMap<>();
        trims.put(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SENTRY);
        trims.put(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.DUNE);
        trims.put(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.COAST);
        trims.put(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WILD);
        trims.put(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WARD);
        trims.put(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.EYE);
        trims.put(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.VEX);
        trims.put(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.TIDE);
        trims.put(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SNOUT);
        trims.put(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.RIB);
        trims.put(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SPIRE);
        trims.put(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WAYFINDER);
        trims.put(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SHAPER);
        trims.put(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SILENCE);
        trims.put(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.RAISER);
        trims.put(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.HOST);
        return trims;
    }

    private HashMap<Material, TrimMaterial> createTrimColorMap() {
        HashMap<Material, TrimMaterial> trimMaterial = new HashMap<>();
        trimMaterial.put(Material.EMERALD, TrimMaterial.EMERALD);
        trimMaterial.put(Material.REDSTONE, TrimMaterial.REDSTONE);
        trimMaterial.put(Material.LAPIS_LAZULI, TrimMaterial.LAPIS);
        trimMaterial.put(Material.QUARTZ, TrimMaterial.QUARTZ);
        trimMaterial.put(Material.NETHERITE_INGOT, TrimMaterial.NETHERITE);
        trimMaterial.put(Material.DIAMOND, TrimMaterial.DIAMOND);
        trimMaterial.put(Material.GOLD_INGOT, TrimMaterial.GOLD);
        trimMaterial.put(Material.IRON_INGOT, TrimMaterial.IRON);
        trimMaterial.put(Material.COPPER_INGOT, TrimMaterial.COPPER);
        trimMaterial.put(Material.AMETHYST_SHARD, TrimMaterial.AMETHYST);
        return trimMaterial;
    }
}
