package com.fr.canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stop {

    public static final List<Stop> NO_STOP =
            Arrays.asList(new Stop(0.0, ColorsAdapter.TRANSPARENT),
                    new Stop(1.0, ColorsAdapter.TRANSPARENT));

    private Color color;

    private double offset;

    public Stop(double offset, Color color) {
        this.offset = offset;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public double getOffset() {
        return offset;
    }

    static List<Stop> normalize(List<Stop> stops) {
        if (stops == null) {
            return NO_STOP;
        }
        Stop zeroStop = null;
        Stop oneStop = null;
        List<Stop> newList = new ArrayList<Stop>(stops.size());
        for (Stop s : stops) {
            if (s == null || s.getColor() == null) continue;
            double off = s.getOffset();
            if (off < 0.0 || off > 1.0) {
                continue;
            } else {
                for (int i = newList.size() - 1; i >= 0; i--) {
                    Stop s2 = newList.get(i);
                    if (s2.getOffset() <= off) {
                        if (s2.getOffset() == off) {
                            if (i > 0 && newList.get(i - 1).getOffset() == off) {
                                newList.set(i, s);
                            } else {
                                newList.add(i + 1, s);
                            }
                        } else {
                            newList.add(i + 1, s);
                        }
                        s = null;
                        break;
                    }
                }
                if (s != null) {
                    newList.add(0, s);
                }
            }
        }
        if (newList.isEmpty()) {
            return NO_STOP;
        } else if (newList.size() == 1) {
            return Arrays.asList(new Stop(0.0, newList.get(0).color),
                    new Stop(1.0, newList.get(0).color));
        }
        return newList;
    }

    @Override
    public String toString(){
        return offset + "&" + color.getRGB();
    }
}
