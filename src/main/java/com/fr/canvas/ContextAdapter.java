package com.fr.canvas;

import com.fr.canvas.util.StringUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.LinkedList;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
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

    private LinkedList<CanvasState> stateStack;

    private float[] coords = new float[6];

    public ContextAdapter(Graphics2D context, BufferedImage canvas) {
        init(context, canvas);
    }

    public void reset(Graphics2D context, BufferedImage canvas) {
        init(context, canvas);
    }

    private void init(Graphics2D context, BufferedImage canvas) {
        this.canvas = canvas;
        this.context = context;
        this.fillPaint = Color.BLACK;
        this.strokePaint = Color.BLACK;
        textAlign = TextAlignAdapter.LEFT;
        textBaseline = TextBaselineAdapter.ALPHABETIC;
        path = new Path2D.Double();
        stateStack = new LinkedList<CanvasState>();
    }

    public Paint getFillPaint() {
        return fillPaint;
    }

    public Paint getStrokePaint() {
        return strokePaint;
    }

    public Paint setFillStyle(String fillStyle) {
        setStyle(fillStyle, true);
        return fillPaint;
    }

    public Paint setStrokeStyle(String strokeStyle) {
        setStyle(strokeStyle, false);
        return strokePaint;
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

    public LinearGradientAdapter createLinearGradient(float x0, float y0, float x1, float y1) {
        return new LinearGradientAdapter(x0, y0, x1, y1);
    }

    public RadialGradientAdapter createRadialGradient(float x0, float y0, float r0, float x1, float y1, float r1) {
        return new RadialGradientAdapter(x0, y0, r0, x1, y1, r1);
    }

    public void moveTo(double x, double y) {
        coords[0] = (float) x;
        coords[1] = (float) y;
        context.getTransform().transform(coords, 0, coords, 0, 1);
        path.moveTo(coords[0], coords[1]);
        moved = true;
    }

    public void lineTo(double x, double y) {
        if (!moved) {
            moveTo(x, y);
        }
        coords[0] = (float) x;
        coords[1] = (float) y;
        context.getTransform().transform(coords, 0, coords, 0, 1);
        path.lineTo(coords[0], coords[1]);
    }

    public void rect(double x, double y, double w, double h) {
        moveTo(x, y);
        lineTo(x + w, y);
        lineTo(x + w, y + h);
        lineTo(x, y + h);
        closePath();
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
        path.append(arc.getPathIterator(context.getTransform()), true);
        moved = true;
    }

    public void arcTo(double x1, double y1, double x2, double y2, double radius) {
        if (!moved) {
            moveTo(x1, y1);
        } else if (!tryArcTo((float) x1, (float) y1, (float) x2, (float) y2, (float) radius)) {
            lineTo(x1, y1);
        }
    }

    private boolean tryArcTo(float x1, float y1, float x2, float y2, float radius) {

        float x0, y0;
        coords[0] = (float) path.getCurrentPoint().getX();
        coords[1] = (float) path.getCurrentPoint().getY();
        double[] doubleCoords = new double[]{coords[0], coords[1]};
        try{
            context.getTransform().inverseTransform(doubleCoords, 0, doubleCoords, 0, 1);
        } catch (Exception e) {
            return false;
        }
        x0 = (float) doubleCoords[0];
        y0 = (float) doubleCoords[1];
        double lsq01 = lenSq(x0, y0, x1, y1);
        double lsq12 = lenSq(x1, y1, x2, y2);
        double lsq02 = lenSq(x0, y0, x2, y2);
        double len01 = Math.sqrt(lsq01);
        double len12 = Math.sqrt(lsq12);
        double cosnum = lsq01 + lsq12 - lsq02;
        double cosden = 2.0 * len01 * len12;
        if (cosden == 0.0 || radius <= 0f) {
            return false;
        }
        double cos_2theta = cosnum / cosden;
        double tansq_den = (1.0 + cos_2theta);
        if (tansq_den == 0.0) {
            return false;
        }
        double tansq_theta = (1.0 - cos_2theta) / tansq_den;
        double A = radius / Math.sqrt(tansq_theta);
        double tx0 = x1 + (A / len01) * (x0 - x1);
        double ty0 = y1 + (A / len01) * (y0 - y1);
        double tx1 = x1 + (A / len12) * (x2 - x1);
        double ty1 = y1 + (A / len12) * (y2 - y1);
        double mx = (tx0 + tx1) / 2.0;
        double my = (ty0 + ty1) / 2.0;
        double lenratioden = lenSq(mx, my, x1, y1);
        if (lenratioden == 0.0) {
            return false;
        }
        double lenratio = lenSq(mx, my, tx0, ty0) / lenratioden;
        double cx = mx + (mx - x1) * lenratio;
        double cy = my + (my - y1) * lenratio;
        if (!(cx == cx && cy == cy)) {
            return false;
        }
        if (tx0 != x0 || ty0 != y0) {
            lineTo(tx0, ty0);
        }
        double coshalfarc = Math.sqrt((1.0 - cos_2theta) / 2.0);
        boolean ccw = (ty0 - cy) * (tx1 - cx) > (ty1 - cy) * (tx0 - cx);
        if (cos_2theta <= 0.0) {
            double sinhalfarc = Math.sqrt((1.0 + cos_2theta) / 2.0);
            double cv = 4.0 / 3.0 * sinhalfarc / (1.0 + coshalfarc);
            if (ccw) cv = -cv;
            double cpx0 = tx0 - cv * (ty0 - cy);
            double cpy0 = ty0 + cv * (tx0 - cx);
            double cpx1 = tx1 + cv * (ty1 - cy);
            double cpy1 = ty1 - cv * (tx1 - cx);
            bezierCurveTo(cpx0, cpy0, cpx1, cpy1, tx1, ty1);
        } else {
            double sinqtrarc = Math.sqrt((1.0 - coshalfarc) / 2.0);
            double cosqtrarc = Math.sqrt((1.0 + coshalfarc) / 2.0);
            double cv = 4.0 / 3.0 * sinqtrarc / (1.0 + cosqtrarc);
            if (ccw) cv = -cv;
            double midratio = radius / Math.sqrt(lenratioden);
            double midarcx = cx + (x1 - mx) * midratio;
            double midarcy = cy + (y1 - my) * midratio;
            double cpx0 = tx0 - cv * (ty0 - cy);
            double cpy0 = ty0 + cv * (tx0 - cx);
            double cpx1 = midarcx + cv * (midarcy - cy);
            double cpy1 = midarcy - cv * (midarcx - cx);
            bezierCurveTo(cpx0, cpy0, cpx1, cpy1, midarcx, midarcy);
            cpx0 = midarcx - cv * (midarcy - cy);
            cpy0 = midarcy + cv * (midarcx - cx);
            cpx1 = tx1 + cv * (ty1 - cy);
            cpy1 = ty1 - cv * (tx1 - cx);
            bezierCurveTo(cpx0, cpy0, cpx1, cpy1, tx1, ty1);
        }
        return true;
    }

    private static double lenSq(double x0, double y0, double x1, double y1) {
        x1 -= x0;
        y1 -= y0;
        return x1 * x1 + y1 * y1;
    }

    public void quadraticCurveTo(double xc, double yc, double x1, double y1) {
        coords[0] = (float) xc;
        coords[1] = (float) yc;
        coords[2] = (float) x1;
        coords[3] = (float) y1;
        context.getTransform().transform(coords, 0, coords, 0, 2);
        if (!moved) {
            path.moveTo(coords[0], coords[1]);
            moved = true;
        }
        path.quadTo(coords[0], coords[1], coords[2], coords[3]);
    }

    public void bezierCurveTo(double xc1, double yc1, double xc2, double yc2, double x1, double y1) {
        coords[0] = (float) xc1;
        coords[1] = (float) yc1;
        coords[2] = (float) xc2;
        coords[3] = (float) yc2;
        coords[4] = (float) x1;
        coords[5] = (float) y1;
        context.getTransform().transform(coords, 0, coords, 0, 3);
        if (!moved) {
            path.moveTo(coords[0], coords[1]);
            moved = true;
        }
        path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
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
            checkPaint(false);
            draw(false);
        }
    }

    public void fill() {
        if (moved) {
            checkPaint(true);
            draw(true);
        }
    }

    private void checkPaint(boolean isFill) {
        Paint paint = isFill ? fillPaint : strokePaint;
        Paint transPaint = paint;
        if (paint instanceof LinearGradientPaint) {
            LinearGradientPaint linearPaint = (LinearGradientPaint) paint;
            coords[0] = (float) linearPaint.getStartPoint().getX();
            coords[1] = (float) linearPaint.getStartPoint().getY();
            coords[2] = (float) linearPaint.getEndPoint().getX();
            coords[3] = (float) linearPaint.getEndPoint().getY();
            context.getTransform().transform(coords, 0, coords, 0, 2);
            transPaint = new LinearGradientPaint(coords[0], coords[1], coords[2], coords[3],
                    linearPaint.getFractions(), linearPaint.getColors());
        } else if (paint instanceof RadialGradientPaint) {
            RadialGradientPaint radialPaint = (RadialGradientPaint) paint;
            coords[0] = (float) radialPaint.getCenterPoint().getX();
            coords[1] = (float) radialPaint.getCenterPoint().getY();
            coords[2] = (float) radialPaint.getFocusPoint().getX();
            coords[3] = (float) radialPaint.getFocusPoint().getY();
            context.getTransform().transform(coords, 0, coords, 0, 2);
            //FIXME 渐变半径如何变化
            transPaint = new RadialGradientPaint(new Point2D.Double(coords[0], coords[1]), radialPaint.getRadius(),
                    new Point2D.Double(coords[2], coords[3]), radialPaint.getFractions(), radialPaint.getColors(), radialPaint.getCycleMethod());
        }
        context.setPaint(transPaint);
    }

    private void draw(boolean isFill) {
        AffineTransform af = context.getTransform();
        context.setTransform(new AffineTransform());
        if (isFill) {
            context.fill(path);
        } else {
            context.draw(path);
        }
        context.setTransform(af);
    }

    public void clip() {
        AffineTransform af = context.getTransform();
        context.setTransform(new AffineTransform());
        context.clip(path);
        context.setTransform(af);
    }

    public String setLineCap(String lineCap) {
        int cap = LineCapAdapter.parse(lineCap);
        if (cap != MISMATCH) {
            BasicStroke stroke = (BasicStroke) context.getStroke();
            context.setStroke(new BasicStroke(stroke.getLineWidth(), cap, stroke.getLineJoin(), stroke.getMiterLimit()));
        }
        BasicStroke strokeState = (BasicStroke) context.getStroke();
        return LineJoinAdapter.valueOf(strokeState.getLineJoin());
    }

    public String setLineJoin(String lineJoin) {
        int join = LineJoinAdapter.parse(lineJoin);
        if (join != MISMATCH) {
            BasicStroke stroke = (BasicStroke) context.getStroke();
            context.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), join, stroke.getMiterLimit()));
        }
        BasicStroke strokeState = (BasicStroke) context.getStroke();
        return LineJoinAdapter.valueOf(strokeState.getLineJoin());
    }

    public double setLineWidth(double lineWidth) {
        if (lineWidth > 0) {
            BasicStroke stroke = (BasicStroke) context.getStroke();
            context.setStroke(new BasicStroke((float) lineWidth, stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit()));
        }
        BasicStroke strokeState = (BasicStroke) context.getStroke();
        return strokeState.getLineWidth();
    }

    public double setMiterLimit(double miterLimit) {
        if (miterLimit > 0) {
            BasicStroke stroke = (BasicStroke) context.getStroke();
            context.setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), (float) miterLimit));
        }
        BasicStroke strokeState = (BasicStroke) context.getStroke();
        return strokeState.getMiterLimit();

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

    public String setFont(String font) {
        if (!StringUtils.isEmpty(font)) {
            context.setFont(FontAdapter.processFont(font));
        }
        Font fontState = context.getFont();
        return FontAdapter.font2String(fontState);
    }

    public String setTextAlign(String textAlign) {
        TextAlignAdapter textAlignAdapter = TextAlignAdapter.get(textAlign);
        if (textAlignAdapter != null) {
            this.textAlign = textAlignAdapter;
        }
        return this.textAlign.getTextAlign();
    }

    public String setTextBaseline(String textBaseline) {
        TextBaselineAdapter textBaselineAdapter = TextBaselineAdapter.get(textBaseline);
        if (textBaseline != null) {
            this.textBaseline = textBaselineAdapter;
        }
        return this.textBaseline.getTextBaseline();
    }

    public void fillText(String text, double x, double y) {
        beforeFill();
        drawText(text, (float) x, (float) y, true);
    }

    public void strokeText(String text, double x, double y) {
        beforeStroke();
        drawText(text, (float) x, (float) y, false);
    }

    private void drawText(String text, float x, float y, boolean isFill) {
        if (!StringUtils.isEmpty(text)) {
            x = calAlign(text, x);
            y = calBaseline(y);
            //绘制实心文字
            if (isFill) {
                context.drawString(text, x, y);
            }
            //绘制空心文字
            else {
                GlyphVector glyphVector = context.getFont().createGlyphVector(context.getFontMetrics().getFontRenderContext(), text);
                Shape shape = glyphVector.getOutline(x, y);
                context.draw(shape);
            }
        }
    }

    private float calAlign(String text, float x) {
        TextMetrics TextMetrics = measureText(text);
        switch (textAlign) {
            case CENTER:
                x -= TextMetrics.getWidth() / 2;
                break;
            case RIGHT:
                x -= TextMetrics.getWidth();
                break;
            default:
                break;
        }
        return x;
    }

    private float calBaseline(float y) {
        int size = context.getFont().getSize();
        switch (textBaseline) {
            case TOP:
                y += size / 4 * 3;
                break;
            case BOTTOM:
                y -= size / 4;
                break;
            case CENTER:
                y += size / 8 * 3;
            default:
                break;
        }
        return y;
    }

    public TextMetrics measureText(String text) {
        if (!StringUtils.isEmpty(text)) {
            return new TextMetrics(context.getFontMetrics().stringWidth(text));
        }
        return new TextMetrics(0);
    }

    public String setGlobalCompositeOperation(String rule) {
        int compositeRule = CompositeAdapter.parse(rule);
        if (compositeRule != MISMATCH) {
            AlphaComposite composite = (AlphaComposite) context.getComposite();
            context.setComposite(AlphaComposite.getInstance(compositeRule, composite.getAlpha()));
        }
        AlphaComposite compositeState = (AlphaComposite) context.getComposite();
        return CompositeAdapter.valueOf(compositeState.getRule());
    }

    public double setGlobalAlpha(double alpha) {
        if (alpha <= 1.0 || alpha >= 0) {
            AlphaComposite composite = (AlphaComposite) context.getComposite();
            context.setComposite(AlphaComposite.getInstance(composite.getRule(), (float) alpha));
        }
        AlphaComposite compositeState = (AlphaComposite) context.getComposite();
        return compositeState.getAlpha();
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

    public void putImageData(ImageData imageData, int x, int y) {
        putImageData(imageData, x, y, 0, 0, imageData.getWidth(), imageData.getHeight());
    }

    public void putImageData(ImageData imageData, int x, int y, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
        putImageData(canvas, imageData, x, y, dirtyX, dirtyY, dirtyWidth, dirtyHeight);
    }

    public void save() {
        AffineTransform af = context.getTransform();
        context.setTransform(new AffineTransform());
        Shape shape = context.getClip();
        context.setTransform(af);
        CanvasState state = new CanvasState(fillPaint, strokePaint, textAlign, textBaseline, shape,
                context.getStroke(), af, context.getComposite(), context.getFont());
        stateStack.push(state);
    }

    public void restore() {
        if (!stateStack.isEmpty()) {
            CanvasState state = stateStack.pop();
            context.setTransform(new AffineTransform());
            context.setClip(state.getClip());
            context.setTransform(state.getTransform());
            context.setStroke(state.getStroke());
            context.setComposite(state.getComposite());
            context.setFont(state.getFont());
            fillPaint = state.getFillPaint();
            strokePaint = state.getStrokePaint();
            textAlign = state.getTextAlign();
            textBaseline = state.getTextBaseline();
        }
    }

    public void out(String file) {
        try{
            ImageIO.write(canvas, "PNG", new File(file));
            context.dispose();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dispose() {
        context.dispose();
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
                int color = CanvasUtils.RGBAToIntColor(data[k], data[k + 1], data[k + 2], data[k + 3]);
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
