package com.fr.canvas;

import junit.framework.TestCase;

import java.awt.BasicStroke;

public class LineCapTest extends TestCase {
    public void testParse1() {
        String type = "butt";
        int cap = LineCapAdapter.parse(type);
        assertEquals(BasicStroke.CAP_BUTT, cap);
    }

    public void testParse2() {
        String type = "sss";
        int cap = LineCapAdapter.parse(type);
        assertEquals(ContextAdapter.MISMATCH, cap);
    }

    public void testValueOf1() {
        int join = BasicStroke.CAP_ROUND;
        String type = LineCapAdapter.valueOf(join);
        assertEquals("round", type);
    }

    public void testValueOf2() {
        int join = -1;
        String type = LineCapAdapter.valueOf(join);
        assertEquals("butt", type);
    }
}
