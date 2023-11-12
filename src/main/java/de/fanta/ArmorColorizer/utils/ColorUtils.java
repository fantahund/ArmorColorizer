package de.fanta.ArmorColorizer.utils;

import de.fanta.ArmorColorizer.ArmorColorizer;
import org.bukkit.Color;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.List;
import java.util.Random;

public class ColorUtils {

    private static final ArmorColorizer plugin = ArmorColorizer.getPlugin();
    private static final Random random = new Random();

    public static List<Color> rainbow(List<Color> colorList) {
        return colorList.stream()
                .map(c -> new HSBColor(new java.awt.Color(c.asRGB())))
                .sorted()
                .map(c -> Color.fromRGB(c.getRGB().getRed(), c.getRGB().getGreen(), c.getRGB().getBlue()))
                .toList();
    }

    public static Color randomRGBColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        return hex2Color(String.format("%02x", red) + String.format("%02x", green) + String.format("%02x", blue));
    }

    public static HSBColor randomHSBColor() {
        Random random = new Random();
        return new HSBColor(random.nextInt(0, 360), random.nextInt(0, 100), random.nextInt(20, 100));
    }

    public static Color calculateRGBColor(Color color, int red, int green, int blue) {
        int calcRed = Math.min(color.getRed() + red, 255);
        int calcGreen = Math.min(color.getGreen() + green, 255);
        int calcBlue = Math.min(color.getBlue() + blue, 255);
        return hex2Color(String.format("%02x", Math.max(calcRed, 0)) + String.format("%02x", Math.max(calcGreen, 0)) + String.format("%02x", Math.max(calcBlue, 0)));
    }

    public static HSBColor calculateHSBColor(HSBColor color, int hue, int saturation, int brightness) {
        float calcHue = color.getHue() + hue;
        if (calcHue > 360) {
            calcHue = calcHue - 360;
        } else if (calcHue < 0) {
            calcHue = calcHue + 360;
        }
        float calcSaturation = Math.min(color.getSaturation() + saturation, 100f);
        float calcBrightness = Math.min(color.getBrightness() + brightness, 100f);
        return new HSBColor(Math.max(calcHue, 0), Math.max(calcSaturation, 0), Math.max(calcBrightness, 0));
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

    public static ArmorTrim getRandomArmorTrim() {
        List<TrimPattern> trimPatternList = plugin.getTrimPatternMap().values().stream().toList();
        List<TrimMaterial> trimMaterialList = plugin.getTrimColorMap().values().stream().toList();
        TrimPattern trimPattern = trimPatternList.get(random.nextInt(trimPatternList.size()));
        TrimMaterial trimMaterial = trimMaterialList.get(random.nextInt(trimMaterialList.size()));
        return new ArmorTrim(trimMaterial, trimPattern);
    }
}
