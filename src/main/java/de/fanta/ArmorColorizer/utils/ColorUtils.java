package de.fanta.ArmorColorizer.utils;

import org.bukkit.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ColorUtils {
    public static List<Color> rainbow(List<Color> colorList) {
        HashMap<Integer, Color> hsbColors = new HashMap<>();
        for (Color color : colorList) {
            HSBColor hsbColor = new HSBColor(new java.awt.Color(color.asRGB()));
            hsbColors.put(Integer.parseInt((int) hsbColor.getHue() + "" + (int) hsbColor.getSaturation() + "" + (int) hsbColor.getBrightness()), color);
        }


        Integer[] colors = hsbColors.keySet().toArray(Integer[]::new);
        Arrays.sort(colors, Comparator.comparingInt(Integer::intValue));

        List<Color> rainbowList = new ArrayList<>();
        for (Integer colorInt : colors) {
            rainbowList.add(hsbColors.get(colorInt));
        }


        return rainbowList;
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
}
