package com.fr.canvas;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    public void testToArc2DAngle1() {
        double radian = 0;
        double angle = CanvasUtils.toArc2DAngle(radian);
        assertEquals(0.0, angle);
    }

    public void testToArc2DAngle2() {
        double radian = 2 * Math.PI;
        double angle = CanvasUtils.toArc2DAngle(radian);
        assertEquals(360.0, angle);
    }

    public void testToArc2DAngle3() {
        double radian = 2.5 * Math.PI;
        double angle = CanvasUtils.toArc2DAngle(radian);
        assertEquals(270.0, angle);
    }

    public void testToArc2DAngle4() {
        double radian = -2.5 * Math.PI;
        double angle = CanvasUtils.toArc2DAngle(radian);
        assertEquals(-270.0, angle);
    }

    public void testToArc2DLength1() {
        double startRadian = 1.5 * Math.PI;
        double endRadian = 1.5 * Math.PI;
        boolean counterclockwise = true;
        double length = CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise);
        assertEquals(0.0, length);
    }

    public void testToArc2DLength2() {
        double startRadian = -0.5 * Math.PI;
        double endRadian = 2.5 * Math.PI;
        boolean counterclockwise = true;
        double length = CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise);
        assertEquals(540.0, length);
    }

    public void testToArc2DLength3() {
        double startRadian = -0.5 * Math.PI;
        double endRadian = 1.0 * Math.PI;
        boolean counterclockwise = false;
        double length = CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise);
        assertEquals(-270.0, length);
    }

    public void testToArc2DLength4() {
        double startRadian = -0.5 * Math.PI;
        double endRadian = 1.0 * Math.PI;
        boolean counterclockwise = true;
        double length = CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise);
        assertEquals(90.0, length);
    }

    public void testToArc2DLength5() {
        double startRadian = 0.5 * Math.PI;
        double endRadian = 1.0 * Math.PI;
        boolean counterclockwise = true;
        double length = CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise);
        assertEquals(270.0, length);
    }

    public void testToArc2DLength6() {
        double startRadian = 0.5 * Math.PI;
        double endRadian = 1.0 * Math.PI;
        boolean counterclockwise = false;
        double length = CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise);
        assertEquals(-90.0, length);
    }

    public void testIntColorToRGBA1() {
        int color = 0;
        int[] rgba = CanvasUtils.intColorToRGBA(color);
        assertEquals(0, rgba[0]);
        assertEquals(0, rgba[1]);
        assertEquals(0, rgba[2]);
        assertEquals(0, rgba[3]);
    }

    public void testIntColorToRGBA2() {
        int color = 0xff00ff00;
        int[] rgba = CanvasUtils.intColorToRGBA(color);
        assertEquals(0, rgba[0]);
        assertEquals(255, rgba[1]);
        assertEquals(0, rgba[2]);
        assertEquals(255, rgba[3]);
    }
}
