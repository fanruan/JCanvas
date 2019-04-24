package com.fr.canvas;


import java.awt.BasicStroke;

public enum LineCapAdapter {

    BUTT("butt", BasicStroke.CAP_BUTT),
    SQUARE("square", BasicStroke.CAP_SQUARE),
    ROUND("round", BasicStroke.CAP_ROUND);

    private String type;

    private int capType;

    LineCapAdapter(String type, int capType){
        this.type = type;
        this.capType = capType;
    }

    public String getType() {
        return this.type;
    }

    private static LineCapAdapter[] types;

    public static int parse(String type){
        if(types == null){
            types = LineCapAdapter.values();
        }
        for(LineCapAdapter lineCap : types){
            if(lineCap.getType().equals(type)){
                return lineCap.capType;
            }
        }
        return ContextAdapter.MISMATCH;
    }
}
