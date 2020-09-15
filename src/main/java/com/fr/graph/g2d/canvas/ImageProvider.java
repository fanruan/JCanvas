package com.fr.graph.g2d.canvas;

import java.awt.image.BufferedImage;

/**
 * @author Bjorn
 * @version 10.0
 * Created by Bjorn on 2020-01-07
 */
public interface ImageProvider {

    /**
     * 获取BufferImage对象
     * 返回BufferImage
     */
    BufferedImage getImage();

    boolean isCanvas();

    /**
     * 调用完drawImage之后的动作
     */
    void reset();

    /**
     * 绘制结束之后的动作
     */
    void dispose();
}