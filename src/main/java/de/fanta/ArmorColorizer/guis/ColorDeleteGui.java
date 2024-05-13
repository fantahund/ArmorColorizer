package de.fanta.ArmorColorizer.guis;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.guiutils.GUIUtils;
import de.iani.cubesideutils.bukkit.inventory.AbstractWindow;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class ColorDeleteGui extends AbstractWindow {
    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    private static final int YES_INDEX = 0;
    private static final int ITEM_INDEX = 2;
    private static final int NO_INDEX = 4;

    private static final int INVENTORY_SIZE = 5;

    private final HashMap<UUID, Color> playerColorList = new HashMap<>();

    public ColorDeleteGui(Player player, Color color) {
        super(player, Bukkit.createInventory(player, InventoryType.HOPPER, plugin.getMessages().getGuiTitleDelete()));
        playerColorList.put(player.getUniqueId(), color);
    }


    @Override
    protected void rebuildInventory() {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            ItemStack item;
            ItemStack stack = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(playerColorList.get(getPlayer().getUniqueId()));
            meta.setDisplayName(plugin.getMessages().getGuiTitleDelete());
            stack.setItemMeta(meta);
            switch (i) {
                case YES_INDEX ->
                        item = GUIUtils.createGuiItem(Material.LIME_CONCRETE, ChatUtil.GREEN + plugin.getMessages().getYes(), false);
                case ITEM_INDEX -> item = stack;
                case NO_INDEX ->
                        item = GUIUtils.createGuiItem(Material.RED_CONCRETE, ChatUtil.RED + plugin.getMessages().getNo(), false);
                default -> item = GUIUtils.EMPTY_ICON;
            }
            this.getInventory().setItem(i, item);
        }
    }

    @Override
    public void onItemClicked(InventoryClickEvent event) {
        Player player = getPlayer();
        if (!mayAffectThisInventory(event)) {
            return;
        }

        event.setCancelled(true);
        if (!getInventory().equals(event.getClickedInventory())) {
            return;
        }

        int slot = event.getSlot();
        switch (slot) {
            case YES_INDEX -> {
                try {
                    plugin.getDatabase().deleteColor(player.getUniqueId(), playerColorList.get(player.getUniqueId()).asRGB());
                    plugin.removePlayerColor(player, playerColorList.get(player.getUniqueId()));
                    ChatUtil.sendNormalMessage(player, plugin.getMessages().getColorDeleteSuccessful());
                    player.closeInventory();
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "Color could not be deleted", e);
                    ChatUtil.sendNormalMessage(player, plugin.getMessages().getColorDeleteError());
                }
            }
            case NO_INDEX -> player.closeInventory();
            default -> {
            }
        }
        rebuildInventory();
    }

    @Override
    public void closed() {
        playerColorList.remove(getPlayer().getUniqueId());
    }
}
