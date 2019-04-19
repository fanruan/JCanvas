package com.fr.canvas;

import java.util.ArrayList;
import java.util.List;

public class RadialGradientAdapter {
    private double x0, y0, r0, x1, y1, r1;

    private List<Stop> stops;

    public RadialGradientAdapter(double x0, double y0, double r0, double x1, double y1, double r1) {
        this.x0 = x0;
        this.y0 = y0;
        this.r0 = r0;
        this.x1 = x1;
        this.y1 = y1;
        this.r1 = r1;
        stops = new ArrayList<Stop>();
    }

    public void addColorStop(double offset, String color) {
        stops.add(new Stop(offset, ColorsAdapter.web(color)));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("radial-gradient(")
                .append(x0).append("|")
                .append(y0).append("|")
                .append(r0).append("|")
                .append(x1).append("|")
                .append(y1).append("|")
                .append(r1).append("|");
        stops = Stop.normalize(stops);
        for (Stop stop : stops) {
            s.append(stop).append(",");
        }
        s.delete(s.length() - 1, s.length());
        s.append(")");
        return s.toString();
    }
}
