package com.fr.canvas;

import java.util.HashMap;
import java.util.Map;

public enum TextBaselineAdapter {

    ALPHABETIC,
    TOP,
    BOTTOM,
    CENTER;

    public static Map<String, TextBaselineAdapter> textBaselineMap;

    static {
        textBaselineMap = new HashMap<String,TextBaselineAdapter>();
        textBaselineMap.put("alphabetic", ALPHABETIC);
        textBaselineMap.put("top", TOP);
        textBaselineMap.put("hanging", TOP);
        textBaselineMap.put("ideographic", BOTTOM);
        textBaselineMap.put("bottom", BOTTOM);
        textBaselineMap.put("middle", CENTER);
    }

    public static TextBaselineAdapter get(String textBaseline) {
        return textBaselineMap.get(textBaseline);
    }
}
