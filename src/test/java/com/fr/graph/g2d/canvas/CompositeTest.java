package com.fr.graph.g2d.canvas;

import junit.framework.TestCase;

import java.awt.AlphaComposite;

public class CompositeTest extends TestCase {

    public void testParse1() {
        String type = "source-atop";
        int composite = CompositeAdapter.parse(type);
        assertEquals(AlphaComposite.SRC_ATOP, composite);
    }

    public void testParse2() {
        String type = "sss";
        int composite = CompositeAdapter.parse(type);
        assertEquals(ContextAdapter.MISMATCH, composite);
    }

    public void testValueOf1() {
        int composite = AlphaComposite.SRC_ATOP;
        String type = CompositeAdapter.valueOf(composite);
        assertEquals("source-atop", type);
    }

    public void testValueOf2() {
        int composite = -1;
        String type = CompositeAdapter.valueOf(composite);
        assertEquals("source-over", type);
    }

}
