package com.fr.graph.g2d.canvas;

import java.awt.Color;

public class CanvasUtils {

    private static final double FULL_RADIAN = Math.PI * 2;

    public static double toArc2DAngle(double radian) {

        //0和360是重合的
        if (Double.compare(Math.abs(radian), 0) == 0 || Double.compare(Math.abs(radian), FULL_RADIAN) == 0) {
            return Math.toDegrees(radian);
        }

        radian = radian % FULL_RADIAN;

        radian = radian < 0 ? -radian - FULL_RADIAN : FULL_RADIAN - radian;

        return Math.toDegrees(radian);
    }

    public static double toArc2DLength(double startRadian, double endRadian, boolean counterclockwise) {

        double length = Math.abs(endRadian - startRadian);

        if (Double.compare(length, 0) == 1 && Double.compare(length, FULL_RADIAN) < 0) {

            startRadian = startRadian % FULL_RADIAN;
            endRadian = endRadian % FULL_RADIAN;

            if (startRadian < 0) {
                startRadian += FULL_RADIAN;
            }
            if (endRadian < 0) {
                endRadian += FULL_RADIAN;
            }
            if (Double.compare(startRadian, endRadian) == 0) {
                length = FULL_RADIAN;
            } else {
                length = Math.abs(endRadian - startRadian);

                if (!(endRadian > startRadian ^ counterclockwise)) {
                    length = FULL_RADIAN - length;
                }
            }
        }
        return Math.toDegrees(counterclockwise ? length : -length);
    }

    public static int[] intColorToRGBA(int argb) {
        int a = argb >>> 24;
        int r = argb >> 16 & 255;
        int g = argb >> 8 & 255;
        int b = argb & 255;

        return new int[]{r, g, b, a};
    }

    public static int RGBAToIntColor(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static String color2Hexadecimal(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();
        String stringR = r > 15 ? Integer.toString(r, 16) : "0" + Integer.toString(r, 16);
        String stringG = g > 15 ? Integer.toString(g, 16) : "0" + Integer.toString(g, 16);
        String stringB = b > 15 ? Integer.toString(b, 16) : "0" + Integer.toString(b, 16);
        String stringA = a == 255 ? "" : a > 15 ? Integer.toString(a, 16) : "0" + Integer.toString(a, 16);
        return ("#" + stringR + stringG + stringB + stringA);
    }
}
