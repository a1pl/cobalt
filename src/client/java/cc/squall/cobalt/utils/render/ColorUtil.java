package cc.squall.cobalt.utils.render;

import java.awt.Color;

public final class ColorUtil {
    private ColorUtil() {}

    private static int clamp(int v) {
        return Math.clamp(v, 0, 255);
    }

    public static int toRGBA(int r, int g, int b, int a) {
        return clamp(a) << 24 | clamp(r) << 16 | clamp(g) << 8 | clamp(b);
    }

    public static int toRGBA(int r, int g, int b) {
        return toRGBA(r, g, b, 255);
    }

    public static int toRGBA(float r, float g, float b, float a) {
        return toRGBA(Math.round(r * 255f), Math.round(g * 255f),
                Math.round(b * 255f), Math.round(a * 255f));
    }

    public static int toRGBA(double r, double g, double b, double a) {
        return toRGBA((float) r, (float) g, (float) b, (float) a);
    }

    public static int toRGBA(float[] c) {
        if (c.length != 4) throw new IllegalArgumentException("colors[] must have a length of 4!");
        return toRGBA(c[0], c[1], c[2], c[3]);
    }

    public static int toRGBA(double[] c) {
        if (c.length != 4) throw new IllegalArgumentException("colors[] must have a length of 4!");
        return toRGBA(c[0], c[1], c[2], c[3]);
    }

    public static int toRGBA(Color color) {
        return color.getRGB();
    }

    public static int[] toRGBAArray(int color) {
        return new int[]{color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >>> 24};
    }

    public static int changeAlpha(int color, int alpha) {
        return clamp(alpha) << 24 | (color & 0xFFFFFF);
    }

    public static int toRGB(Color c) {
        return c.getRGB() & 0xFFFFFF;
    }

    public static int parseHex(String hex) {
        if (hex == null) throw new IllegalArgumentException("Color string null");
        String s = hex.trim();
        if (s.startsWith("#")) s = s.substring(1);
        if (s.length() == 6) return 0xFF000000 | Integer.parseInt(s, 16);
        if (s.length() == 8) return Integer.parseUnsignedInt(s, 16);
        throw new IllegalArgumentException("Invalid color: " + hex);
    }
}