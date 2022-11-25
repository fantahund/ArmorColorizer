package de.fanta.ArmorColorizer.data;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.iani.cubesideutils.sql.MySQLConnection;
import de.iani.cubesideutils.sql.SQLConfig;
import de.iani.cubesideutils.sql.SQLConnection;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {

    private final SQLConnection connection;
    private final SQLConfig config;
    private final ArmorColorizer plugin;
    private final String insertColorQuery;
    private final String getColorsQuery;

    public Database(SQLConfig config, ArmorColorizer plugin) {
        this.config = config;
        this.plugin = plugin;

        try {
            this.connection = new MySQLConnection(config.getHost(), config.getDatabase(), config.getUser(), config.getPassword());

            createTablesIfNotExist();
        } catch (SQLException ex) {
            throw new RuntimeException("Could not initialize database", ex);
        }

        insertColorQuery = "INSERT INTO " + config.getTablePrefix() + "_colors" + " (uuid, color, timestamp) VALUE (?, ?, ?)";
        getColorsQuery = "SELECT * FROM " + config.getTablePrefix() + "_colors" + " WHERE uuid = ?" + " ORDER BY timestamp DESC";
    }


    private void createTablesIfNotExist() throws SQLException {
        this.connection.runCommands((connection, sqlConnection) -> {
            Statement smt = connection.createStatement();
            smt.executeUpdate("CREATE TABLE IF NOT EXISTS " + config.getTablePrefix() + "_colors" + " (" +
                    "`uuid` char(36)," +
                    "`color` INT," +
                    "`timestamp` BIGINT," +
                    "PRIMARY KEY (`uuid`, `color`)" +
                    ")");
            smt.close();
            return null;
        });
    }

    public void insertColor(UUID uuid, int color) throws SQLException {
        this.connection.runCommands((connection, sqlConnection) -> {
            PreparedStatement smt = sqlConnection.getOrCreateStatement(insertColorQuery);

            smt.setString(1, uuid.toString());
            smt.setInt(2, color);
            smt.setLong(3, System.currentTimeMillis());

            smt.executeUpdate();
            return null;
        });
    }

    public List<Color> getColors(Player player) throws SQLException {
        List<Color> colors = new ArrayList<>();
        this.connection.runCommands((connection, sqlConnection) -> {
            PreparedStatement statement = sqlConnection.getOrCreateStatement(getColorsQuery);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int color = rs.getInt(2);
                colors.add(Color.fromRGB(color));
            }
            return null;
        });
        return colors;
    }
}
