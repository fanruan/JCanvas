package com.fr.canvas;

import java.util.HashMap;
import java.util.Map;

public enum TextAlignAdapter {

    LEFT,
    RIGHT,
    CENTER;

    public static Map<String, TextAlignAdapter> textAlignMap;

    static {
        textAlignMap = new HashMap<>();
        textAlignMap.put("start", LEFT);
        textAlignMap.put("end", RIGHT);
        textAlignMap.put("left", LEFT);
        textAlignMap.put("right", RIGHT);
        textAlignMap.put("center", CENTER);
    }

    public static TextAlignAdapter get(String textAlign) {
        return textAlignMap.get(textAlign);
    }

}
