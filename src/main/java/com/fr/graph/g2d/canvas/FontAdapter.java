package com.fr.graph.g2d.canvas;

import com.fr.graph.g2d.canvas.constant.CSS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FontAdapter {

    public static final String BOLD = "bold";

    public static final String ITALIC = "italic";

    public static final String OBLIQUE = "oblique";

    public static final int DEFAULT_SIZE = 12;

    public static Map<String, Integer> fontStyle;

    public static Map<String, Font> fontMap = new ConcurrentHashMap<String, Font>();

    public static Set<String> availableFontFamilyNames;

    static {
        fontStyle = new HashMap<String, Integer>();
        fontStyle.put(BOLD, new Integer(Font.BOLD));
        fontStyle.put(OBLIQUE, new Integer(Font.ITALIC));
        fontStyle.put(ITALIC, new Integer(Font.ITALIC));


        //加载可用字体，用英文显示
        String[] familyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.ENGLISH);
        //转换为小写
        for (int i = 0; i < familyNames.length; i++) {
            familyNames[i] = familyNames[i].toLowerCase();
        }
        availableFontFamilyNames = new HashSet<String>
                (Arrays.asList(familyNames));
    }

    public static Font processFont(String font) {
        if (fontMap.containsKey(font)) {
            return fontMap.get(font);
        }
        String[] styleAndRest = font.split("\\s");
        int style = Font.PLAIN;
        int size = DEFAULT_SIZE;
        String name = "DIALOG";
        Font f = null;
        for (int i = 0; i < styleAndRest.length; i++) {
            String rule = styleAndRest[i];
            if (rule.equalsIgnoreCase(ITALIC) || rule.equalsIgnoreCase(OBLIQUE) || rule.equalsIgnoreCase(BOLD)) {
                style = style | fontStyle.get(rule.toLowerCase());
            } else if (isMetricValue(rule) || isNumericValue(rule)) {
                size = (int) (Double.parseDouble(isMetricValue(rule) ? rule.substring(0, rule.length() - 2) : rule) + 0.5);
                size = size < 0 ? DEFAULT_SIZE : size;
                if (i != styleAndRest.length - 1) {
                    StringBuilder s = new StringBuilder();
                    for (int j = i + 1; j < styleAndRest.length; j++) {
                        String rest = styleAndRest[j];
                        rest = rest.replaceAll("\"", "");
                        rest = rest.replaceAll("'", "");
                        s.append(rest).append(" ");
                    }
                    String[] fontNames = s.delete(s.length() - 1, s.length()).toString().split(",");
                    for (String fontName : fontNames) {
                        //手动加载的字体
                        if (CanvasPainter.hasFont(fontName)) {
                            f = CanvasPainter.getFont(fontName);
                            break;
                        } else if (availableFontFamilyNames.contains(fontName.toLowerCase())) {
                            name = fontName;
                            break;
                        }
                    }
                }
                break;
            }
        }
        if (f == null) {
            f = new Font(name, style, size);
        } else {
            f = f.deriveFont(style, size);
        }
        fontMap.put(font, f);
        return f;
    }

    public static String font2String(Font font) {
        StringBuilder builder = new StringBuilder();
        builder.append((font.getStyle() & Font.BOLD) > 0 ? "bold " : "")
                .append((font.getStyle() & Font.ITALIC) > 0 ? "italic " : "")
                .append(font.getSize()).append(" ")
                .append(font.getFamily());
        return builder.toString();
    }

    public static boolean isMetricValue(final String value) {
        return value.contains(CSS.Value.PX) || value.contains(CSS.Value.IN) || value.contains(CSS.Value.CM)
                || value.contains(CSS.Value.MM) || value.contains(CSS.Value.PC) || value.contains(CSS.Value.PT);
    }

    public static boolean isNumericValue(final String value) {
        return value.matches("^-?\\d\\d*\\.\\d*$") || value.matches("^-?\\d\\d*$") || value.matches("^-?\\.\\d\\d*$");
    }
}