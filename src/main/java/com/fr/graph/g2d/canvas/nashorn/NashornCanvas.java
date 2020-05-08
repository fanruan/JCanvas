package com.fr.graph.g2d.canvas.nashorn;

import com.fr.graph.g2d.canvas.CanvasAdapter;
import com.fr.graph.g2d.canvas.ContextAdapter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NashornCanvas extends CanvasAdapter {

    private String innerHTML;

    private String _drawn;


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

    public String get_drawn() {
        return _drawn;
    }

    public void set_drawn(String _drawn) {
        this._drawn = _drawn;
    }

    public ContextAdapter getContext(Object... obj) {
        return super.getContext();
    }

    @Override
    public ContextAdapter createContextAdapter(Graphics2D context, BufferedImage canvas) {
        NashornContext nashornContext = new NashornContext(context, canvas);
        nashornContext.setCanvas(this);
        return nashornContext;
    }
}