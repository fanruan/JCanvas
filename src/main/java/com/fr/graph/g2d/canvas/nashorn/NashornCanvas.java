package com.fr.graph.g2d.canvas.nashorn;

import com.fr.graph.g2d.canvas.CanvasAdapter;
import com.fr.graph.g2d.canvas.ContextAdapter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NashornCanvas extends CanvasAdapter {

    private String innerHTML;


    public NashornCanvas() {
        super();
    }

    public NashornCanvas(int width, int height) {
        super(width, height);
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public ContextAdapter getContext(Object... obj) {
        return super.getContext();
    }

    @Override
    public ContextAdapter createContextAdapter(Graphics2D context, BufferedImage canvas) {
        return new NashornContext(context, canvas);
    }
}
