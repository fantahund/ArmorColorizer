package de.fanta.ArmorColorizer.guis;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.utils.ItemUtil;
import de.iani.cubesideutils.bukkit.inventory.AbstractWindow;
import java.util.HashMap;
import java.util.UUID;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ArmorTrimSelectGui extends AbstractWindow {

    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    private static final HashMap<UUID, ItemStack> playerItemList = new HashMap<>();

    public ArmorTrimSelectGui(Player player, ItemStack stack) {
        super(player, Bukkit.createInventory(player, getInventorySize(), plugin.getMessages().getTrimSelectGUITitle()));
        playerItemList.put(player.getUniqueId(), stack);
    }

    @Override
    protected void rebuildInventory() {
        for (int i = 0; i < plugin.getTrimPatternMap().size(); i++) {
            ItemStack template = new ItemStack((Material) plugin.getTrimPatternMap().keySet().toArray()[i]);
            ItemMeta meta = template.getItemMeta();
            for (ItemFlag flag : ItemFlag.values()) {
                meta.addItemFlags(flag);
            }
            TrimPattern trimPattern = plugin.getTrimPatternMap().get(template.getType());
            meta.displayName(trimPattern.description().color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            template.setItemMeta(meta);

            int templateRow = i / 9;
            int templateSlot = i % 9 + (templateRow * 9);

            this.getInventory().setItem(templateSlot, new ItemStack(template));
        }

        for (int slot = 0; slot < this.getInventory().getSize(); slot++) {
            ItemStack item = this.getInventory().getItem(slot);
            if (item == null) {
                this.getInventory().setItem(slot, ItemUtil.EMPTY_ICON);
            }
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
        ItemStack stack = getInventory().getItem(slot);
        if (stack != null && stack.getType() != Material.AIR) {
            TrimPattern pattern = plugin.getTrimPatternMap().get(stack.getType());
            if (pattern != null) {
                new ArmorTrimColorSelectGui(player, playerItemList.get(player.getUniqueId()), pattern).open();
            }
        }

        event.setCancelled(true);
    }

    @Override
    public void closed() {
        playerItemList.remove(getPlayer().getUniqueId());
    }

    private static int getInventorySize() {
        int armorTrimCount = plugin.getTrimPatternMap().size();
        if (armorTrimCount % 9 == 0) {
            return armorTrimCount;
        } else {
            return ((armorTrimCount / 9) + 1) * 9;
        }
    }
}
