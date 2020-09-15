package com.fr.graph.g2d.canvas;

import java.awt.image.BufferedImage;

/**
 * @author Bjorn
 * @version 10.0
 * Created by Bjorn on 2020-09-07
 */
public interface CustomImageProvider {

    boolean isCustom(String url);

    BufferedImage getImage(String url);
}
