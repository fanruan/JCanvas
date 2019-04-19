package com.fr.canvas;


import java.awt.*;
import java.awt.image.BufferedImage;

public class CanvasAdapter {

    private BufferedImage canvas;

    private ContextAdapter contextAdapter;

    public CanvasAdapter() {
        this(200, 200);
    }

    public CanvasAdapter(double width, double height) {
        this.canvas = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
    }

    public double getWidth() {
        return canvas.getWidth();
    }

    public double getHeight() {
        return canvas.getHeight();
    }

    public void setWidth(double width) {
        int height = canvas.getHeight();
        canvas = new BufferedImage((int) width, height, BufferedImage.TYPE_INT_RGB);
        initContextAdapter();
    }

    public void setHeight(double height) {
        int width = canvas.getWidth();
        canvas = new BufferedImage(width, (int) height, BufferedImage.TYPE_INT_RGB);
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
        Graphics2D context = (Graphics2D) canvas.getGraphics();
        context.setBackground(new Color(0,0,0,0));
        context.setColor(Color.BLACK); //默认绘图颜色设置为黑色
        //默认的线条样式改变
        context.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
        this.contextAdapter = new ContextAdapter(context, this.canvas);
    }
}
