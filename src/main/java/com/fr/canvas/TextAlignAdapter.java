package com.fr.canvas;

import java.util.HashMap;
import java.util.Map;

public enum TextAlignAdapter {

    LEFT("start"),
    RIGHT("end"),
    CENTER("center");

    private String textAlign;

    TextAlignAdapter(String textAlign){
        this.textAlign = textAlign;
    }

    public String getTextAlign(){
        return textAlign;
    }

    public static Map<String, TextAlignAdapter> textAlignMap;

    static {
        textAlignMap = new HashMap<String,TextAlignAdapter>();
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
