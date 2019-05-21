package com.fr.graph.g2d.canvas;

import junit.framework.TestCase;

import java.awt.BasicStroke;

public class LineJoinTest extends TestCase {

    public void testParse1() {
        String type = "bevel";
        int join = LineJoinAdapter.parse(type);
        assertEquals(BasicStroke.JOIN_BEVEL, join);
    }

    public void testParse2() {
        String type = "sss";
        int join = LineJoinAdapter.parse(type);
        assertEquals(ContextAdapter.MISMATCH, join);
    }

    public void testValueOf1() {
        int join = BasicStroke.JOIN_BEVEL;
        String type = LineJoinAdapter.valueOf(join);
        assertEquals("bevel", type);
    }

    public void testValueOf2() {
        int join = -1;
        String type = LineJoinAdapter.valueOf(join);
        assertEquals("miter", type);
    }
}
