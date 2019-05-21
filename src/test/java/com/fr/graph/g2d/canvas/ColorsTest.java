package com.fr.graph.g2d.canvas;

import junit.framework.TestCase;

import java.awt.Color;

public class ColorsTest extends TestCase {

    public void testWeb1() {
        String s = "#ff00ff";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(255, 0, 255), color);
    }

    public void testWeb2() {
        String s = "#ff00ffcc";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(255, 0, 255, 204), color);
    }

    public void testWeb3() {
        String s = "rgb(40,70,179)";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(40, 70, 179), color);
    }

    public void testWeb4() {
        String s = "rgba(180,67,149,0.8)";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(180, 67, 149, 204), color);
    }

    public void testWeb5() {
        String s = "hsl(120,65%,75%)";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(149, 232, 149), color);
    }

    public void testWeb6() {
        String s = "hsl(120,65%,75%,0.8)";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(149, 232, 149, 204), color);
    }

    public void testWeb7() {
        String s = "red";
        Color color = ColorsAdapter.web(s);
        assertEquals(new Color(255, 0, 0), color);
    }
}
