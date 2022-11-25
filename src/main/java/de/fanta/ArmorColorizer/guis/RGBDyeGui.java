package de.fanta.ArmorColorizer.guis;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.utils.ArmordDyeingUtil;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.ColorUtils;
import de.fanta.ArmorColorizer.utils.HSBColor;
import de.fanta.ArmorColorizer.utils.guiutils.AbstractWindow;
import de.fanta.ArmorColorizer.utils.guiutils.GUIUtils;
import de.iani.cubesideutils.bukkit.items.CustomHeads;
import de.iani.cubesideutils.bukkit.items.ItemGroups;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.UUID;

public class RGBDyeGui extends AbstractWindow {
    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    public static final int INVENTORY_SIZE = 27;

    private static final int RED_INDEX = 10;
    private static final int ADD_RED_INDEX = 1;
    private static final int REMOVE_RED_INDEX = 19;

    private static final int GREEN_INDEX = 11;
    private static final int ADD_GREEN_INDEX = 2;
    private static final int REMOVE_GREEN_INDEX = 20;

    private static final int BLUE_INDEX = 12;
    private static final int ADD_BLUE_INDEX = 3;
    private static final int REMOVE_BLUE_INDEX = 21;

    private static final int ARMOR_INDEX = 14;

    private static final int CONFIRM_INDEX = 16;
    private static final int RANDOM_INDEX = 5;

    private static final HashMap<UUID, ItemStack> playerItemList = new HashMap<>();
    private static final HashMap<UUID, Color> playerColorList = new HashMap<>();

    public RGBDyeGui(Player player, ItemStack stack) {
        super(player, Bukkit.createInventory(player, INVENTORY_SIZE, "ArmorColorizer >> Dye Armor"));
        playerItemList.put(player.getUniqueId(), stack);
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
            case ADD_RED_INDEX -> {
                Color color = ColorUtils.calculateRGBColor(playerColorList.get(player.getUniqueId()), event.isShiftClick() ? 10 : 1, 0, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case REMOVE_RED_INDEX -> {
                Color color = ColorUtils.calculateRGBColor(playerColorList.get(player.getUniqueId()), event.isShiftClick() ? -10 : -1, 0, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case ADD_GREEN_INDEX -> {
                Color color = ColorUtils.calculateRGBColor(playerColorList.get(player.getUniqueId()), 0, event.isShiftClick() ? 10 : 1, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case REMOVE_GREEN_INDEX -> {
                Color color = ColorUtils.calculateRGBColor(playerColorList.get(player.getUniqueId()), 0, event.isShiftClick() ? -10 : -1, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case ADD_BLUE_INDEX -> {
                Color color = ColorUtils.calculateRGBColor(playerColorList.get(player.getUniqueId()), 0, 0, event.isShiftClick() ? 10 : 1);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case REMOVE_BLUE_INDEX -> {
                Color color = ColorUtils.calculateRGBColor(playerColorList.get(player.getUniqueId()), 0, 0, event.isShiftClick() ? -10 : -1);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case RANDOM_INDEX -> {
                playerColorList.put(player.getUniqueId(), ColorUtils.randomRGBColor());
                rebuildInventory();
            }
            case CONFIRM_INDEX -> {
                ItemStack stack = player.getInventory().getItemInMainHand();
                Color color = playerColorList.get(player.getUniqueId());
                ArmordDyeingUtil.applyColorToItem(player, stack, color);
                player.closeInventory();
            }
            default -> {
            }
        }
    }

    @Override
    protected void rebuildInventory() {
        ItemStack armorItem = playerItemList.get(getPlayer().getUniqueId());
        LeatherArmorMeta meta = (LeatherArmorMeta) armorItem.getItemMeta();
        Color color = playerColorList.getOrDefault(getPlayer().getUniqueId(), meta.getColor());
        meta.setColor(color);
        armorItem.setItemMeta(meta);
        playerColorList.put(getPlayer().getUniqueId(), color);

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            ItemStack item;
            switch (i) {
                case RED_INDEX ->
                        item = GUIUtils.createGuiItem(Material.RED_DYE, ChatUtil.RED + "Red: " + color.getRed());
                case ADD_RED_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_UP.getHead(ChatUtil.RED + "Add Red (" + color.getRed() + ")", ChatUtil.YELLOW + "Click: +1", ChatUtil.YELLOW + "Shift + Click: +10");
                case REMOVE_RED_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_DOWN.getHead(ChatUtil.RED + "Remove Red (" + color.getRed() + ")", ChatUtil.YELLOW + "Click: -1", ChatUtil.YELLOW + "Shift + Click: -10");

                case GREEN_INDEX ->
                        item = GUIUtils.createGuiItem(Material.GREEN_DYE, ChatUtil.GREEN + "Green: " + color.getGreen());
                case ADD_GREEN_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_UP.getHead(ChatUtil.GREEN + "Add Green (" + color.getGreen() + ")", ChatUtil.YELLOW + "Click: +1", ChatUtil.YELLOW + "Shift + Click: +10");
                case REMOVE_GREEN_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_DOWN.getHead(ChatUtil.GREEN + "Remove Green (" + color.getGreen() + ")", ChatUtil.YELLOW + "Click: -1", ChatUtil.YELLOW + "Shift + Click: -10");

                case BLUE_INDEX ->
                        item = GUIUtils.createGuiItem(Material.BLUE_DYE, ChatUtil.BLUE + "Blue: " + color.getBlue());
                case ADD_BLUE_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_UP.getHead(ChatUtil.BLUE + "Add Blue (" + color.getBlue() + ")", ChatUtil.YELLOW + "Click: +1", ChatUtil.YELLOW + "Shift + Click: +10");
                case REMOVE_BLUE_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_DOWN.getHead(ChatUtil.BLUE + "Remove Blue (" + color.getBlue() + ")", ChatUtil.YELLOW + "Click: -1", ChatUtil.YELLOW + "Shift + Click: -10");

                case ARMOR_INDEX -> item = armorItem;

                case CONFIRM_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_RIGHT.getHead(ChatUtil.GREEN + "Change Color", plugin.getEconomy().getBalance(getPlayer()) >= 100 ? ChatUtil.GREEN + "Price: 100 Cubes" : ChatUtil.RED + "You do not have enough money (100 Cubes)");

                case RANDOM_INDEX -> item = CustomHeads.RAINBOW_R.getHead(ChatUtil.GREEN + "Random Color");
                default -> item = GUIUtils.EMPTY_ICON;
            }
            this.getInventory().setItem(i, item);
        }
    }

    @Override
    public void closed() {
        playerColorList.remove(getPlayer().getUniqueId());
    }
}
