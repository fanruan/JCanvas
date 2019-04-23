package com.fr.canvas;

import com.fr.stable.StringUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ContextAdapter {

    public static final int MISMATCH = -1;

    private Graphics2D context;

    private BufferedImage canvas;

    private Path2D path;

    private boolean moved;

    private Paint fillPaint;

    private Paint strokePaint;

    private TextAlignAdapter textAlign;

    private TextBaselineAdapter textBaseline;

    public ContextAdapter(Graphics2D context, BufferedImage canvas) {
        this.canvas = canvas;
        this.context = context;
        this.fillPaint = Color.BLACK;
        this.strokePaint = Color.BLACK;
        path = new Path2D.Double();
    }

    public void setFillStyle(String fillStyle) {
        setStyle(fillStyle, true);
    }

    public void setStrokeStyle(String strokeStyle) {
        setStyle(strokeStyle, false);
    }

    private void setStyle(String style, boolean isFill) {
        Paint paint = ColorsAdapter.parsePaint(style);
        if (paint != null) {
            if (isFill) {
                fillPaint = paint;
            } else {
                strokePaint = paint;
            }
        }
    }

    public LinearGradientAdapter createLinearGradient(double x0, double y0, double x1, double y1) {
        return new LinearGradientAdapter(x0, y0, x1, y1);
    }

    public RadialGradientAdapter createRadialGradient(double x0, double y0, double r0, double x1, double y1, double r1) {
        return new RadialGradientAdapter(x0, y0, r0, x1, y1, r1);
    }

    public void moveTo(double x, double y) {
        path.moveTo(x, y);
        moved = true;
    }

    public void lineTo(double x, double y) {
        if (!moved) {
            moveTo(x, y);
        }
        path.lineTo(x, y);
    }

    public void rect(double x, double y, double w, double h) {
        moveTo(x, y);
        path.lineTo(x + w, y);
        path.lineTo(x + w, y + h);
        path.lineTo(x, y + h);
        path.closePath();
    }

    public void strokeRect(double x, double y, double w, double h) {
        beforeStroke();
        Rectangle2D rec = new Rectangle2D.Double(x, y, w, h);
        context.draw(rec);
    }

    public void fillRect(double x, double y, double w, double h) {
        beforeFill();
        Rectangle2D rec = new Rectangle2D.Double(x, y, w, h);
        context.fill(rec);
    }

    public void clearRect(double x, double y, double w, double h) {
        Rectangle2D rec = new Rectangle2D.Double(x, y, w, h);
        Composite oldComposite = context.getComposite();
        Paint oldPaint = context.getPaint();
        context.setComposite(AlphaComposite.Src);
        context.setColor(context.getBackground());
        context.fill(rec);
        context.setPaint(oldPaint);
        context.setComposite(oldComposite);
    }

    public void arc(double x, double y, double r, double startRadian, double endRadian) {
        arc(x, y, r, startRadian, endRadian, false);
    }

    public void arc(double x, double y, double r, double startRadian, double endRadian, boolean counterclockwise) {
        Arc2D arc = new Arc2D.Double(x - r, y - r, 2 * r, 2 * r, CanvasUtils.toArc2DAngle(startRadian), CanvasUtils.toArc2DLength(startRadian, endRadian, counterclockwise), Arc2D.OPEN);
        path.append(arc, true);
        moved = true;
    }

    public void quadraticCurveTo(double xc, double yc, double x1, double y1) {
        if (!moved) {
            moveTo(xc, yc);
        }
        path.quadTo(xc, yc, x1, y1);
    }

    public void bezierCurveTo(double xc1, double yc1, double xc2, double yc2, double x1, double y1) {
        if (!moved) {
            moveTo(xc1, yc1);
        }
        path.curveTo(xc1, yc1, xc2, yc2, x1, y1);
    }

    public void closePath() {
        if (moved) {
            path.closePath();
        }
    }

    public boolean isPointInPath(double x, double y) {
        return path.contains(x, y);
    }

    //重置路径点，并清空之前的指令集合
    public void beginPath() {
        path.reset();
        moved = false;
    }

    public void stroke() {
        if (moved) {
            beforeStroke();
            context.draw(path);
        }
    }

    public void fill() {
        if (moved) {
            beforeFill();
            context.fill(path);
        }
    }

    public void clip() {
        context.clip(path);
    }

    public void setLineCap(String lineCap) {
        int cap = LineCapAdapter.parse(lineCap);
        if (cap != MISMATCH) {
            BasicStroke stroke = (BasicStroke) context.getStroke();
            context.setStroke(new BasicStroke(stroke.getLineWidth(), cap, stroke.getLineJoin(), stroke.getMiterLimit()));
        }
    }

    public void setLineJoin(String lineJoin) {
        int join = LineJoinAdapter.parse(lineJoin);
        if (join != MISMATCH) {
            BasicStroke stroke = (BasicStroke) context.getStroke();
            context.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), join, stroke.getMiterLimit()));
        }
    }

    public void setLineWidth(float lineWidth) {
        BasicStroke stroke = (BasicStroke) context.getStroke();
        context.setStroke(new BasicStroke(lineWidth, stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit()));
    }

    public void setMiterLimit(float miterLimit) {
        BasicStroke stroke = (BasicStroke) context.getStroke();
        context.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), miterLimit));

    }

    public void scale(double x, double y) {
        context.scale(x, y);
    }

    public void rotate(double theta) {
        context.rotate(theta);
    }

    public void translate(double x, double y) {
        context.translate(x, y);
    }

    public void transform(double mxx, double myx,
                          double mxy, double myy,
                          double mxt, double myt) {
        context.transform(new AffineTransform(mxx, myx, mxy, myy, mxt, myt));
    }

    public void setTransform(double mxx, double myx,
                             double mxy, double myy,
                             double mxt, double myt) {
        context.setTransform(new AffineTransform(mxx, myx, mxy, myy, mxt, myt));
    }

    public void setFont(String font) {
        if (!StringUtils.isEmpty(font)) {
            context.setFont(FontAdapter.processFont(font));
        }
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = TextAlignAdapter.get(textAlign);
    }

    public void setTextBaseline(String textBaseline) {
        this.textBaseline = TextBaselineAdapter.get(textBaseline);
    }

    public void setGlobalCompositeOperation(String rule) {
        int compositeRule = CompositeAdapter.parse(rule);
        if (compositeRule != MISMATCH) {
            AlphaComposite composite = (AlphaComposite) context.getComposite();
            context.setComposite(AlphaComposite.getInstance(compositeRule, composite.getAlpha()));
        }
    }

    public void setGlobalAlpha(double alpha) {
        if (alpha > 1.0 || alpha < 0) {
            return;
        }
        AlphaComposite composite = (AlphaComposite) context.getComposite();
        context.setComposite(AlphaComposite.getInstance(composite.getRule(), (float) alpha));
    }

    public void drawImage(BufferedImage img, int x, int y) {
        context.drawImage(img, x, y, null);
    }

    public void drawImage(BufferedImage img, int x, int y, int width, int height) {
        if (width < 0) {
            x = x + width;
            width = -width;
        }
        if (height < 0) {
            y = y + height;
            height = -height;
        }
        context.drawImage(img, x, y, width, height, null);
    }

    public void drawImage(BufferedImage img, int sx, int sy, int sWidth, int sHeight, int x, int y, int width, int height) {
        if (sWidth < 0) {
            sx = sx + sWidth;
            sWidth = -sWidth;
        }
        if (sHeight < 0) {
            sy = sy + sHeight;
            sHeight = -sHeight;
        }
        ImageData imageData = getImageData(img, sx, sy, sWidth, sHeight);
        BufferedImage buffer = new BufferedImage(sWidth, sHeight, BufferedImage.TYPE_INT_ARGB);
        putImageData(buffer, imageData, 0, 0, 0, 0, sWidth, sHeight);
        drawImage(buffer, x, y, width, height);
    }

    public ImageData createImageData(int width, int height) {
        if (width < 0) {
            width = -width;
        }
        if (height < 0) {
            height = -height;
        }
        int[] data = new int[width * height * 4];
        return new ImageData(width, height, data);
    }

    public ImageData getImageData(int x, int y, int width, int height) {
        return getImageData(canvas, x, y, width, height);
    }

    public void putImageData(String imageData, int x, int y) {
        ImageData image = ImageData.valueOf(imageData);
        putImageData(image, x, y, 0, 0, image.getWidth(), image.getHeight());
    }

    public void putImageData(String imageData, int x, int y, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
        ImageData image = ImageData.valueOf(imageData);
        putImageData(image, x, y, dirtyX, dirtyY, dirtyWidth, dirtyHeight);
    }

    public void putImageData(ImageData imageData, int x, int y, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
        putImageData(canvas, imageData, x, y, dirtyX, dirtyY, dirtyWidth, dirtyHeight);
    }

    public void out(File file) {
        try{
            ImageIO.write(canvas, "PNG", file);
            context.dispose();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private ImageData getImageData(BufferedImage img, int x, int y, int width, int height) {
        if (width < 0) {
            x = x + width;
            width = -width;
        }
        if (height < 0) {
            y = y + height;
            height = -height;
        }
        int minX = Math.max(x, 0);
        int minY = Math.max(y, 0);
        int boundX = Math.min(x + width, img.getWidth());
        int boundY = Math.min(y + height, img.getHeight());
        int[] data = new int[width * height * 4];
        for (int j = minY; j < boundY; j++) {
            for (int i = minX; i < boundX; i++) {
                int k = ((j - y) * width + (i - x)) * 4;
                int[] rgba = CanvasUtils.intColorToRGBA(img.getRGB(i, j));
                data[k] = rgba[0];
                data[k + 1] = rgba[1];
                data[k + 2] = rgba[2];
                data[k + 3] = rgba[3];
            }
        }
        return new ImageData(width, height, data);
    }

    private void putImageData(BufferedImage img, ImageData imageData, int x, int y, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
        int minX = Math.max(x, 0);
        int minY = Math.max(y, 0);
        int boundX = Math.min(x + imageData.getWidth(), img.getWidth());
        int boundY = Math.min(y + imageData.getHeight(), img.getHeight());
        //将边为负的矩形重行定位宽高和左上角的点
        dirtyX = dirtyX + x;
        dirtyY = dirtyY + y;
        if (dirtyWidth < 0) {
            dirtyX = dirtyX + dirtyWidth;
            dirtyWidth = -dirtyWidth;
        }
        if (dirtyHeight < 0) {
            dirtyY = dirtyY + dirtyHeight;
            dirtyHeight = -dirtyHeight;
        }
        minX = Math.max(minX, dirtyX);
        minY = Math.max(minY, dirtyY);
        boundX = Math.min(boundX, dirtyX + dirtyWidth);
        boundY = Math.min(boundY, dirtyY + dirtyHeight);

        int[] data = imageData.getData();
        for (int n = 0; n < data.length; n++) {
            if (data[n] > 255) {
                data[n] = 255;
            } else if (data[n] < 0) {
                data[n] = 0;
            }
        }
        for (int j = minY; j < boundY; j++) {
            for (int i = minX; i < boundX; i++) {
                int k = ((j - y) * imageData.getWidth() + (i - x)) * 4;
                int color = (data[k + 3] << 24) | (data[k] << 16) | (data[k + 1] << 8) | data[k + 2];
                img.setRGB(i, j, color);
            }
        }
    }

    private void beforeFill() {
        context.setPaint(fillPaint);
    }

    private void beforeStroke() {
        context.setPaint(strokePaint);
    }
}
