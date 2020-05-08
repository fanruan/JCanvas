package com.fr.graph.g2d.canvas.nashorn;

import com.fr.graph.g2d.canvas.Image;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * @author Bjorn
 * @version 10.0
 * Created by Bjorn on 2019-11-14
 */
public class NashornImage extends Image {

    private ScriptObjectMirror onload;

    private double width;

    private double height;

    private ScriptObjectMirror onerror;

    private int loadTime;

    private Object __cachedImgObj;

    private Object onabort;

    private Object __zrImageSrc;

    public void setOnload(ScriptObjectMirror onload) {
        this.onload = onload;
    }

    public ScriptObjectMirror getOnload() {
        return onload;
    }

    public Object get__cachedImgObj() {
        return __cachedImgObj;
    }

    public void set__cachedImgObj(Object __cachedImgObj) {
        this.__cachedImgObj = __cachedImgObj;
    }

    public Object getOnabort() {
        return onabort;
    }

    public void setOnabort(Object onabort) {
        this.onabort = onabort;
    }

    public Object get__zrImageSrc() {
        return __zrImageSrc;
    }

    public void set__zrImageSrc(Object __zrImageSrc) {
        this.__zrImageSrc = __zrImageSrc;
    }

    public void setSrc(String src) {
        super.setSrc(src);
        if (getImage() != null) {
            this.width = getImage().getWidth();
            this.height = getImage().getHeight();
        }
        onload.call(null, null);
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ScriptObjectMirror getOnerror() {
        return onerror;
    }

    public void setOnerror(ScriptObjectMirror onerror) {
        this.onerror = onerror;
    }

    public int getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }
}