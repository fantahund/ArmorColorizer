package de.fanta.ArmorColorizer.utils;

import org.bukkit.Color;

import java.util.Random;

public class ColorUtils {
    public Color[] rainbow(Color[] array, int k) {
        if (array == null || array.length < 2) {
            return array;
        }
        int left = 0;
        int right = array.length - 1;
        for (int round = 1; round <= k / 2; round++) {
            int rightColor = k + 1 - round;
            for (int i = left; i <= right; i++) {
                if (array[i].asRGB() == round) {
                    swap(array, i, left);
                    left++;
                } else if (array[i].asRGB() == rightColor) {
                    swap(array, i, right);
                    i--;
                    right--;
                }
            }
        }
        return array;
    }

    private void swap(Color[] array, int left, int right) {
        int tmp = array[left].asRGB();
        array[left] = array[right];
        array[right] = Color.fromRGB(tmp);
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
