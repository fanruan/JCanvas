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

    public void testFont2String1() {
        Font font = new Font("Times New Roman", Font.ITALIC, 18);
        String s = FontAdapter.font2String(font);
        assertEquals("italic 18 Times New Roman", s);
    }

    public void testFont2String2() {
        Font font = new Font("Times New Roman", Font.BOLD, 11);
        String s = FontAdapter.font2String(font);
        assertEquals("bold 11 Times New Roman", s);
    }

    public void testFont2String3() {
        Font font = new Font("Times New Roman", Font.ITALIC | Font.BOLD, 11);
        String s = FontAdapter.font2String(font);
        assertEquals("bold italic 11 Times New Roman", s);
    }
}
