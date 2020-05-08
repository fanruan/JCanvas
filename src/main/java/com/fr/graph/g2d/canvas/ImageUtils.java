package com.fr.graph.g2d.canvas;

import com.fr.general.IOUtils;
import com.fr.log.FineLoggerFactory;
import com.fr.stable.StringUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.image.BufferedImage;

/**
 * 辅助处理canvas的图片绘制接口(drawImage)，处理canvas和image。
 */
public class ImageUtils {

    //10s超时
    private static final int READ_TIME_OUT = 10000;

    public static BufferedImage getOrCreate(String src) {
        BufferedImage image = create(src);
        return image;
    }

    public static BufferedImage create(String src) {
        try {
            return StringUtils.isEmpty(src) ? null : src.startsWith("data:") ? createByBase64(src) : createByUrl(src);
        } catch (IOException ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
            return null;
        }
    }

    private static BufferedImage createByBase64(String base64) throws IOException {
        String imageData = base64.split("base64,")[1];
        byte[] data = IOUtils.base64Decode(imageData);
        return ImageIO.read(new ByteArrayInputStream(data));
    }

    public static BufferedImage createByUrl(String strUrl) throws IOException {
        BufferedImage image = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strUrl); //声明url对象
            connection = (HttpURLConnection) url.openConnection(); //打开连接
            connection.setReadTimeout(READ_TIME_OUT);
            connection.connect();
            int responseCode = connection.getResponseCode();
            //等待load成功之后再读取流
            if (responseCode == HttpURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
                image = ImageIO.read(stream);
            }
        } catch (IOException e) {
            FineLoggerFactory.getLogger().error(e.getMessage(), e);
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return image;
    }
}