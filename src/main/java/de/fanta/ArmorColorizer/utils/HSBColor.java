package de.fanta.ArmorColorizer.utils;

import java.awt.*;

/**
 * Original Code from jeffjianzhao: https://github.com/jeffjianzhao/DAViewer/blob/master/src/daviewer/HSLColor.java
 * <p>
 * /**
 * The HSBColor class provides methods to manipulate HSB (Hue, Saturation
 * Brightness) values to create a corresponding Color object using the RGB
 * ColorSpace.
 * <p>
 * The HUE is the color, the Saturation is the purity of the color (with
 * respect to grey) and Brightness is the brightness of the color (with respect
 * to black and white)
 * <p>
 * The Hue is specified as an angel between 0 - 360 degrees where red is 0,
 * green is 120 and blue is 240. In between you have the colors of the rainbow.
 * Saturation is specified as a percentage between 0 - 100 where 100 is fully
 * saturated and 0 approaches gray. Brightness is specified as a percentage
 * between 0 - 100 where 0 is black and 100 is white.
 * <p>
 * In particular the HSB color space makes it easier change the Tone or Shade
 * of a color by adjusting the brightness value.
 */
public class HSBColor {
    private final Color rgb;
    private final float[] hsb;
    private final float alpha;

    /**
     * Create a HSBColor object using an RGB Color object.
     *
     * @param rgb the RGB Color object
     */
    public HSBColor(Color rgb) {
        this.rgb = rgb;
        hsb = fromRGB(rgb);
        alpha = rgb.getAlpha() / 255.0f;
    }

    /**
     * Create a HSBColor object using individual HSB values and a default
     * alpha value of 1.0.
     *
     * @param h is the Hue value in degrees between 0 - 360
     * @param s is the Saturation percentage between 0 - 100
     * @param l is the Lumanance percentage between 0 - 100
     */
    public HSBColor(float h, float s, float l) {
        this(h, s, l, 1.0f);
    }

    /**
     * Create a HSBColor object using individual HSB values.
     *
     * @param h     the Hue value in degrees between 0 - 360
     * @param s     the Saturation percentage between 0 - 100
     * @param b     the Lumanance percentage between 0 - 100
     * @param alpha the alpha value between 0 - 1
     */
    public HSBColor(float h, float s, float b, float alpha) {
        hsb = new float[]{h, s, b};
        this.alpha = alpha;
        rgb = toRGB(hsb, alpha);
    }

    /**
     * Create a HSbColor object using an array containing the
     * individual HSb values and with a default alpha value of 1.
     *
     * @param hsb array containing HSB values
     */
    public HSBColor(float[] hsb) {
        this(hsb, 1.0f);
    }

    /**
     * Create a HSBColor object using an array containing the
     * individual HSB values.
     *
     * @param hsb   array containing HSB values
     * @param alpha the alpha value between 0 - 1
     */
    public HSBColor(float[] hsb, float alpha) {
        this.hsb = hsb;
        this.alpha = alpha;
        rgb = toRGB(hsb, alpha);
    }

    /**
     * Create an RGB Color object based on this HSBColor with a different
     * Hue value. The degrees specified is an absolute value.
     *
     * @param degrees - the Hue value between 0 - 360
     * @return the RGB Color object
     */
    public Color adjustHue(float degrees) {
        return toRGB(degrees, hsb[1], hsb[2], alpha);
    }

    /**
     * Create an RGB Color object based on this HSBColor with a different
     * Luminance value. The percent specified is an absolute value.
     *
     * @param percent - the Luminance value between 0 - 100
     * @return the RGB Color object
     */
    public Color adjustBrightness(float percent) {
        return toRGB(hsb[0], hsb[1], percent, alpha);
    }

    /**
     * Create an RGB Color object based on this HSBColor with a different
     * Saturation value. The percent specified is an absolute value.
     *
     * @param percent - the Saturation value between 0 - 100
     * @return the RGB Color object
     */
    public Color adjustSaturation(float percent) {
        return toRGB(hsb[0], percent, hsb[2], alpha);
    }

    /**
     * Create a RGB Color object based on this HSBColor with a different
     * Shade. Changing the shade will return a darker color. The percent
     * specified is a relative value.
     *
     * @param percent - the value between 0 - 100
     * @return the RGB Color object
     */
    public Color adjustShade(float percent) {
        float multiplier = (100.0f - percent) / 100.0f;
        float l = Math.max(0.0f, hsb[2] * multiplier);

        return toRGB(hsb[0], hsb[1], l, alpha);
    }

    /**
     * Create an RGB Color object based on this HSBColor with a different
     * Tone. Changing the tone will return a lighter color. The percent
     * specified is a relative value.
     *
     * @param percent - the value between 0 - 100
     * @return the RGB Color object
     */
    public Color adjustTone(float percent) {
        float multiplier = (100.0f + percent) / 100.0f;
        float l = Math.min(100.0f, hsb[2] * multiplier);

        return toRGB(hsb[0], hsb[1], l, alpha);
    }

    /**
     * Get the Alpha value.
     *
     * @return the Alpha value.
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Create an RGB Color object that is the complementary color of this
     * HSBColor. This is a convenience method. The complementary color is
     * determined by adding 180 degrees to the Hue value.
     *
     * @return the RGB Color object
     */
    public Color getComplementary() {
        float hue = (hsb[0] + 180.0f) % 360.0f;
        return toRGB(hue, hsb[1], hsb[2]);
    }

