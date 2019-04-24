package com.fr.canvas;


import java.awt.BasicStroke;

public enum LineJoinAdapter {

    BEVEL("bevel", BasicStroke.JOIN_BEVEL),
    ROUND("round", BasicStroke.JOIN_ROUND),
    MITER("miter", BasicStroke.JOIN_MITER);

    private String type;

    private int join;

    LineJoinAdapter(String type, int join){
        this.type = type;
        this.join = join;
    }

    public String getType() {
        return this.type;
    }

    private static LineJoinAdapter[] types;

    public static int parse(String type){
        if(types == null){
            types = LineJoinAdapter.values();
        }

        for(LineJoinAdapter lineJoin : types){
            if(lineJoin.getType().equals(type)){
                return lineJoin.join;
            }
        }

        return ContextAdapter.MISMATCH;
    }
}
