package com.fr.canvas;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class CanvasAdapter {

    private BufferedImage canvas;

    private ContextAdapter contextAdapter;

    public CanvasAdapter() {
        this(200, 200);
    }

    public CanvasAdapter(int width, int height) {
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public double getWidth() {
        return canvas.getWidth();
    }

    public double getHeight() {
        return canvas.getHeight();
    }

    public void setWidth(int width) {
        int height = canvas.getHeight();
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        initContextAdapter();
    }

    public void setHeight(int height) {
        int width = canvas.getWidth();
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        initContextAdapter();
    }

    public ContextAdapter getContext(String type) {
        return getContext();
    }

    public ContextAdapter getContext(String type, Object para) {
        return getContext();
    }

    public ContextAdapter getContext() {
        if (this.contextAdapter == null) {
            initContextAdapter();
        }
        return this.contextAdapter;
    }

    public void initContextAdapter() {
        java.awt.Graphics2D context = (java.awt.Graphics2D) canvas.getGraphics();
        context.setBackground(ColorsAdapter.TRANSPARENT);
        context.setColor(Color.BLACK); //默认绘图颜色设置为黑色
        context.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        //默认的线条样式改变
        context.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
        this.contextAdapter = new ContextAdapter(context, this.canvas);
    }
}
