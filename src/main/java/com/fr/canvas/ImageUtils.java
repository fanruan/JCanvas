package com.fr.canvas;

import com.fr.log.FineLoggerFactory;
import com.fr.stable.StringUtils;
import com.fr.third.alibaba.druid.util.LRUCache;
import com.fr.third.guava.io.BaseEncoding;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.image.BufferedImage;

/**
 * 辅助处理canvas的图片绘制接口(drawImage)，处理canvas和image。
 */
public class ImageUtils {

    public static final Map<String, CanvasAdapter> CANVAS_HOLDER = new ConcurrentHashMap<String, CanvasAdapter>();

    private static final LRUCache<String, BufferedImage> IMAGE_CACHE = new LRUCache<String, BufferedImage>(50);

    public static BufferedImage get(String id) {
        CanvasAdapter canvasAdapter =  CANVAS_HOLDER.get(id);
        if(canvasAdapter ==null) {
           return null;
        }
        return canvasAdapter.getCanvas();
    }

    public static void put(String id, CanvasAdapter canvas) {
        CANVAS_HOLDER.put(id, canvas);
    }

    public static void remove(String id) {
        CANVAS_HOLDER.remove(id);
    }

    public static void putImage(String src, BufferedImage image) {
        IMAGE_CACHE.put(src, image);
    }

    public static BufferedImage getImage(String src) {
        return IMAGE_CACHE.get(src);
    }

    public static BufferedImage getOrCreate(String src) {
        BufferedImage image = IMAGE_CACHE.get(src);
        if (image == null) {
            image = create(src);
            if (image == null) {
                IMAGE_CACHE.put(src, image);
            }
        }
        return image;
    }

    public static BufferedImage create(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        try{
            if (src.startsWith("data:")) {
                return createByBase64(src);
            }
            return createByUrl(src);
        } catch (IOException ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
            return null;
        }
    }

    private static BufferedImage createByBase64(String base64) throws IOException {
        String imageData = base64.split("base64,")[1];
        byte[] data = BaseEncoding.base64().decode(imageData);
        return ImageIO.read(new ByteArrayInputStream(data));
    }

    private static BufferedImage createByUrl(String strUrl) throws IOException {
        URL url = new URL(strUrl); //声明url对象
        URLConnection connection = url.openConnection(); //打开连接
        connection.setDoOutput(true);
        return ImageIO.read(connection.getInputStream());
    }
}
