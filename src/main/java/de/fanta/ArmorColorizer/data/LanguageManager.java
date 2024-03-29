package de.fanta.ArmorColorizer.data;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.iani.cubesideutils.StringUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class LanguageManager {
    private final ArmorColorizer plugin;
    private final String[] languages;
    private final File languageFolder;
    private final FileConfiguration currentMessages;


    public LanguageManager(ArmorColorizer plugin, ArmorColorizerConfig config) {
        this.plugin = plugin;
        this.languageFolder = new File(plugin.getDataFolder().getAbsolutePath() + "/languages");
        this.languages = new String[]{"de_DE", "en_EN"};
        loadLanguageFiles();
        File currentLanguage = new File(languageFolder, config.getLanguage() + ".yml");
        this.currentMessages = YamlConfiguration.loadConfiguration(currentLanguage);
    }

    public void loadLanguageFiles() {
        for (String string : languages) {
            File languageConfig = new File(languageFolder, string + ".yml");

            if (!languageConfig.exists()) {
                plugin.getLogger().log(Level.INFO, "create " + languageConfig.getAbsolutePath());
                languageConfig.getParentFile().mkdirs();
                plugin.saveResource("languages/" + string + ".yml", false);
            } else {
                FileConfiguration langConfig = YamlConfiguration.loadConfiguration(languageConfig);
                Reader defConfigStream = new InputStreamReader(plugin.getResource("languages/" + string + ".yml"), StandardCharsets.UTF_8);
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                langConfig.setDefaults(defConfig);
                langConfig.options().copyDefaults(true);
                try {
                    langConfig.save(languageConfig);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getMessage(String path) {
        String message = currentMessages.getString(path);
        if (message == null) {
            return "Error loading the message " + path + "." + " Please report to an admin!";
        }
        return StringUtil.convertColors(message);
    }
}