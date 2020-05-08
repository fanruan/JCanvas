package com.fr.graph.g2d.canvas;

import com.fr.log.FineLoggerFactory;
import com.fr.stable.AssistUtils;
import com.fr.stable.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Paint;


public class ColorsAdapter {

    public final static Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final Color ALICEBLUE = new Color(0.9411765f, 0.972549f, 1.0f);

    public static final Color ANTIQUEWHITE = new Color(0.98039216f, 0.92156863f, 0.84313726f);

    public static final Color AQUA = new Color(0.0f, 1.0f, 1.0f);

    public static final Color AQUAMARINE = new Color(0.49803922f, 1.0f, 0.83137256f);

    public static final Color AZURE = new Color(0.9411765f, 1.0f, 1.0f);

    public static final Color BEIGE = new Color(0.9607843f, 0.9607843f, 0.8627451f);

    public static final Color BISQUE = new Color(1.0f, 0.89411765f, 0.76862746f);

    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);

    public static final Color BLANCHEDALMOND = new Color(1.0f, 0.92156863f, 0.8039216f);

    public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f);

    public static final Color BLUEVIOLET = new Color(0.5411765f, 0.16862746f, 0.8862745f);

    public static final Color BROWN = new Color(0.64705884f, 0.16470589f, 0.16470589f);

    public static final Color BURLYWOOD = new Color(0.87058824f, 0.72156864f, 0.5294118f);

    public static final Color CADETBLUE = new Color(0.37254903f, 0.61960787f, 0.627451f);

    public static final Color CHARTREUSE = new Color(0.49803922f, 1.0f, 0.0f);

    public static final Color CHOCOLATE = new Color(0.8235294f, 0.4117647f, 0.11764706f);

    public static final Color CORAL = new Color(1.0f, 0.49803922f, 0.3137255f);

    public static final Color CORNFLOWERBLUE = new Color(0.39215687f, 0.58431375f, 0.92941177f);

    public static final Color CORNSILK = new Color(1.0f, 0.972549f, 0.8627451f);

    public static final Color CRIMSON = new Color(0.8627451f, 0.078431375f, 0.23529412f);

    public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f);

    public static final Color DARKBLUE = new Color(0.0f, 0.0f, 0.54509807f);

    public static final Color DARKCYAN = new Color(0.0f, 0.54509807f, 0.54509807f);

    public static final Color DARKGOLDENROD = new Color(0.72156864f, 0.5254902f, 0.043137256f);

    public static final Color DARKGRAY = new Color(0.6627451f, 0.6627451f, 0.6627451f);

    public static final Color DARKGREEN = new Color(0.0f, 0.39215687f, 0.0f);

    public static final Color DARKGREY = DARKGRAY;

    public static final Color DARKKHAKI = new Color(0.7411765f, 0.7176471f, 0.41960785f);

    public static final Color DARKMAGENTA = new Color(0.54509807f, 0.0f, 0.54509807f);

    public static final Color DARKOLIVEGREEN = new Color(0.33333334f, 0.41960785f, 0.18431373f);

    public static final Color DARKORANGE = new Color(1.0f, 0.54901963f, 0.0f);

    public static final Color DARKORCHID = new Color(0.6f, 0.19607843f, 0.8f);

    public static final Color DARKRED = new Color(0.54509807f, 0.0f, 0.0f);

    public static final Color DARKSALMON = new Color(0.9137255f, 0.5882353f, 0.47843137f);

    public static final Color DARKSEAGREEN = new Color(0.56078434f, 0.7372549f, 0.56078434f);

    public static final Color DARKSLATEBLUE = new Color(0.28235295f, 0.23921569f, 0.54509807f);

    public static final Color DARKSLATEGRAY = new Color(0.18431373f, 0.30980393f, 0.30980393f);

    public static final Color DARKSLATEGREY = DARKSLATEGRAY;

    public static final Color DARKTURQUOISE = new Color(0.0f, 0.80784315f, 0.81960785f);

    public static final Color DARKVIOLET = new Color(0.5803922f, 0.0f, 0.827451f);

    public static final Color DEEPPINK = new Color(1.0f, 0.078431375f, 0.5764706f);

    public static final Color DEEPSKYBLUE = new Color(0.0f, 0.7490196f, 1.0f);

    public static final Color DIMGRAY = new Color(0.4117647f, 0.4117647f, 0.4117647f);

    public static final Color DIMGREY = DIMGRAY;

    public static final Color DODGERBLUE = new Color(0.11764706f, 0.5647059f, 1.0f);

    public static final Color FIREBRICK = new Color(0.69803923f, 0.13333334f, 0.13333334f);

    public static final Color FLORALWHITE = new Color(1.0f, 0.98039216f, 0.9411765f);

    public static final Color FORESTGREEN = new Color(0.13333334f, 0.54509807f, 0.13333334f);

    public static final Color FUCHSIA = new Color(1.0f, 0.0f, 1.0f);

    public static final Color GAINSBORO = new Color(0.8627451f, 0.8627451f, 0.8627451f);

    public static final Color GHOSTWHITE = new Color(0.972549f, 0.972549f, 1.0f);

    public static final Color GOLD = new Color(1.0f, 0.84313726f, 0.0f);

    public static final Color GOLDENROD = new Color(0.85490197f, 0.64705884f, 0.1254902f);

    public static final Color GRAY = new Color(0.5019608f, 0.5019608f, 0.5019608f);

    public static final Color GREEN = new Color(0.0f, 0.5019608f, 0.0f);

    public static final Color GREENYELLOW = new Color(0.6784314f, 1.0f, 0.18431373f);

    public static final Color GREY = GRAY;

    public static final Color HONEYDEW = new Color(0.9411765f, 1.0f, 0.9411765f);

    public static final Color HOTPINK = new Color(1.0f, 0.4117647f, 0.7058824f);

    public static final Color INDIANRED = new Color(0.8039216f, 0.36078432f, 0.36078432f);

    public static final Color INDIGO = new Color(0.29411766f, 0.0f, 0.50980395f);

    public static final Color IVORY = new Color(1.0f, 1.0f, 0.9411765f);

    public static final Color KHAKI = new Color(0.9411765f, 0.9019608f, 0.54901963f);

    public static final Color LAVENDER = new Color(0.9019608f, 0.9019608f, 0.98039216f);

    public static final Color LAVENDERBLUSH = new Color(1.0f, 0.9411765f, 0.9607843f);

    public static final Color LAWNGREEN = new Color(0.4862745f, 0.9882353f, 0.0f);

    public static final Color LEMONCHIFFON = new Color(1.0f, 0.98039216f, 0.8039216f);

    public static final Color LIGHTBLUE = new Color(0.6784314f, 0.84705883f, 0.9019608f);

    public static final Color LIGHTCORAL = new Color(0.9411765f, 0.5019608f, 0.5019608f);

    public static final Color LIGHTCYAN = new Color(0.8784314f, 1.0f, 1.0f);

    public static final Color LIGHTGOLDENRODYELLOW = new Color(0.98039216f, 0.98039216f, 0.8235294f);

    public static final Color LIGHTGRAY = new Color(0.827451f, 0.827451f, 0.827451f);

    public static final Color LIGHTGREEN = new Color(0.5647059f, 0.93333334f, 0.5647059f);

    public static final Color LIGHTGREY = LIGHTGRAY;

    public static final Color LIGHTPINK = new Color(1.0f, 0.7137255f, 0.75686276f);

    public static final Color LIGHTSALMON = new Color(1.0f, 0.627451f, 0.47843137f);

    public static final Color LIGHTSEAGREEN = new Color(0.1254902f, 0.69803923f, 0.6666667f);

    public static final Color LIGHTSKYBLUE = new Color(0.5294118f, 0.80784315f, 0.98039216f);

    public static final Color LIGHTSLATEGRAY = new Color(0.46666667f, 0.53333336f, 0.6f);

    public static final Color LIGHTSLATEGREY = LIGHTSLATEGRAY;

    public static final Color LIGHTSTEELBLUE = new Color(0.6901961f, 0.76862746f, 0.87058824f);

    public static final Color LIGHTYELLOW = new Color(1.0f, 1.0f, 0.8784314f);

    public static final Color LIME = new Color(0.0f, 1.0f, 0.0f);

    public static final Color LIMEGREEN = new Color(0.19607843f, 0.8039216f, 0.19607843f);

    public static final Color LINEN = new Color(0.98039216f, 0.9411765f, 0.9019608f);

    public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);

    public static final Color MAROON = new Color(0.5019608f, 0.0f, 0.0f);

    public static final Color MEDIUMAQUAMARINE = new Color(0.4f, 0.8039216f, 0.6666667f);

    public static final Color MEDIUMBLUE = new Color(0.0f, 0.0f, 0.8039216f);

    public static final Color MEDIUMORCHID = new Color(0.7294118f, 0.33333334f, 0.827451f);

    public static final Color MEDIUMPURPLE = new Color(0.5764706f, 0.4392157f, 0.85882354f);

    public static final Color MEDIUMSEAGREEN = new Color(0.23529412f, 0.7019608f, 0.44313726f);

    public static final Color MEDIUMSLATEBLUE = new Color(0.48235294f, 0.40784314f, 0.93333334f);

    public static final Color MEDIUMSPRINGGREEN = new Color(0.0f, 0.98039216f, 0.6039216f);

    public static final Color MEDIUMTURQUOISE = new Color(0.28235295f, 0.81960785f, 0.8f);

    public static final Color MEDIUMVIOLETRED = new Color(0.78039217f, 0.08235294f, 0.52156866f);

    public static final Color MIDNIGHTBLUE = new Color(0.09803922f, 0.09803922f, 0.4392157f);

    public static final Color MINTCREAM = new Color(0.9607843f, 1.0f, 0.98039216f);

    public static final Color MISTYROSE = new Color(1.0f, 0.89411765f, 0.88235295f);

    public static final Color MOCCASIN = new Color(1.0f, 0.89411765f, 0.70980394f);

    public static final Color NAVAJOWHITE = new Color(1.0f, 0.87058824f, 0.6784314f);

    public static final Color NAVY = new Color(0.0f, 0.0f, 0.5019608f);

    public static final Color OLDLACE = new Color(0.99215686f, 0.9607843f, 0.9019608f);

    public static final Color OLIVE = new Color(0.5019608f, 0.5019608f, 0.0f);

    public static final Color OLIVEDRAB = new Color(0.41960785f, 0.5568628f, 0.13725491f);

    public static final Color ORANGE = new Color(1.0f, 0.64705884f, 0.0f);

    public static final Color ORANGERED = new Color(1.0f, 0.27058825f, 0.0f);

    public static final Color ORCHID = new Color(0.85490197f, 0.4392157f, 0.8392157f);

    public static final Color PALEGOLDENROD = new Color(0.93333334f, 0.9098039f, 0.6666667f);

    public static final Color PALEGREEN = new Color(0.59607846f, 0.9843137f, 0.59607846f);

    public static final Color PALETURQUOISE = new Color(0.6862745f, 0.93333334f, 0.93333334f);

    public static final Color PALEVIOLETRED = new Color(0.85882354f, 0.4392157f, 0.5764706f);

    public static final Color PAPAYAWHIP = new Color(1.0f, 0.9372549f, 0.8352941f);

    public static final Color PEACHPUFF = new Color(1.0f, 0.85490197f, 0.7254902f);

    public static final Color PERU = new Color(0.8039216f, 0.52156866f, 0.24705882f);

    public static final Color PINK = new Color(1.0f, 0.7529412f, 0.79607844f);

    public static final Color PLUM = new Color(0.8666667f, 0.627451f, 0.8666667f);

    public static final Color POWDERBLUE = new Color(0.6901961f, 0.8784314f, 0.9019608f);

    public static final Color PURPLE = new Color(0.5019608f, 0.0f, 0.5019608f);

    public static final Color RED = new Color(1.0f, 0.0f, 0.0f);

    public static final Color ROSYBROWN = new Color(0.7372549f, 0.56078434f, 0.56078434f);

    public static final Color ROYALBLUE = new Color(0.25490198f, 0.4117647f, 0.88235295f);

    public static final Color SADDLEBROWN = new Color(0.54509807f, 0.27058825f, 0.07450981f);

    public static final Color SALMON = new Color(0.98039216f, 0.5019608f, 0.44705883f);

    public static final Color SANDYBROWN = new Color(0.95686275f, 0.6431373f, 0.3764706f);

    public static final Color SEAGREEN = new Color(0.18039216f, 0.54509807f, 0.34117648f);

    public static final Color SEASHELL = new Color(1.0f, 0.9607843f, 0.93333334f);

    public static final Color SIENNA = new Color(0.627451f, 0.32156864f, 0.1764706f);

    public static final Color SILVER = new Color(0.7529412f, 0.7529412f, 0.7529412f);

    public static final Color SKYBLUE = new Color(0.5294118f, 0.80784315f, 0.92156863f);

    public static final Color SLATEBLUE = new Color(0.41568628f, 0.3529412f, 0.8039216f);

    public static final Color SLATEGRAY = new Color(0.4392157f, 0.5019608f, 0.5647059f);

    public static final Color SLATEGREY = SLATEGRAY;

    public static final Color SNOW = new Color(1.0f, 0.98039216f, 0.98039216f);

    public static final Color SPRINGGREEN = new Color(0.0f, 1.0f, 0.49803922f);

    public static final Color STEELBLUE = new Color(0.27450982f, 0.50980395f, 0.7058824f);

    public static final Color TAN = new Color(0.8235294f, 0.7058824f, 0.54901963f);

    public static final Color TEAL = new Color(0.0f, 0.5019608f, 0.5019608f);

    public static final Color THISTLE = new Color(0.84705883f, 0.7490196f, 0.84705883f);

    public static final Color TOMATO = new Color(1.0f, 0.3882353f, 0.2784314f);

    public static final Color TURQUOISE = new Color(0.2509804f, 0.8784314f, 0.8156863f);

    public static final Color VIOLET = new Color(0.93333334f, 0.50980395f, 0.93333334f);

    public static final Color WHEAT = new Color(0.9607843f, 0.87058824f, 0.7019608f);

    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);

    public static final Color WHITESMOKE = new Color(0.9607843f, 0.9607843f, 0.9607843f);

    public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f);

    public static final Color YELLOWGREEN = new Color(0.6039216f, 0.8039216f, 0.19607843f);


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
            return NamedColors.get(color);
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

        if (isJsNull(style) || AssistUtils.equals(style, JS_OBJECT)) {
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

        return StringUtils.isEmpty(style) || "none".equals(style) || "undefined".equals(style);

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

    private static final class NamedColors {
        private static final Map<String, Color> namedColors = createNamedColors();

        private NamedColors() {
        }

        private static Color get(String name) {
            return namedColors.get(name);
        }

        private static Map<String, Color> createNamedColors() {
            Map<String, Color> colors = new HashMap<String, Color>(256);

            colors.put("aliceblue", ALICEBLUE);
            colors.put("antiquewhite", ANTIQUEWHITE);
            colors.put("aqua", AQUA);
            colors.put("aquamarine", AQUAMARINE);
            colors.put("azure", AZURE);
            colors.put("beige", BEIGE);
            colors.put("bisque", BISQUE);
            colors.put("black", BLACK);
            colors.put("blanchedalmond", BLANCHEDALMOND);
            colors.put("blue", BLUE);
            colors.put("blueviolet", BLUEVIOLET);
            colors.put("brown", BROWN);
            colors.put("burlywood", BURLYWOOD);
            colors.put("cadetblue", CADETBLUE);
            colors.put("chartreuse", CHARTREUSE);
            colors.put("chocolate", CHOCOLATE);
            colors.put("coral", CORAL);
            colors.put("cornflowerblue", CORNFLOWERBLUE);
            colors.put("cornsilk", CORNSILK);
            colors.put("crimson", CRIMSON);
            colors.put("cyan", CYAN);
            colors.put("darkblue", DARKBLUE);
            colors.put("darkcyan", DARKCYAN);
            colors.put("darkgoldenrod", DARKGOLDENROD);
            colors.put("darkgray", DARKGRAY);
            colors.put("darkgreen", DARKGREEN);
            colors.put("darkgrey", DARKGREY);
            colors.put("darkkhaki", DARKKHAKI);
            colors.put("darkmagenta", DARKMAGENTA);
            colors.put("darkolivegreen", DARKOLIVEGREEN);
            colors.put("darkorange", DARKORANGE);
            colors.put("darkorchid", DARKORCHID);
            colors.put("darkred", DARKRED);
            colors.put("darksalmon", DARKSALMON);
            colors.put("darkseagreen", DARKSEAGREEN);
            colors.put("darkslateblue", DARKSLATEBLUE);
            colors.put("darkslategray", DARKSLATEGRAY);
            colors.put("darkslategrey", DARKSLATEGREY);
            colors.put("darkturquoise", DARKTURQUOISE);
            colors.put("darkviolet", DARKVIOLET);
            colors.put("deeppink", DEEPPINK);
            colors.put("deepskyblue", DEEPSKYBLUE);
            colors.put("dimgray", DIMGRAY);
            colors.put("dimgrey", DIMGREY);
            colors.put("dodgerblue", DODGERBLUE);
            colors.put("firebrick", FIREBRICK);
            colors.put("floralwhite", FLORALWHITE);
            colors.put("forestgreen", FORESTGREEN);
            colors.put("fuchsia", FUCHSIA);
            colors.put("gainsboro", GAINSBORO);
            colors.put("ghostwhite", GHOSTWHITE);
            colors.put("gold", GOLD);
            colors.put("goldenrod", GOLDENROD);
            colors.put("gray", GRAY);
            colors.put("green", GREEN);
            colors.put("greenyellow", GREENYELLOW);
            colors.put("grey", GREY);
            colors.put("honeydew", HONEYDEW);
            colors.put("hotpink", HOTPINK);
            colors.put("indianred", INDIANRED);
            colors.put("indigo", INDIGO);
            colors.put("ivory", IVORY);
            colors.put("khaki", KHAKI);
            colors.put("lavender", LAVENDER);
            colors.put("lavenderblush", LAVENDERBLUSH);
            colors.put("lawngreen", LAWNGREEN);
            colors.put("lemonchiffon", LEMONCHIFFON);
            colors.put("lightblue", LIGHTBLUE);
            colors.put("lightcoral", LIGHTCORAL);
            colors.put("lightcyan", LIGHTCYAN);
            colors.put("lightgoldenrodyellow", LIGHTGOLDENRODYELLOW);
            colors.put("lightgray", LIGHTGRAY);
            colors.put("lightgreen", LIGHTGREEN);
            colors.put("lightgrey", LIGHTGREY);
            colors.put("lightpink", LIGHTPINK);
            colors.put("lightsalmon", LIGHTSALMON);
            colors.put("lightseagreen", LIGHTSEAGREEN);
            colors.put("lightskyblue", LIGHTSKYBLUE);
            colors.put("lightslategray", LIGHTSLATEGRAY);
            colors.put("lightslategrey", LIGHTSLATEGREY);
            colors.put("lightsteelblue", LIGHTSTEELBLUE);
            colors.put("lightyellow", LIGHTYELLOW);
            colors.put("lime", LIME);
            colors.put("limegreen", LIMEGREEN);
            colors.put("linen", LINEN);
            colors.put("magenta", MAGENTA);
            colors.put("maroon", MAROON);
            colors.put("mediumaquamarine", MEDIUMAQUAMARINE);
            colors.put("mediumblue", MEDIUMBLUE);
            colors.put("mediumorchid", MEDIUMORCHID);
            colors.put("mediumpurple", MEDIUMPURPLE);
            colors.put("mediumseagreen", MEDIUMSEAGREEN);
            colors.put("mediumslateblue", MEDIUMSLATEBLUE);
            colors.put("mediumspringgreen", MEDIUMSPRINGGREEN);
            colors.put("mediumturquoise", MEDIUMTURQUOISE);
            colors.put("mediumvioletred", MEDIUMVIOLETRED);
            colors.put("midnightblue", MIDNIGHTBLUE);
            colors.put("mintcream", MINTCREAM);
            colors.put("mistyrose", MISTYROSE);
            colors.put("moccasin", MOCCASIN);
            colors.put("navajowhite", NAVAJOWHITE);
            colors.put("navy", NAVY);
            colors.put("oldlace", OLDLACE);
            colors.put("olive", OLIVE);
            colors.put("olivedrab", OLIVEDRAB);
            colors.put("orange", ORANGE);
            colors.put("orangered", ORANGERED);
            colors.put("orchid", ORCHID);
            colors.put("palegoldenrod", PALEGOLDENROD);
            colors.put("palegreen", PALEGREEN);
            colors.put("paleturquoise", PALETURQUOISE);
            colors.put("palevioletred", PALEVIOLETRED);
            colors.put("papayawhip", PAPAYAWHIP);
            colors.put("peachpuff", PEACHPUFF);
            colors.put("peru", PERU);
            colors.put("pink", PINK);
            colors.put("plum", PLUM);
            colors.put("powderblue", POWDERBLUE);
            colors.put("purple", PURPLE);
            colors.put("red", RED);
            colors.put("rosybrown", ROSYBROWN);
            colors.put("royalblue", ROYALBLUE);
            colors.put("saddlebrown", SADDLEBROWN);
            colors.put("salmon", SALMON);
            colors.put("sandybrown", SANDYBROWN);
            colors.put("seagreen", SEAGREEN);
            colors.put("seashell", SEASHELL);
            colors.put("sienna", SIENNA);
            colors.put("silver", SILVER);
            colors.put("skyblue", SKYBLUE);
            colors.put("slateblue", SLATEBLUE);
            colors.put("slategray", SLATEGRAY);
            colors.put("slategrey", SLATEGREY);
            colors.put("snow", SNOW);
            colors.put("springgreen", SPRINGGREEN);
            colors.put("steelblue", STEELBLUE);
            colors.put("tan", TAN);
            colors.put("teal", TEAL);
            colors.put("thistle", THISTLE);
            colors.put("tomato", TOMATO);
            colors.put("transparent", TRANSPARENT);
            colors.put("turquoise", TURQUOISE);
            colors.put("violet", VIOLET);
            colors.put("wheat", WHEAT);
            colors.put("white", WHITE);
            colors.put("whitesmoke", WHITESMOKE);
            colors.put("yellow", YELLOW);
            colors.put("yellowgreen", YELLOWGREEN);

            return colors;
        }
    }
}
