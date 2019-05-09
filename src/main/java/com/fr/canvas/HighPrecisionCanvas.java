package com.fr.canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class HighPrecisionCanvas extends CanvasAdapter {

    public static int PRECISION = 2; //横纵像素倍数

    public HighPrecisionCanvas() {
        super();
    }

    public HighPrecisionCanvas(int width, int height) {
        super(width * PRECISION, height * PRECISION);
    }

    public int getWidth() {
        return super.getWidth() / PRECISION;
    }

    public int getHeight() {
        return super.getHeight() / PRECISION;
    }

    public void setWidth(int width) {
        super.setWidth(width * PRECISION);
    }

    public void setHeight(int height) {
        super.setHeight(height * PRECISION);
    }

    public void initContextAdapter() {
        Graphics2D context = canvas.createGraphics();
        context.setBackground(ColorsAdapter.TRANSPARENT);
        context.setColor(Color.BLACK); //默认绘图颜色设置为黑色
        context.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //默认的线条样式改变
        context.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f));
        context.scale(PRECISION,PRECISION);
        if (contextAdapter == null) {
            this.contextAdapter = new HighPrecisionContext(context, this.canvas);
        } else {
            this.contextAdapter.reset(context, this.canvas);
        }
    }
}
