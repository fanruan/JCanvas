package com.fr.graph.g2d.canvas.nashorn;

import com.fr.graph.g2d.canvas.CanvasAdapter;
import com.fr.graph.g2d.canvas.ContextAdapter;
import com.fr.graph.g2d.canvas.ImageUtils;
import com.fr.graph.g2d.canvas.TextMetrics;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

public class NashornContext extends ContextAdapter {

    public static final String UNDEFINED = "undefined";

    double dpr;

    public NashornContext(Graphics2D context, BufferedImage canvas) {
        super(context, canvas);
    }

    public double getDpr() {
        return dpr;
    }

    public void setDpr(double dpr) {
        this.dpr = dpr;
    }

    public void arc(double x, double y, double r, double startRadian, double endRadian, Object counterclockwise) {
        boolean anticlockwise = false;
        if (counterclockwise instanceof Integer) {
            anticlockwise = (Integer) counterclockwise != 0;
        } else if (counterclockwise instanceof Double) {
            anticlockwise = (Double) counterclockwise != 0;
        } else if (counterclockwise instanceof String) {
            anticlockwise = !"".equals(counterclockwise.toString());
        } else if (counterclockwise != null && !UNDEFINED.equalsIgnoreCase(counterclockwise.toString())) {
            anticlockwise = true;
        }
        super.arc(x, y, r, startRadian, endRadian, anticlockwise);
    }

    public TextMetrics measureText(Object text) {
        if (text == null) {
            return super.measureText("null");
        } else {
            return super.measureText(text.toString());
        }
    }

    public void setFillStyle(Object fillStyle) {
        if (fillStyle == null) {
            return;
        }
        if (fillStyle instanceof Paint) {
            setFillPaint((Paint) fillStyle);
        } else {
            setFillStyle(fillStyle.toString());
        }
    }

    public void setStrokeStyle(Object strokeStyle) {
        if (strokeStyle == null) {
            return;
        }
        if (strokeStyle instanceof Paint) {
            setStrokePaint((Paint) strokeStyle);
        } else {
            setStrokeStyle(strokeStyle.toString());
        }
    }

    public void drawImage(CanvasAdapter img, int x, int y) {
        scale(1.0 / CanvasAdapter.RESOLUTION, 1.0 / CanvasAdapter.RESOLUTION);
        drawImage(img.getCanvas(), x * CanvasAdapter.RESOLUTION, y * CanvasAdapter.RESOLUTION);
        scale(CanvasAdapter.RESOLUTION, CanvasAdapter.RESOLUTION);
    }


    public void drawImage(CanvasAdapter img, int x, int y, int width, int height) {
        drawImage(img.getCanvas(), x, y, width, height);
    }

    public void drawImage(CanvasAdapter img, int sx, int sy, int sWidth, int sHeight, int x, int y, int width, int height) {
        drawImage(img.getCanvas(), sx, sy, sWidth, sHeight, x, y, width, height, CanvasAdapter.RESOLUTION);
    }

    public void drawImage(String url, int x, int y) {
        drawImage(ImageUtils.getOrCreate(url), x * CanvasAdapter.RESOLUTION, y * CanvasAdapter.RESOLUTION);
    }


    public void drawImage(String url, int x, int y, int width, int height) {
        drawImage(ImageUtils.getOrCreate(url), x, y, width, height);
    }

    public void drawImage(String url, int sx, int sy, int sWidth, int sHeight, int x, int y, int width, int height) {
        drawImage(ImageUtils.getOrCreate(url), sx, sy, sWidth, sHeight, x, y, width, height, 1);
    }
}