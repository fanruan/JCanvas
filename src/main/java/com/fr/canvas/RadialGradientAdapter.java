package com.fr.canvas;

import com.fr.log.FineLoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

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
        try{
            Color c = ColorsAdapter.web(color);
            stops.add(new Stop(offset, c));
        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
        }
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

    public static RadialGradientPaint valueOf(String value) {
        if (value == null) {
            throw new NullPointerException("gradient must be specified");
        }
        String start = "radial-gradient(";
        String end = ")";
        if (!value.startsWith(start) || !value.endsWith(end)) {
            throw new IllegalArgumentException("Invalid linear-gradient specification, "
                    + "must begin with \"" + start + '"' + " and end with \"" + end + '"');
        }
        value = value.substring(start.length(), value.length() - end.length());
        String[] attributes = value.split("\\|");
        float x0 = Float.parseFloat(attributes[0]);
        float y0 = Float.parseFloat(attributes[1]);
        float x1 = Float.parseFloat(attributes[3]);
        float y1 = Float.parseFloat(attributes[4]);
        float r1 = Float.parseFloat(attributes[5]);
        List<Stop> stops = new ArrayList<Stop>();
        for (String s : attributes[6].split(",")) {
            String[] stop = s.split("&");
            stops.add(new Stop(Double.parseDouble(stop[0]), new Color(Integer.parseInt(stop[1]), true)));
        }
        List<Float> fractions = new ArrayList<Float>();
        List<Color> colors = new ArrayList<Color>();
        //将stops转换为LinearGradientPaint的构造函数入参
        Stop.transStops(stops, colors, fractions);
        float[] floats = new float[fractions.size()];
        for (int i = 0; i < fractions.size(); i++) {
            floats[i] = fractions.get(i).floatValue();
        }
        return new RadialGradientPaint(new Point2D.Double(x1, y1), r1, new Point2D.Double(x0, y0), floats, colors.toArray(new Color[colors.size()]), MultipleGradientPaint.CycleMethod.NO_CYCLE);
    }

}
