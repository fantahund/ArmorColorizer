package de.fanta.ArmorColorizer.guis;

import de.fanta.ArmorColorizer.ArmorColorizer;
import de.fanta.ArmorColorizer.utils.ArmordDyeingUtil;
import de.fanta.ArmorColorizer.utils.ChatUtil;
import de.fanta.ArmorColorizer.utils.ItemUtil;
import de.iani.cubesideutils.StringUtil;
import de.iani.cubesideutils.bukkit.inventory.AbstractWindow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.HashMap;
import java.util.UUID;

public class ArmorTrimColorSelectGui extends AbstractWindow {

    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();

    private static final HashMap<UUID, ItemStack> playerItemList = new HashMap<>();
    private static final HashMap<UUID, TrimPattern> playerTrimList = new HashMap<>();

    public ArmorTrimColorSelectGui(Player player, ItemStack stack, TrimPattern trimPattern) {
        super(player, Bukkit.createInventory(player, getInventorySize(), plugin.getMessages().getTrimSelectColorGUITitle()));
        playerItemList.put(player.getUniqueId(), stack);
        playerTrimList.put(player.getUniqueId(), trimPattern);
    }

    @Override
    protected void rebuildInventory() {
        for (int i = 0; i < plugin.getTrimColorMap().size(); i++) {
            ItemStack template = new ItemStack((Material) plugin.getTrimColorMap().keySet().toArray()[i]);
            ItemMeta meta = template.getItemMeta();
            for (ItemFlag flag : ItemFlag.values()) {
                meta.addItemFlags(flag);
            }
            TrimMaterial trimMaterial = plugin.getTrimColorMap().get(template.getType());
            meta.setDisplayName(ChatUtil.GREEN + StringUtil.capitalizeFirstLetter(trimMaterial.getKey().getKey(), false));
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
            TrimMaterial trimMaterial = plugin.getTrimColorMap().get(stack.getType());
            if (trimMaterial != null) {
                ArmordDyeingUtil.applyTrimAndColorToArmor(player, playerItemList.get(player.getUniqueId()), new ArmorTrim(trimMaterial, playerTrimList.get(player.getUniqueId())));
            }
            player.closeInventory();
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