    /**
     * Get the Hue value.
     *
     * @return the Hue value.
     */
    public float getHue() {
        return hsb[0];
    }

    /**
     * Get the HSB values.
     *
     * @return the HSB values.
     */
    public float[] getHSB() {
        return hsb;
    }

    /**
     * Get the Luminance value.
     *
     * @return the Luminance value.
     */
    public float getBrightness() {
        return hsb[2];
    }

    /**
     * Get the RGB Color object represented by this HDLColor.
     *
     * @return the RGB Color object.
     */
    public Color getRGB() {
        return rgb;
    }

    /**
     * Get the Saturation value.
     *
     * @return the Saturation value.
     */
    public float getSaturation() {
        return hsb[1];
    }

    @Override
    public String toString() {
        String toString = "HSBColor[h=" + hsb[0] +
                ",s=" + hsb[1] +
                ",b=" + hsb[2] +
                ",alpha=" + alpha + "]";

        return toString;
    }

    /**
     * Convert a RGB Color to it corresponding HSB values.
     *
     * @return an array containing the 3 HSB values.
     */
    public static float[] fromRGB(Color color) {
        // Get RGB values in the range 0 - 1

        float[] rgb = color.getRGBColorComponents(null);
        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];

        // Minimum and Maximum RGB values are used in the HSB calculations

        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        // Calculate the Hue

        float h = 0;

        if (max == min) {
            h = 0;
        } else if (max == r) {
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * (b - r) / (max - min)) + 120;
        } else if (max == b) {
            h = (60 * (r - g) / (max - min)) + 240;
        }

        // Calculate the Luminance

        float l = max;
        // System.out.println(max + " : " + min + " : " + l);

        // Calculate the Saturation

        float s = 0;
        if (max != min) {
            s = (max - min) / max;
        }

        return new float[]{h, s * 100, l * 100};
    }

    /**
     * Convert HSB values to an RGB Color with a default alpha value of 1.
     * H (Hue) is specified as degrees in the range 0 - 360.
     * S (Saturation) is specified as a percentage in the range 1 - 100.
     * B (Brightness) is specified as a percentage in the range 1 - 100.
     *
     * @param hsb an array containing the 3 HSB values
     * @returns the RGB Color object
     */
    public static Color toRGB(float[] hsb) {
        return toRGB(hsb, 1.0f);
    }

    /**
     * Convert HSB values to an RGB Color.
     * H (Hue) is specified as degrees in the range 0 - 360.
     * S (Saturation) is specified as a percentage in the range 1 - 100.
     * L (Lumanance) is specified as a percentage in the range 1 - 100.
     *
     * @param hsb   an array containing the 3 HSB values
     * @param alpha the alpha value between 0 - 1
     * @returns the RGB Color object
     */
    public static Color toRGB(float[] hsb, float alpha) {
        return toRGB(hsb[0], hsb[1], hsb[2], alpha);
    }

    /**
     * Convert HSB values to an RGB Color with a default alpha value of 1.
     *
     * @param h Hue is specified as degrees in the range 0 - 360.
     * @param s Saturation is specified as a percentage in the range 1 - 100.
     * @param l Lumanance is specified as a percentage in the range 1 - 100.
     * @returns the RGB Color object
     */
    public static Color toRGB(float h, float s, float l) {
        return toRGB(h, s, l, 1.0f);
    }

    /**
     * Convert HSB values to an RGB Color.
     *
     * @param hue   Hue is specified as degrees in the range 0 - 360.
     * @param s     Saturation is specified as a percentage in the range 1 - 100.
     * @param l     Lumanance is specified as a percentage in the range 1 - 100.
     * @param alpha the alpha value between 0 - 1
     * @returns the RGB Color object
     */
    public static Color toRGB(float hue, float s, float l, float alpha) {
        if (s < 0.0f || s > 100.0f) {
            String message = "Color parameter outside of expected range - Saturation";
            throw new IllegalArgumentException(message);
        }

        if (l < 0.0f || l > 100.0f) {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException(message);
        }

        if (alpha < 0.0f || alpha > 1.0f) {
            String message = "Color parameter outside of expected range - Alpha";
            throw new IllegalArgumentException(message);
        }

        // Formula needs all values between 0 - 1.

        hue = hue % 360.0f;
        hue /= 360f;
        s /= 100f;
        l /= 100f;

        float r = 0, g = 0, b = 0;
        if (s == 0) {
            r = g = b = l;
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) Math.floor(h);
            float p = l * (1.0f - s);
            float q = l * (1.0f - s * f);
            float t = l * (1.0f - (s * (1.0f - f)));
            switch ((int) h) {
                case 0 -> {
                    r = l;
                    g = t;
                    b = p;
                }
                case 1 -> {
                    r = q;
                    g = l;
                    b = p;
                }
                case 2 -> {
                    r = p;
                    g = l;
                    b = t;
                }
                case 3 -> {
                    r = p;
                    g = q;
                    b = l;
                }
                case 4 -> {
                    r = t;
                    g = p;
                    b = l;
                }
                case 5 -> {
                    r = l;
                    g = p;
                    b = q;
                }
            }
        }

        return new Color(r, g, b, alpha);
    }
}
