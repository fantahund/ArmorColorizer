package de.fanta.ArmorColorizer;

import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ArmorColorizerListener implements Listener {

    private final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        List<Color> colors;
        try {
            colors = plugin.getDatabase().getColors(e.getPlayer());
        } catch (SQLException ex) {
            colors = new ArrayList<>();
            plugin.getLogger().log(Level.SEVERE, "Secrets for player " + e.getPlayer().getName() + " could not be loaded.", ex);
        }

        plugin.addPlayerColors(e.getPlayer(), colors);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (plugin.getPlayerColors(e.getPlayer()) != null) {
            plugin.removePlayerColors(e.getPlayer());
        }
        plugin.getNoCostPlayerList().remove(e.getPlayer().getUniqueId());
    }

}
