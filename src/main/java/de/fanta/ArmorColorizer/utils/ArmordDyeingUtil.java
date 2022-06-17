package de.fanta.ArmorColorizer.utils;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmordDyeingUtil {


    public static ItemStack dyeingLeatherItem(ItemStack stack, String hexCode) {
        Color color = hex2Color(hexCode);

        if (color == null) {
            return null;
        }

        LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        itemMeta.setColor(color);
        stack.setItemMeta(itemMeta);

        return stack;
    }


    public static Color hex2Color(String colorStr) {
        Color color;
        try {
            color = Color.fromRGB(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
        } catch (IllegalArgumentException e) {
            color = null;
        }
        return color;
    }
}
