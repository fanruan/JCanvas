
package com.fr.graph.g2d.canvas;

import com.fr.stable.StringUtils;

import java.awt.image.BufferedImage;

/**
 * @author Bjorn
 * @version 10.0
 * Created by Bjorn on 2020-01-02
 */
public class Image implements ImageProvider {

    BufferedImage bufferedImage;

    private String src = StringUtils.EMPTY;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        try {
            bufferedImage = ImageUtils.create(src);
        } catch (Exception ignore) {

        }
    }

    public BufferedImage getImage() {
        return bufferedImage;
    }

    public boolean isCanvas() {
        return false;
    }

    public void dispose() {
        bufferedImage = null;
    }
}