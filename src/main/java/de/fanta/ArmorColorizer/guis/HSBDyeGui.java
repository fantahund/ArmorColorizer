package de.fanta.ArmorColorizer.guis;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.data.Messages;
import de.fanta.ArmorColorizer.utils.ArmordDyeingUtil;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.ColorUtils;
import de.fanta.ArmorColorizer.utils.CustomHeadsUtil;
import de.fanta.ArmorColorizer.utils.HSBColor;
import de.fanta.ArmorColorizer.utils.guiutils.AbstractWindow;
import de.fanta.ArmorColorizer.utils.guiutils.GUIUtils;
import de.iani.cubesideutils.bukkit.items.CustomHeads;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class HSBDyeGui extends AbstractWindow {
    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    public static final int INVENTORY_SIZE = 27;

    private static final int HUE_INDEX = 10;
    private static final int ADD_HUE_INDEX = 1;
    private static final int REMOVE_HUE_INDEX = 19;

    private static final int SATURATION_INDEX = 11;
    private static final int ADD_SATURATION_INDEX = 2;
    private static final int REMOVE_SATURATION_INDEX = 20;

    private static final int LUMINANCE_INDEX = 12;
    private static final int ADD_LUMINANCE_INDEX = 3;
    private static final int REMOVE_LUMINANCE_INDEX = 21;

    private static final int ARMOR_INDEX = 14;

    private static final int CONFIRM_INDEX = 16;
    private static final int RANDOM_INDEX = 5;
    private static final int SAVE_COLOR_INDEX = 23;

    private static final HashMap<UUID, ItemStack> playerItemList = new HashMap<>();
    private static final HashMap<UUID, HSBColor> playerColorList = new HashMap<>();

    public HSBDyeGui(Player player, ItemStack stack) {
        super(player, Bukkit.createInventory(player, INVENTORY_SIZE, plugin.getMessages().getHsbColorizerTitle()));
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
            case ADD_HUE_INDEX -> {
                HSBColor color = ColorUtils.calculateHSBColor(playerColorList.get(player.getUniqueId()), event.isShiftClick() ? 10 : 1, 0, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case REMOVE_HUE_INDEX -> {
                HSBColor color = ColorUtils.calculateHSBColor(playerColorList.get(player.getUniqueId()), event.isShiftClick() ? -10 : -1, 0, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case ADD_SATURATION_INDEX -> {
                HSBColor color = ColorUtils.calculateHSBColor(playerColorList.get(player.getUniqueId()), 0, event.isShiftClick() ? 10 : 1, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case REMOVE_SATURATION_INDEX -> {
                HSBColor color = ColorUtils.calculateHSBColor(playerColorList.get(player.getUniqueId()), 0, event.isShiftClick() ? -10 : -1, 0);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case ADD_LUMINANCE_INDEX -> {
                HSBColor color = ColorUtils.calculateHSBColor(playerColorList.get(player.getUniqueId()), 0, 0, event.isShiftClick() ? 10 : 1);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case REMOVE_LUMINANCE_INDEX -> {
                HSBColor color = ColorUtils.calculateHSBColor(playerColorList.get(player.getUniqueId()), 0, 0, event.isShiftClick() ? -10 : -1);
                playerColorList.put(player.getUniqueId(), color);
                rebuildInventory();
            }
            case RANDOM_INDEX -> {
                playerColorList.put(player.getUniqueId(), ColorUtils.randomHSBColor());
                rebuildInventory();
            }
            case CONFIRM_INDEX -> {
                ItemStack stack = player.getInventory().getItemInMainHand();
                HSBColor color = playerColorList.get(player.getUniqueId());
                ArmordDyeingUtil.applyColorToItem(player, stack, org.bukkit.Color.fromRGB(color.getRGB().getRed(), color.getRGB().getGreen(), color.getRGB().getBlue()));
                player.closeInventory();
            }

            case SAVE_COLOR_INDEX -> {
                HSBColor hsbColor = playerColorList.get(player.getUniqueId());
                org.bukkit.Color color = org.bukkit.Color.fromRGB(hsbColor.getRGB().getRed(), hsbColor.getRGB().getGreen(), hsbColor.getRGB().getBlue());
                try {
                    if (!plugin.getPlayerColors(player).contains(color)) {
                        plugin.getDatabase().insertColor(player.getUniqueId(), color.asRGB());
                        plugin.addPlayerColor(player, color);
                        ChatUtil.sendNormalMessage(player, plugin.getMessages().getInsertColorSuccessful());
                        rebuildInventory();
                    } else {
                        ChatUtil.sendWarningMessage(player, plugin.getMessages().getInsertColorAlreadyAvailable());
                    }
                } catch (SQLException e) {
                    plugin.getLogger().log(Level.SEVERE, "color " + color.asRGB() + "could not be saved", e);
                    ChatUtil.sendErrorMessage(player, plugin.getMessages().getInsertColorError());
                }
            }
            default -> {
            }
        }
    }

    @Override
    protected void rebuildInventory() {
        ItemStack armorItem = playerItemList.get(getPlayer().getUniqueId());
        LeatherArmorMeta meta = (LeatherArmorMeta) armorItem.getItemMeta();
        HSBColor HSBcolor = playerColorList.getOrDefault(getPlayer().getUniqueId(), new HSBColor(new Color(meta.getColor().asRGB())));
        org.bukkit.Color color = org.bukkit.Color.fromRGB(HSBcolor.getRGB().getRed(), HSBcolor.getRGB().getGreen(), HSBcolor.getRGB().getBlue());
        meta.setColor(org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
        armorItem.setItemMeta(meta);
        playerColorList.put(getPlayer().getUniqueId(), HSBcolor);
        Messages messages = plugin.getMessages();

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            ItemStack item;
            switch (i) {
                case HUE_INDEX -> item = GUIUtils.createGuiItem(Material.LOOM, messages.getHue(HSBcolor.getHue()));
                case ADD_HUE_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_UP.getHead(messages.getAddHue(HSBcolor.getHue()), messages.getClick(1), messages.getShiftClick(10));
                case REMOVE_HUE_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_DOWN.getHead(messages.getAddHue(HSBcolor.getHue()), messages.getClick(-1), messages.getShiftClick(-10));

                case SATURATION_INDEX ->
                        item = GUIUtils.createGuiItem(Material.WOODEN_SWORD, messages.getSaturation(HSBcolor.getSaturation()));
                case ADD_SATURATION_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_UP.getHead(messages.getAddSaturation(HSBcolor.getSaturation()), messages.getClick(1), messages.getShiftClick(10));
                case REMOVE_SATURATION_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_DOWN.getHead(messages.getRemoveSaturation(HSBcolor.getSaturation()), messages.getClick(-1), messages.getShiftClick(-10));

                case LUMINANCE_INDEX ->
                        item = GUIUtils.createGuiItem(Material.LIGHT, messages.getBrightness(HSBcolor.getBrightness()));
                case ADD_LUMINANCE_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_UP.getHead(messages.getAddBrightness(HSBcolor.getBrightness()), messages.getClick(1), messages.getShiftClick(10));
                case REMOVE_LUMINANCE_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_DOWN.getHead(messages.getRemoveBrightness(HSBcolor.getBrightness()), messages.getClick(-1), messages.getShiftClick(-10));

                case ARMOR_INDEX -> item = armorItem;

                case CONFIRM_INDEX ->
                        item = CustomHeads.RAINBOW_ARROW_RIGHT.getHead(ChatUtil.GREEN + messages.getChangeColor(), plugin.getEconomy().getBalance(getPlayer()) >= plugin.getArmorColorizerConfig().getEconomyPrice() ? ChatUtil.GREEN + messages.getPrice(plugin.getArmorColorizerConfig().getEconomyPrice(), getPlayer().getGameMode() == GameMode.CREATIVE ? "" : plugin.getArmorColorizerConfig().getEconomyPrice() > 1 ? plugin.getEconomy().currencyNamePlural() : plugin.getEconomy().currencyNameSingular()) : ChatUtil.RED + messages.getNotenoughmoney());
                case RANDOM_INDEX -> item = CustomHeads.RAINBOW_R.getHead(ChatUtil.GREEN + messages.getRandomColor());
                case SAVE_COLOR_INDEX ->
                        item = CustomHeadsUtil.SERVER.getHead(plugin.getPlayerColors(getPlayer()).contains(color) ? ChatUtil.RED + messages.getGuiSaveColor() : ChatUtil.GREEN + messages.getGuiSaveColor());
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
