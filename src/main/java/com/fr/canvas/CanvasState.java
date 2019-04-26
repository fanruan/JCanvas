package com.fr.canvas;

import java.awt.Composite;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

public class CanvasState {

    private Paint fillPaint;

    private Paint strokePaint;

    private TextAlignAdapter textAlign;

    private TextBaselineAdapter textBaseline;

    private Shape clip;

    private Stroke stroke;

    private AffineTransform transform;

    private Composite composite;

    private Font font;

    public CanvasState(Paint fillPaint, Paint strokePaint, TextAlignAdapter textAlign, TextBaselineAdapter textBaseline,
                       Shape clip, Stroke stroke, AffineTransform transform, Composite composite, Font font) {
        this.fillPaint = fillPaint;
        this.strokePaint = strokePaint;
        this.textAlign = textAlign;
        this.textBaseline = textBaseline;
        this.clip = clip;
        this.stroke = stroke;
        this.transform = transform;
        this.composite = composite;
        this.font = font;
    }

    public Paint getFillPaint() {
        return fillPaint;
    }

    public void setFillPaint(Paint fillPaint) {
        this.fillPaint = fillPaint;
    }

    public Paint getStrokePaint() {
        return strokePaint;
    }

    public void setStrokePaint(Paint strokePaint) {
        this.strokePaint = strokePaint;
    }

    public TextAlignAdapter getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlignAdapter textAlign) {
        this.textAlign = textAlign;
    }

    public TextBaselineAdapter getTextBaseline() {
        return textBaseline;
    }

    public void setTextBaseline(TextBaselineAdapter textBaseline) {
        this.textBaseline = textBaseline;
    }

    public Shape getClip() {
        return clip;
    }

    public void setClip(Shape clip) {
        this.clip = clip;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public void setTransform(AffineTransform transform) {
        this.transform = transform;
    }

    public Composite getComposite() {
        return composite;
    }

    public void setComposite(Composite composite) {
        this.composite = composite;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
