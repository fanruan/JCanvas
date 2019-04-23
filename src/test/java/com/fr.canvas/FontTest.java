package com.fr.canvas;

import junit.framework.TestCase;

import java.awt.Font;

public class FontTest extends TestCase {

    public void testProcessFont1() {
        String font = "29px Times New Roman";
        Font f = FontAdapter.processFont(font);
        assertEquals("Times New Roman", f.getFamily());
        assertEquals(29, f.getSize());
    }

    public void testProcessFont2() {
        String font = "bold italic -29px Times New Roman";
        Font f = FontAdapter.processFont(font);
        assertEquals("Times New Roman", f.getFamily());
        assertEquals(12, f.getSize());
        assertEquals(Font.ITALIC | Font.BOLD, f.getStyle());
    }
}
