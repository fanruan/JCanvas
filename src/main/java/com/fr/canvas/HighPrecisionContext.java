package com.fr.canvas;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * 高精度版本的canvas接口。
 */
public class HighPrecisionContext extends ContextAdapter {

    public HighPrecisionContext(Graphics2D context, BufferedImage canvas) {
        super(context, canvas);
    }

    @Override
    public void setTransform(double mxx, double myx,
                             double mxy, double myy,
                             double mxt, double myt) {
        //重新设置矩阵时，对位移点进行相应的双倍变换
        super.setTransform(mxx, myx, mxy, myy, mxt * HighPrecisionCanvas.PRECISION, myt * HighPrecisionCanvas.PRECISION);
        scale(HighPrecisionCanvas.PRECISION, HighPrecisionCanvas.PRECISION);

    }

    @Override
    protected ImageData getImageData(BufferedImage img, int x, int y, int width, int height) {
        if (width < 0) {
            x = x + width;
            width = -width;
        }
        if (height < 0) {
            y = y + height;
            height = -height;
        }
        int minX = Math.max(x * HighPrecisionCanvas.PRECISION, 0);
        int minY = Math.max(y * HighPrecisionCanvas.PRECISION, 0);
        int boundX = Math.min((x + width) * HighPrecisionCanvas.PRECISION, img.getWidth());
        int boundY = Math.min((y + height) * HighPrecisionCanvas.PRECISION, img.getHeight());
        int[] data = new int[width * height * 4];
        for (int j = minY; j < boundY; j = j + HighPrecisionCanvas.PRECISION) {
            for (int i = minX; i < boundX; i = i + HighPrecisionCanvas.PRECISION) {
                //距离除以2
                int k = ((j - y * HighPrecisionCanvas.PRECISION) * width
                        / HighPrecisionCanvas.PRECISION + (i - x * HighPrecisionCanvas.PRECISION) / HighPrecisionCanvas.PRECISION) * 4;
                int[] rgba = CanvasUtils.intColorToRGBA(img.getRGB(i, j));
                data[k] = rgba[0];
                data[k + 1] = rgba[1];
                data[k + 2] = rgba[2];
                data[k + 3] = rgba[3];
            }
        }
        return new ImageData(width, height, data);
    }

    @Override
    protected void putImageData(BufferedImage img, ImageData imageData, int x, int y, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
        int minX = Math.max(x * HighPrecisionCanvas.PRECISION, 0);
        int minY = Math.max(y * HighPrecisionCanvas.PRECISION, 0);
        int boundX = Math.min((x + imageData.getWidth()) * HighPrecisionCanvas.PRECISION, img.getWidth());
        int boundY = Math.min((y + imageData.getHeight()) * HighPrecisionCanvas.PRECISION, img.getHeight());
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
        minX = Math.max(minX, dirtyX * HighPrecisionCanvas.PRECISION);
        minY = Math.max(minY, dirtyY * HighPrecisionCanvas.PRECISION);
        boundX = Math.min(boundX, (dirtyX + dirtyWidth) * HighPrecisionCanvas.PRECISION);
        boundY = Math.min(boundY, (dirtyY + dirtyHeight) * HighPrecisionCanvas.PRECISION);

        int[] data = imageData.getData();
        for (int n = 0; n < data.length; n++) {
            if (data[n] > 255) {
                data[n] = 255;
            } else if (data[n] < 0) {
                data[n] = 0;
            }
        }
        for (int j = minY; j < boundY; j = j + HighPrecisionCanvas.PRECISION) {
            for (int i = minX; i < boundX; i = i + HighPrecisionCanvas.PRECISION) {
                int k = ((j - y * HighPrecisionCanvas.PRECISION) * imageData.getWidth()
                        / HighPrecisionCanvas.PRECISION + (i - x * HighPrecisionCanvas.PRECISION) / HighPrecisionCanvas.PRECISION) * 4;
                int color = CanvasUtils.RGBAToIntColor(data[k], data[k + 1], data[k + 2], data[k + 3]);
                img.setRGB(i, j, color);
                img.setRGB(i + 1, j, color);
                img.setRGB(i, j + 1, color);
                img.setRGB(i + 1, j + 1, color);
            }
        }
    }
}
