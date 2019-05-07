package com.fr.canvas;

import com.fr.general.ComparatorUtils;
import com.fr.log.FineLoggerFactory;
import com.fr.stable.StringUtils;

import java.awt.Color;
import java.awt.Paint;

public class ColorsAdapter {

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final String JS_OBJECT = "[object Object]";

    public static Color web(String colorString) {
        if (StringUtils.isEmpty(colorString)) {
            throw new IllegalArgumentException("Invalid color specification");
        }

        String color = colorString.toLowerCase().trim();

        if (color.startsWith("#")) {
            color = color.substring(1);
        } else if (color.startsWith("rgb")) {
            if (color.startsWith("(", 3)) {
                return parseRGBColor(color, 4);
            } else if (color.startsWith("a(", 3)) {
                return parseRGBColor(color, 5);
            }
        } else if (color.startsWith("hsl")) {
            if (color.startsWith("(", 3)) {
                return parseHSLColor(color, 4);
            } else if (color.startsWith("a(", 3)) {
                return parseHSLColor(color, 5);
            }
        } else {
            return getColor(color);
        }
        int len = color.length();
        int r, g, b, a;
        try{
            if (len == 3) {
                r = Integer.parseInt(color.substring(0, 1), 16);
                g = Integer.parseInt(color.substring(1, 2), 16);
                b = Integer.parseInt(color.substring(2, 3), 16);
                return new Color(r / 15.0f, g / 15.0f, b / 15.0f);
            } else if (len == 4) {
                r = Integer.parseInt(color.substring(0, 1), 16);
                g = Integer.parseInt(color.substring(1, 2), 16);
                b = Integer.parseInt(color.substring(2, 3), 16);
                a = Integer.parseInt(color.substring(3, 4), 16);
                return new Color(r / 15.0f, g / 15.0f, b / 15.0f, a / 15.0f);
            }
            if (len == 6) {
                r = Integer.parseInt(color.substring(0, 2), 16);
                g = Integer.parseInt(color.substring(2, 4), 16);
                b = Integer.parseInt(color.substring(4, 6), 16);
                return new Color(r, g, b);
            } else if (len == 8) {
                r = Integer.parseInt(color.substring(0, 2), 16);
                g = Integer.parseInt(color.substring(2, 4), 16);
                b = Integer.parseInt(color.substring(4, 6), 16);
                a = Integer.parseInt(color.substring(6, 8), 16);
                return new Color(r, g, b, a);
            }
        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
            return null;
        }
        throw new IllegalArgumentException("Invalid color specification");
    }

    private static Color parseRGBColor(String color, int off) {

        color = color.substring(off, color.length() - 1);
        String[] colors = color.split(",");
        if (colors.length >= 3) {
            try{
                double r = parseComponent(colors[0], PARSE_COMPONENT);
                double g = parseComponent(colors[1], PARSE_COMPONENT);
                double b = parseComponent(colors[2], PARSE_COMPONENT);
                double a = 1.0;
                if (colors.length > 3) {
                    a = parseComponent(colors[3], PARSE_ALPHA);
                }
                return new Color((float) r, (float) g, (float) b, (float) a);
            } catch (Exception ex) {
                FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
                return null;
            }
        }
        throw new IllegalArgumentException("Invalid color specification");
    }

    private static Color parseHSLColor(String color, int off) {
        color = color.substring(off, color.length() - 1);
        String[] colors = color.split(",");
        if (colors.length >= 3) {
            try{
                double h = parseComponent(colors[0], PARSE_ANGLE);
                double s = parseComponent(colors[1], PARSE_PERCENT);
                double l = parseComponent(colors[2], PARSE_PERCENT);
                double a = 1.0;
                if (colors.length > 3) {
                    a = parseComponent(colors[3], PARSE_ALPHA);
                }
                h = h / 360;
                return hsla2Rgba(h, s, l, a);
            } catch (Exception ex) {
                FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
                return null;
            }
        }
        throw new IllegalArgumentException("Invalid color specification");
    }

    public static Color hsla2Rgba(double h, double s, double l, double a) {
        double r, g, b;
        double p, q;
        if (s == 0) {
            r = l * 255.0;
            g = l * 255.0;
            b = l * 255.0;
        } else {
            q = l < 0.5 ? l * (1.0 + s) : l + s - (l * s);
            p = 2.0 * l - q;
            r = 255.0 * hue2Rgb(p, q, h + (1.0 / 3.0));
            g = 255.0 * hue2Rgb(p, q, h);
            b = 255.0 * hue2Rgb(p, q, h - (1.0 / 3.0));
        }
        return new Color((int) r, (int) g, (int) b, (int) (a * 255 + 0.5));
    }

    private static double hue2Rgb(double p, double q, double h) {
        if (h < 0) {
            h += 1;
        }
        if (h > 1) {
            h -= 1;
        }
        if (6.0 * h < 1) {
            return p + (q - p) * 6.0 * h;
        }
        if (2.0 * h < 1) {
            return q;
        }
        if (3.0 * h < 2) {
            return p + (q - p) * ((2.0 / 3.0) - h) * 6.0;
        }
        return p;
    }

    private static final int PARSE_COMPONENT = 0; // clamped to [0,255]
    private static final int PARSE_PERCENT = 1; // clamped to [0,100]%
    private static final int PARSE_ANGLE = 2; // clamped to [0,360]
    private static final int PARSE_ALPHA = 3; // clamped to [0.0,1.0]

    private static double parseComponent(String color, int type) {
        color = color.trim();
        if (color.endsWith("%")) {
            type = PARSE_PERCENT;
            color = color.substring(0, color.length() - 1).trim();
        } else if (type == PARSE_PERCENT) {
            throw new IllegalArgumentException("Invalid color specification");
        }
        double c = Double.parseDouble(color);
        switch (type) {
            case PARSE_ALPHA:
                return (c < 0.0) ? 0.0 : ((c > 1.0) ? 1.0 : c);
            case PARSE_PERCENT:
                return (c <= 0.0) ? 0.0 : ((c >= 100.0) ? 1.0 : (c / 100.0));
            case PARSE_COMPONENT:
                return (c <= 0.0) ? 0.0 : ((c >= 255.0) ? 1.0 : (c / 255.0));
            case PARSE_ANGLE:
                return ((c < 0.0)
                        ? ((c % 360.0) + 360.0)
                        : ((c > 360.0)
                        ? (c % 360.0)
                        : c));
        }
        throw new IllegalArgumentException("Invalid color specification");
    }

    public static Paint parsePaint(String style) {

        if (isJsNull(style) || ComparatorUtils.equals(style, JS_OBJECT)) {
            return null;
        }
        try{
            return valueOf(style);
        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
            return null;
        }
    }

    private static boolean isJsNull(String style) {

        return style == null || ComparatorUtils.equals(style, "none") || ComparatorUtils.equals(style, "undefined");

    }

    private static Paint valueOf(String value) {
        if (value == null) {
            throw new NullPointerException("paint must be specified");
        }
        if (value.startsWith("linear-gradient(")) {
            return LinearGradientAdapter.valueOf(value);
        } else if (value.startsWith("radial-gradient(")) {
            return RadialGradientAdapter.valueOf(value);
        } else {
            return web(value);
        }
    }

    private static Color getColor(String name) {
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.web(name);
        return fxColor2Color(fxColor);
    }

    public static Color fxColor2Color(javafx.scene.paint.Color fxColor) {
        return new Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue(), (float) fxColor.getOpacity());
    }
}
