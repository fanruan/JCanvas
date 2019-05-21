package com.fr.graph.g2d.canvas;

import java.util.HashMap;
import java.util.Map;

public enum TextBaselineAdapter {

    ALPHABETIC("alphabetic"),
    TOP("top"),
    BOTTOM("bottom"),
    CENTER("middle");

    private String textBaseline;

    TextBaselineAdapter(String textBaseline){
        this.textBaseline = textBaseline;
    }

    public String getTextBaseline(){
        return textBaseline;
    }

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
