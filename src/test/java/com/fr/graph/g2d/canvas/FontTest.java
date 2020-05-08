package com.fr.graph.g2d.canvas;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FontTest extends TestCase {

    String[] forName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); //返回包含在此所有字体系列名称的数组， GraphicsEnvironment本地化为默认的语言环境，如返回 Locale.getDefault()

    public void testProcessFont1() {

        String font = "29px " + forName[0];
        Font f = FontAdapter.processFont(font);
        assertEquals(forName[0], f.getFamily());
        assertEquals(29, f.getSize());
    }

    public void testProcessFont2() {
        String font = "bold italic -29px " + forName[0];
        Font f = FontAdapter.processFont(font);
        assertEquals(forName[0], f.getFamily());
        assertEquals(12, f.getSize());
        assertEquals(Font.ITALIC | Font.BOLD, f.getStyle());
    }

    public void testProcessFont3() {
        String font = "29px " + "aaa," + forName[0] + "," + forName[1];
        Font f = FontAdapter.processFont(font);
        assertEquals(forName[0], f.getFamily());
        assertEquals(29, f.getSize());
    }

    public void testFont2String1() {
        Font font = new Font(forName[0], Font.ITALIC, 18);
        String s = FontAdapter.font2String(font);
        assertEquals("italic 18 " + forName[0], s);
    }

    public void testFont2String2() {
        Font font = new Font(forName[0], Font.BOLD, 11);
        String s = FontAdapter.font2String(font);
        assertEquals("bold 11 " + forName[0], s);
    }

    public void testFont2String3() {
        Font font = new Font(forName[0], Font.ITALIC | Font.BOLD, 11);
        String s = FontAdapter.font2String(font);
        assertEquals("bold italic 11 " + forName[0], s);
    }

    public void testLoadFont() {
        File file = new File(this.getClass().getResource("/font/Helvetica.ttf").getPath());
        CanvasPainter.loadFont("font1", file);
        String font = "bold italic 29px font1";
        Font f = FontAdapter.processFont(font);
        assertEquals("Helvetica", f.getFamily());
        assertEquals(29, f.getSize());
        assertEquals(Font.ITALIC | Font.BOLD, f.getStyle());
    }

    public void testLoadFontByInputStream() {
        File file = new File(this.getClass().getResource("/font/Helvetica.ttf").getPath());
        try {
            InputStream fileInput = new FileInputStream(file);
            CanvasPainter.loadFont("font1", fileInput);
            fileInput.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        String font = "bold italic 29px font1";
        Font f = FontAdapter.processFont(font);
        assertEquals("Helvetica", f.getFamily());
        assertEquals(29, f.getSize());
        assertEquals(Font.ITALIC | Font.BOLD, f.getStyle());
    }
}