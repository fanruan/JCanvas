package com.fr.graph.g2d.canvas;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class CanvasAdapter {

    public static int RESOLUTION = 2; //横纵像素倍数

    private BufferedImage canvas;

    private ContextAdapter contextAdapter;

    public CanvasAdapter() {
        this(5, 5);
    }

    public CanvasAdapter(int width, int height) {
        this.canvas = new BufferedImage(width * RESOLUTION, height * RESOLUTION, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getCanvas() {
        return this.canvas;
    }

    public int getWidth() {
        return canvas.getWidth() / RESOLUTION;
    }

    public int getHeight() {
        return canvas.getHeight() / RESOLUTION;
    }

    public void setAttribute(String attribute, int value) {
        if ("width".equals(attribute)) {
            setWidth(value);
        } else if ("height".equals(attribute)) {
            setHeight(value);
        }
    }

    public void setWidth(int width) {
        int height = canvas.getHeight();
        this.canvas = new BufferedImage(width * RESOLUTION, height, BufferedImage.TYPE_INT_ARGB);
        initContextAdapter();
    }

    public void setHeight(int height) {
        int width = canvas.getWidth();
        this.canvas = new BufferedImage(width, height * RESOLUTION, BufferedImage.TYPE_INT_ARGB);
        initContextAdapter();
    }

    public ContextAdapter getContext() {
        if (this.contextAdapter == null) {
            initContextAdapter();
        }
        return this.contextAdapter;
    }

    public void initContextAdapter() {
        Graphics2D context = canvas.createGraphics();
        context.setBackground(ColorsAdapter.TRANSPARENT);
        context.setColor(Color.BLACK); //默认绘图颜色设置为黑色
        context.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //默认的线条样式改变
        context.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
        context.scale(RESOLUTION, RESOLUTION);
        if (contextAdapter == null) {
            this.contextAdapter = createContextAdapter(context, canvas);
        } else {
            this.contextAdapter.reset(context, this.canvas);
        }
    }

    public ContextAdapter createContextAdapter(Graphics2D context, BufferedImage canvas) {
        return new ContextAdapter(context, canvas);
    }

    public void dispose() {
        this.canvas = null;
        if (this.contextAdapter != null) {
            this.contextAdapter.dispose();
        }
    }
}
