package com.fr.canvas;

import junit.framework.TestCase;

public class ImageTest extends TestCase {

    public void testToString() {
        int width = 300;
        int height = 150;
        int[] data = new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 100};
        ImageData image = new ImageData(width, height, data);
        assertEquals("ImageData(300|150|11,22,33,44,55,66,77,88,99,100)", image.toString());
    }

    public void testValueOf() {
        int width = 300;
        int height = 150;
        int[] data = new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 100};
        ImageData image = new ImageData(width, height, data);
        assertEquals(ImageData.valueOf("ImageData(300|150|11,22,33,44,55,66,77,88,99,100)"), image);
    }
}
