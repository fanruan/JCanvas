package com.fr.canvas;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;

public class StopTest extends TestCase {

    public void testNormalize1() {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(0.5, Color.WHITE));
        stops = Stop.normalize(stops);
        assertEquals(stops.size(), 2);
        assertEquals(new Stop(0.0, Color.WHITE), stops.get(0));
        assertEquals(new Stop(1.0, Color.WHITE), stops.get(1));
    }

    public void testNormalize2() {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(0.5, Color.WHITE));
        stops.add(new Stop(0.7, Color.BLACK));
        stops.add(new Stop(0.8, null));
        stops.add(new Stop(1.8, Color.WHITE));
        stops.add(new Stop(-0.8, Color.WHITE));
        stops = Stop.normalize(stops);
        assertEquals(2, stops.size());
        assertEquals(new Stop(0.5, Color.WHITE), stops.get(0));
        assertEquals(new Stop(0.7, Color.BLACK), stops.get(1));
    }

    public void testNormalize3() {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(0.7, Color.BLACK));
        stops.add(new Stop(0.5, Color.WHITE));
        stops.add(new Stop(0.7, Color.RED));
        stops.add(new Stop(0.7, Color.BLUE));
        stops = Stop.normalize(stops);
        assertEquals(3, stops.size());
        assertEquals(new Stop(0.5, Color.WHITE), stops.get(0));
        assertEquals(new Stop(0.7, Color.BLACK), stops.get(1));
        assertEquals(new Stop(0.7, Color.BLUE), stops.get(2));
    }

    public void testNormalize4() {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(0.0, Color.BLACK));
        stops.add(new Stop(0.0, Color.WHITE));
        stops.add(new Stop(1.0, Color.RED));
        stops.add(new Stop(1.0, Color.BLUE));
        stops = Stop.normalize(stops);
        assertEquals(2, stops.size());
        assertEquals(new Stop(0.0, Color.WHITE), stops.get(0));
        assertEquals(new Stop(1.0, Color.RED), stops.get(1));
    }

    public void testNormalize5() {
        List<Stop> stops = new ArrayList<>();
        stops = Stop.normalize(stops);
        assertEquals(2, stops.size());
        assertEquals(new Stop(0.0, ColorsAdapter.TRANSPARENT), stops.get(0));
        assertEquals(new Stop(1.0, ColorsAdapter.TRANSPARENT), stops.get(1));
    }

    public void testTransStops() {
        List<Stop> stops = new ArrayList<>();
        stops.add(new Stop(0.5, Color.WHITE));
        stops.add(new Stop(0.7, Color.RED));
        stops.add(new Stop(0.7, Color.BLACK));
        stops.add(new Stop(0.7, Color.BLUE));
        List<Color> colors = new ArrayList<>();
        List<Float> fractions = new ArrayList<>();
        Stop.transStops(stops, colors, fractions);
        assertTrue(Arrays.equals(new Color[]{Color.WHITE, Color.RED, Color.BLUE}, colors.toArray()));
        assertTrue(Arrays.equals(new Float[]{0.5f, 0.7f, 0.700001f}, fractions.toArray()));
    }
}
