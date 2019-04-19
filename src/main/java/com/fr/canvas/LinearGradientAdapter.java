package com.fr.canvas;

import java.util.ArrayList;
import java.util.List;

public class LinearGradientAdapter {

    private double x0, y0, x1, y1;

    private List<Stop> stops;

    public LinearGradientAdapter(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        stops = new ArrayList<Stop>();
    }

    public void addColorStop(double offset, String color) {
        stops.add(new Stop(offset, ColorsAdapter.web(color)));
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("linear-gradient(")
                .append(x0).append("|")
                .append(y0).append("|")
                .append(x1).append("|")
                .append(y1).append("|");
        stops = Stop.normalize(stops);
        for(Stop stop : stops){
            s.append(stop).append(",");
        }
        s.delete(s.length() - 1, s.length());
        s.append(")");
        return s.toString();
    }
}
