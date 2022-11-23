package de.fanta.ArmorColorizer.utils;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmordDyeingUtil {

    public static Color defaultColor = hex2Color("#A06540");

    public static ItemStack dyeingLeatherItem(ItemStack stack, Color color) {
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

    public static ItemStack dyeingLeatherItem(ItemStack stack, String hexCode) {
        return dyeingLeatherItem(stack, hex2Color(hexCode));
    }


    public static Color hex2Color(String colorStr) {
        Color color;
        String stringColor = colorStr.startsWith("#") ? colorStr : "#" + colorStr;
        try {
            color = Color.fromRGB(Integer.valueOf(stringColor.substring(1, 3), 16), Integer.valueOf(stringColor.substring(3, 5), 16), Integer.valueOf(stringColor.substring(5, 7), 16));
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            color = null;
        }
        return color;
    }

    public static boolean itemHasSameColor(ItemStack stack, Color color) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
        Color itemColor = itemMeta.getColor();
        return itemColor.toString().equals(color.toString());
    }

    public static boolean itemHasSameColor(ItemStack stack, String hexColor) {
        return itemHasSameColor(stack, hex2Color(hexColor));
    }

    public static Color randomColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        return hex2Color(String.format("%02x", red) + String.format("%02x", green) + String.format("%02x", blue));
    }

    public static Color calculateColor(Color color, int red, int green, int blue) {
        int calcRed = Math.min(color.getRed() + red, 255);
        int calcGreen = Math.min(color.getGreen() + green, 255);
        int calcBlue = Math.min(color.getBlue() + blue, 255);
        return hex2Color(String.format("%02x", Math.max(calcRed, 0)) + String.format("%02x", Math.max(calcGreen, 0)) + String.format("%02x", Math.max(calcBlue, 0)));
    }
}
